package com.example.omaruokis;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omaruokis.food_details.FoodDao;
import com.example.omaruokis.food_details.FoodEaten;
import com.example.omaruokis.food_details.FoodRoomDatabase;

import java.util.List;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.MealsHolder> {

    private final LayoutInflater inflater;
    private List<FoodEaten> meals;
    private FoodRoomDatabase db;
    private Context context;

    public MealsListAdapter(@NonNull Context context) {
        inflater = LayoutInflater.from(context);
        db = FoodRoomDatabase.getDatabase(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MealsListAdapter.MealsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_meal_edit_item, viewGroup, false);
        return new MealsHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsListAdapter.MealsHolder mealsHolder, int i) {
        if(meals != null){
            FoodEaten current = meals.get(i);
            mealsHolder.textViewMealTextFoodName.setText(current.getFoodName());
            mealsHolder.editTextMealEditText.setText(Double.toString(current.getFoodQuantity()));
        }
    }

    public void setMeals(List<FoodEaten> foodEaten){
        if( meals != null) {
            meals.clear();
        }
        meals = foodEaten;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(meals != null){
            return meals.size();
        } else {
            return 0;
        }
    }

    public class MealsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewMealTextFoodName;
        private final EditText editTextMealEditText;
        private final ImageView imageViewMealButtonRemove;
        final MealsListAdapter adapter;

        private MealsHolder(final View itemView, MealsListAdapter adapter){
            super(itemView);
            textViewMealTextFoodName = itemView.findViewById(R.id.mealTextFoodName);
            editTextMealEditText = itemView.findViewById(R.id.mealEditText);
            imageViewMealButtonRemove = itemView.findViewById(R.id.mealButtonRemove);
            this.adapter = adapter;

            imageViewMealButtonRemove.setOnClickListener(this);
            editTextMealEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        FoodEaten foodEaten = meals.get(getLayoutPosition());
                        foodEaten.setFoodQuantity(Double.parseDouble(v.getText().toString()));
                        new setFoodEatenQuantityAsyncTask(db.foodDao()).execute(foodEaten);
                        //Confirm to user that action was performed.
                        Snackbar.make(itemView, R.string.food_quantity_update, Snackbar.LENGTH_SHORT).show();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            new deleteAsyncTask(db.foodDao()).execute(meals.get(getLayoutPosition()));
        }
    }

    private static class deleteAsyncTask extends AsyncTask<FoodEaten, Void, Void>{

        private FoodDao asyncTaskDao;

        deleteAsyncTask(FoodDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(FoodEaten... foodEatens) {
            asyncTaskDao.deleteFoodEaten(foodEatens[0]);
            return null;
        }
    }
    private static class setFoodEatenQuantityAsyncTask extends AsyncTask<FoodEaten, Void, Void>{

        private FoodDao asyncTaskDao;

        public setFoodEatenQuantityAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(FoodEaten... foodEatens) {
            asyncTaskDao.updateFoodEatenQuantity(foodEatens[0]);
            return null;
        }
    }
}
