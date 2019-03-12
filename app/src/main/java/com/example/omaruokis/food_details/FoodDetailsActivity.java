package com.example.omaruokis.food_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omaruokis.R;

import java.util.List;

/**
 * Android Activity ui logic for food component names, food component quantities, units for food component quantities.
 * Displaying Food details composed of aforementioned in recyclerview.
 * Button for adding food in question to foods_eaten Room database table.
 * @author Mika
 */
public class FoodDetailsActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    private TextView textViewDetails;
    private FoodDetailsListAdapter foodDetailsListAdapter;
    private FoodName foodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        //Get the FoodName object which corresponding food details are to shown.
        foodName = intent.getParcelableExtra(FoodSearchActivity.EXTRA_MESSAGE);
        textViewDetails = findViewById(R.id.textViewFoodId );
        //Display the name of the food.
        textViewDetails.setText(foodName.getFoodName());

        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);

        // recyclerview for displaying food details gotten from table columns
        // eufdname_FI.DESCRIPT component_value.BESTLOC component.COMPUNIT.
        RecyclerView recyclerView = findViewById(R.id.recyclerviewDetails);
        foodDetailsListAdapter = new FoodDetailsListAdapter(this);
        recyclerView.setAdapter(foodDetailsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // with the clicked FoodName object from last activity is food details gotten from food database by FoodName's foodId
        foodViewModel.findFoodDetails(foodName.getFoodId()).observe(this, new Observer<List<FoodDetails>>() {
            @Override
            public void onChanged(@Nullable List<FoodDetails> foodDetails) {
                // update the cached copy of the food details in the adapter for
                // showing them on recyclerview with food in guestion details.
                foodDetailsListAdapter.setFoodDetails(foodDetails);
            }
        });

    }

    /**
     * Button for adding currently displayed food to foods eaten at selected day.
     * Stored in Room database food_eaten table.
     * @param view
     */
    public void buttonAddDaysFood(final View view){
        final FoodEaten foodEaten = new FoodEaten(foodName.getFoodName(), foodName.getFoodId());
        foodViewModel.getFoodIdComponetValues(foodName.getFoodId()).observe(this, new Observer<List<ComponentValue>>() {
            @Override
            public void onChanged(@Nullable List<ComponentValue> componentValues) {
                //set values for FoodEaten object to be stored at Room database.
                foodEaten.setEnerc(componentValues.get(0).getBestLoc());
                foodEaten.setChoavl(componentValues.get(1).getBestLoc());
                foodEaten.setFat(componentValues.get(2).getBestLoc());
                foodEaten.setProt(componentValues.get(3).getBestLoc());
                //add the finalized object to database
                foodViewModel.insertFoodEaten(foodEaten);
                //Confirm to user that action was performed.
                Snackbar.make(view,getString(R.string.snackbar_eat_message)+ " " + foodEaten.getFoodName(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
