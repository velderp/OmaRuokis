package com.example.omaruokis.food_details;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FoodRepository {
    private FoodDao mFoodDao;
    private LiveData<List<Favorite>> favoritess;

    FoodRepository(Application application) {
        if (new File(application.getBaseContext().getDatabasePath("word_database").getPath()).exists()) {
            Log.d(FoodRoomDatabase.TAG, "FoodRepository: local db exists");
        } else {
            Log.d(FoodRoomDatabase.TAG, "FoodRepository: No db exists");
            try {
                Log.d(FoodRoomDatabase.TAG, "FoodRepository: Copy asset db");
                FileCopy.getInstance().copyAssetDatabase(application.getBaseContext());
            } catch (IOException e) {
                Log.d(FoodRoomDatabase.TAG, "FoodRepository: Copy failed: " + e.toString());
            }
        }
        Log.d(FoodRoomDatabase.TAG, "FoodRepository: get room database");
        FoodRoomDatabase db = FoodRoomDatabase.getDatabase(application);
        mFoodDao = db.wordDao();

        favoritess = mFoodDao.getAllFavorites();

    }


    LiveData<List<FoodNameFi>> findFoodByName(String foodName, String foodNameStart){
        return mFoodDao.findFoodByName(foodName, foodNameStart);
    }

    LiveData<List<Favorite>> getAllFavorites(){
        return favoritess;
    }

    LiveData<List<ComponentValue>> getFoodIdComponetValues(int foodId){
        return mFoodDao.getFoodIdComponetValues(foodId);
    }

    LiveData<List<FoodDetails>> findFoodDetails(int foodId){
        return mFoodDao.findFoodDetails(foodId);
    }

    LiveData<List<FoodNameFi>> getFavorites(){
        return mFoodDao.getFavorites();
    }

    public void insertFoodEaten(FoodEaten foodEaten) {
        new insertAsyncTask(mFoodDao).execute(foodEaten);
    }

    public void deleteFoodEaten(FoodEaten foodEaten){
        mFoodDao.deleteFoodEaten(foodEaten);
    }

    LiveData<List<FoodEaten>> findFoodEatenByDate(String date){
        return mFoodDao.findFoodEatenByDate(date);
    }

    public void insertUsersDay(UsersDay usersDay){
        new insertUsersDayAsyncTask(mFoodDao).execute(usersDay);
    }

    public LiveData<UsersDay> findUsersDayByDate(String date){
        return mFoodDao.findUsersDayByDate(date);
    }

    private static class insertAsyncTask extends AsyncTask<FoodEaten, Void, Void> {

        private FoodDao asyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FoodEaten... params) {
            asyncTaskDao.insertFoodEaten(params[0]);
            return null;
        }
    }
    private static class insertUsersDayAsyncTask extends AsyncTask<UsersDay, Void, Void> {
        private FoodDao asyncTaskDao;

        public insertUsersDayAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(UsersDay... usersDays) {
            asyncTaskDao.insertUsersDay(usersDays[0]);
            return null;
        }
    }



}
