package com.example.omaruokis.food_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.omaruokis.R;

import java.util.List;

public class FoodSearchActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.omaruokis.food_details.MESSAGE";
    private FoodListAdapter adapter;

    private FoodViewModel mFoodViewModel;
    private LiveData<List<FoodNameFi>> listLiveDataFoodNameFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        EditText editTextFind = findViewById(R.id.editTextFind);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new FoodListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        listLiveDataFoodNameFi = mFoodViewModel.findFoodByName("peruna");
        mFoodViewModel.getFavorites().observe(this, new Observer<List<FoodNameFi>>() {
            @Override
            public void onChanged(@Nullable final List<FoodNameFi> foodNameFis) {
                //update the cached copy of the foods in the adapter.
                TextView textView = findViewById(R.id.textView3);
                textView.setText(getString(R.string.favorites) + " " + foodNameFis.size());
                adapter.setFoods(foodNameFis);
                Log.d(FoodRoomDatabase.TAG, "onChanged: Observer Food");
            }
        });
        mFoodViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                adapter.setFavorites(favorites);
                Log.d(FoodRoomDatabase.TAG, "onChanged: Observer Favorite");
                for(int i = 0; i < favorites.size(); i++){
                    Log.d(FoodRoomDatabase.TAG, "onChanged: " + favorites.get(i).getFoodId());
                }
            }
        });
    }


    public void buttonFind(View view){
        EditText editTextFind = findViewById(R.id.editTextFind);
        mFoodViewModel.findFoodByName(editTextFind.getText().toString()).observe(this, new Observer<List<FoodNameFi>>() {
            @Override
            public void onChanged(@Nullable List<FoodNameFi> foodNameFis) {
                TextView textView = findViewById(R.id.textView3);
                int i = foodNameFis.size();
                textView.setText(getString(R.string.results) + " " + i);
                adapter.setFoods(foodNameFis);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.food_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
