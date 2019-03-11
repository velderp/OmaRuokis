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

/**
 * Adapter for recyclerview of FoodSearchActivity. For displaying favorite foods and search results and
 * provides user inter activity for clicked items, opening details view for food or favoriting it.
 * @author Mika
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.WordViewHolder> {

    private final LayoutInflater inflater;
    private List<FoodNameFi> foodNameFis; // Cached copy of foodNames are search result or matching FOODIDs from favorites
    private FoodRoomDatabase db;
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

        //sets the favorite star indicator according to the "favorite" table.
        if(favoritesFoodIds.contains(foodNameFis.get(position).getFoodId())){
            holder.imageView.setImageAlpha(255);
        }else{
            holder.imageView.setImageAlpha(50);
        }
    }

    /**
     * Set the food names to be displayed in recyclerview.
     * @param foodNameFis List<FoodNameFi> list food names to be displayed in recyclerview.
     */
    public void setFoods(List<FoodNameFi> foodNameFis) {
        this.foodNameFis = foodNameFis;
        notifyDataSetChanged();
    }

    /**
     * Set the current favorite foods to show as favorites in recyclerview.
     * @param favorites List<Favorite> list of favorites.
     */
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

        /**
         * Action to perform depending on clicked recyclerview view.
         * Start FoodDetails Activity about clicked food or
         * add clicked foot to "favorites" table
         * @param v
         */
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
                new insertAsyncTask(db.foodDao()).execute(new Favorite(foodNameFis.get(getLayoutPosition()).getFoodId()));
            }
        }



    }
    /**
     * AsyncTask for deleteFavorite operation, to not block the UI with potentially long-running operation.
     * Room requires it to be so.
     */
    private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {

        private FoodDao asyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        /**
         * Remove favorite from "favorite" table
         * @param favorite Favorite favorite to be removed from "favorite" table.
         */
        @Override
        protected Void doInBackground(final Favorite... favorite) {
            if(asyncTaskDao.findFavorite(favorite[0].getFoodId()).isEmpty()) {
                    asyncTaskDao.insert(favorite[0]);
            }else{
                asyncTaskDao.deleteFavorite(favorite[0]);
            }
            return null;
        }
    }
}

