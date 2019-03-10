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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.omaruokis.R;

import java.util.List;

/**
 * Android Activity ui logic for food search, search result and food favorites.
 * before search favorites are shown in recyclerview after search,
 * search results are shown in same recyclerview replacing favorites.
 * By taping food name in recyclerview is FoodDetailsActivity launched.
 * By taping imageview star next to food name is foodId added to favorites representing it as favorite.
 * @author m
 */
public class FoodSearchActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.omaruokis.food_details.MESSAGE"; //String for use of Intends
    private FoodListAdapter adapter; //recyclerview adapter for displaying list of items
    private FoodViewModel mFoodViewModel; //FoodViewModel is for keeping non ui logic separate
    private LiveData<List<FoodNameFi>> listLiveDataFoodNameFi;

    /**
     * Handles FoodSearchActivity wigets and uses FoodViewModel metholds for non ui operations.
     * Is run when activity is started or recreated for example in screen rotation events.
     * Listens LiveData changes in favorite list and foodname list caused by user searches or user favorites.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        EditText editTextFind = findViewById(R.id.editTextFind);
        //keyboard search button does the same as the layout's find button
        editTextFind.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    buttonFind(v);
                    return true;
                }else{
                    return false;
                }
            }
        });

        setSupportActionBar(toolbar);

        //RecyclerView for favorites and search
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new FoodListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //getting the same FoodviewModel.
        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        listLiveDataFoodNameFi = mFoodViewModel.findFoodByName("peruna");
        mFoodViewModel.getFavorites().observe(this, new Observer<List<FoodNameFi>>() {
            @Override
            public void onChanged(@Nullable final List<FoodNameFi> foodNameFis) {
                //Show the number of favorites at ui
                TextView textView = findViewById(R.id.textView3);
                textView.setText(getString(R.string.favorites) + " " + foodNameFis.size());
                //update the cached copy of the foods in the adapter for showing them on ui with favorite users foods.
                adapter.setFoods(foodNameFis);
                Log.d(FoodRoomDatabase.TAG, "onChanged: Observer Food");
            }
        });
        mFoodViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                //update the cached copy of the FOODIDs (favoritesFoodIds) of user favorited foods in the adapter for determining if they are favorites.
                adapter.setFavorites(favorites);
            }
        });
    }

    /**
     * Find Button for doing food name search from database with users text input.
     * @param view
     */
    public void buttonFind(View view){
        EditText editTextFind = findViewById(R.id.editTextFind);
        mFoodViewModel.findFoodByName(editTextFind.getText().toString()).observe(this, new Observer<List<FoodNameFi>>() {
            @Override
            public void onChanged(@Nullable List<FoodNameFi> foodNameFis) {
                TextView textView = findViewById(R.id.textView3);
                int i = foodNameFis.size();
                //Show the number of search results on ui
                textView.setText(getString(R.string.results) + " " + i);
                //update the cached copy of the foods in the adapter for showing them on ui with food search results.
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
