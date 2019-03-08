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
import android.widget.TextView;
import android.widget.Toast;

import com.project.omaruokis.R;

import java.util.List;

public class FoodDetailsActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    private TextView textViewDetails;
    private FoodDetailsListAdapter foodDetailsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        FoodName foodName = intent.getParcelableExtra(FoodSearch.EXTRA_MESSAGE);
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

}
