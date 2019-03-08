package com.example.omaruokis.food_details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.omaruokis.R;

import java.util.List;
import java.util.Locale;

public class FoodDetailsListAdapter extends RecyclerView.Adapter<FoodDetailsListAdapter.FoodDetailsHolder> {
    private final LayoutInflater inflater;
    private List<FoodDetails> foodDetails;

    public FoodDetailsListAdapter(@NonNull Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setFoodDetails(List<FoodDetails> foodDetails) {
        this.foodDetails = foodDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodDetailsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recyclerview_details_item, viewGroup, false);
        return new FoodDetailsHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDetailsHolder foodDetailsHolder, int i) {
        if(foodDetails != null){
            FoodDetails current = foodDetails.get(i);
            foodDetailsHolder.textViewdesscript.setText(current.getDescript());
            //use ',' instead of '.' as decimal separator
            foodDetailsHolder.textViewbestloc.setText(Double.toString(current.getBestloc()).replace('.',','));
        }else{
            //when foodDetails is not yet initialized
            foodDetailsHolder.textViewbestloc.setText("No value");
            foodDetailsHolder.textViewdesscript.setText("No descriptor");
        }

    }

    //if foodDetails is not yet set return 0 as number of list elements
    @Override
    public int getItemCount() {
        if(foodDetails != null) {
            Log.d(FoodRoomDatabase.TAG, "getItemCount: " + foodDetails.size());
            return foodDetails.size();
        } else {
            return 0;
        }
    }

    public class FoodDetailsHolder extends RecyclerView.ViewHolder{
        private final TextView textViewdesscript;
        private final TextView textViewbestloc;
        final FoodDetailsListAdapter foodDetailsListAdapter;

        public FoodDetailsHolder(@NonNull View itemView, FoodDetailsListAdapter adapter) {
            super(itemView);
            textViewdesscript = itemView.findViewById(R.id.textViewEufName);
            textViewbestloc = itemView.findViewById(R.id.textViewEufValue);
            foodDetailsListAdapter = adapter;
        }
    }
}
