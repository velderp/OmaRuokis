package com.example.omaruokis.food_details;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.omaruokis.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.WordViewHolder> {

    private final LayoutInflater inflater;
    private List<FoodNameFi> foodNameFis; // Cached copy of foodNames are search result or matching FOODIDs from favorites
    private FoodRoomDatabase db;
    private List<Favorite> favorites; // Cached copy of favorites that are in favorite table
    private List<Integer> favoritesFoodIds; // Cached copy of FOODIDs that are in favorite table

    FoodListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        db = FoodRoomDatabase.getDatabase(context); //Room database
        favoritesFoodIds = new ArrayList<>();
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (foodNameFis != null) {
            FoodNameFi current = foodNameFis.get(position);
            holder.wordItemView.setText(current.getFoodName());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Food");
        }

        if(favoritesFoodIds.contains(foodNameFis.get(position).getFoodId())){
            holder.imageView.setImageAlpha(255);
        }else{
            holder.imageView.setImageAlpha(50);
        }
    }

    public void setFoods(List<FoodNameFi> foodNameFis) {
        this.foodNameFis = foodNameFis;
        notifyDataSetChanged();
    }

    void setFavorites(List<Favorite> favorites){
        favoritesFoodIds.clear();
        if(favorites != null){
            for(int i = 0; i < favorites.size(); i++){
                favoritesFoodIds.add(Integer.valueOf(favorites.get(i).getFoodId()));
            }
        }
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // foodNamesFis has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (foodNameFis != null)
            return foodNameFis.size();
        else return 0;
    }


    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView wordItemView;
        private final ImageView imageView;
        final FoodListAdapter adapter;

        private WordViewHolder(View itemView, FoodListAdapter adapter) {
            super(itemView);
            //food name
            wordItemView = itemView.findViewById(R.id.textViewEufName);
            //favorite star
            imageView = itemView.findViewById(R.id.imageView);
            this.adapter = adapter;
            wordItemView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == wordItemView) {
                //Pass foodName object to FoodDetailsActivity and start it.
                Log.d(FoodRoomDatabase.TAG, "onClick: " + v.toString());
                Intent intent = new Intent(v.getContext(), FoodDetailsActivity.class);
                intent.putExtra(FoodSearchActivity.EXTRA_MESSAGE, foodNameFis.get(getLayoutPosition()));
                v.getContext().startActivity(intent);
            }else if(v == imageView){
                //add taped object FOODID to favorite table if it is not there else remove it from there
                new insertAsyncTask(db.wordDao()).execute(new Favorite(foodNameFis.get(getLayoutPosition()).getFoodId()));
            }
        }



    }
        //async for sql
        private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {

            private FoodDao mAsyncTaskDao;

            insertAsyncTask(FoodDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Favorite... params) {
                if(mAsyncTaskDao.findFavorite(params[0].getFoodId()).isEmpty()) {
                    mAsyncTaskDao.insert(params[0]);
                }else{
                    mAsyncTaskDao.deleteFavorite(params[0]);
                }
                return null;
            }
        }
}

