package com.example.omaruokis.food_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.project.omaruokis.R;

import java.util.List;

public class FoodFindResults extends AppCompatActivity {

    private FoodViewModel mFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_find_results);

        Intent intent = getIntent();
        String findFoodName = intent.getStringExtra(FoodSearch.EXTRA_MESSAGE);
        Log.d(FoodRoomDatabase.TAG, "onCreate: " + findFoodName);

        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        mFoodViewModel.findFoodByName(findFoodName).observe(this, new Observer<List<FoodNameFi>>() {
            @Override
            public void onChanged(@Nullable List<FoodNameFi> foodNameFis) {
                Toast.makeText(getApplicationContext(), "Find count: " + foodNameFis.size(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
