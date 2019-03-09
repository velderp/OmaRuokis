package com.example.omaruokis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omaruokis.food_details.FoodEaten;
import com.project.omaruokis.R;

import java.util.List;

//RecyclerView.Adapter<FoodDetailsListAdapter.FoodDetailsHolder>

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.MealsHolder> {

    private final LayoutInflater inflater;
    private List<FoodEaten> meals;

    public MealsListAdapter(@NonNull Context context) {
        inflater = LayoutInflater.from(context);
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

    public void setMeals(List<FoodEaten> foodEatens){
        if( meals != null) {
            meals.clear();
        }
        meals = foodEatens;
        notifyDataSetChanged();
    }

    public void updateData(){
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

    public class MealsHolder extends RecyclerView.ViewHolder{
        private final TextView textViewMealTextFoodName;
        private final EditText editTextMealEditText;
        //private final Button buttonMealButtonRemove;
        final MealsListAdapter adapter;

        private MealsHolder(View itemView, MealsListAdapter adapter){
            super(itemView);
            textViewMealTextFoodName = itemView.findViewById(R.id.mealTextFoodName);
            editTextMealEditText = itemView.findViewById(R.id.mealEditText);
            //buttonMealButtonRemove = itemView.findViewById(R.id.mealButtonRemove);
            this.adapter = adapter;

            //buttonMealButtonRemove.setOnClickListener(this);
        }
        /*
        @Override
        public void onClick(View v) {

        }*/
    }
}
