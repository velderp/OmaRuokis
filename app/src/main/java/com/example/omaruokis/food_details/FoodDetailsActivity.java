package com.example.omaruokis.food_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omaruokis.utilities.DateHolder;
import com.project.omaruokis.R;

import java.util.List;

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
        foodName = intent.getParcelableExtra(FoodSearchActivity.EXTRA_MESSAGE);
        textViewDetails = findViewById(R.id.textViewFoodId );
        textViewDetails.setText(foodName.getFoodName());

        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        foodViewModel.getFoodIdComponetValues(foodName.getFoodId()).observe(this, new Observer<List<ComponentValue>>() {
            @Override
            public void onChanged(@Nullable List<ComponentValue> componentValues) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerviewDetails);
        foodDetailsListAdapter = new FoodDetailsListAdapter(this);
        recyclerView.setAdapter(foodDetailsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodViewModel.findFoodDetails(foodName.getFoodId()).observe(this, new Observer<List<FoodDetails>>() {
            @Override
            public void onChanged(@Nullable List<FoodDetails> foodDetails) {
                //Toast.makeText(FoodDetailsActivity.this, "" + foodDetails.size(), Toast.LENGTH_SHORT).show();
                foodDetailsListAdapter.setFoodDetails(foodDetails);
            }
        });

    }

    public void buttonAddDaysFood(View view){
        final FoodEaten foodEaten = new FoodEaten(foodName.getFoodName(), foodName.getFoodId());
        foodViewModel.getFoodIdComponetValues(foodName.getFoodId()).observe(this, new Observer<List<ComponentValue>>() {
            @Override
            public void onChanged(@Nullable List<ComponentValue> componentValues) {
                foodEaten.setEnerc(componentValues.get(0).getBestLoc());
                foodEaten.setChoavl(componentValues.get(1).getBestLoc());
                foodEaten.setFat(componentValues.get(2).getBestLoc());
                foodEaten.setProt(componentValues.get(3).getBestLoc());

                foodViewModel.insertFoodEaten(foodEaten);

                Log.d(FoodRoomDatabase.TAG, "onChanged: "+ foodEaten.getFoodName() + " " + foodEaten.getFoodId() + " " + foodEaten.getFat());


            }
        });
    }

}
