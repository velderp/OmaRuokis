package com.example.omaruokis.food_details;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.omaruokis.food_details.FoodRoomDatabase.FOOD_DATABASE_NAME;

/**
 * FoodRepository abstracts access to food database and handles data operations.
 * It provides API to the rest of the app for app data.
 * @author Mika
 */
public class FoodRepository {
    private FoodDao foodDao;
    private LiveData<List<Favorite>> favorites;

    FoodRepository(Application application) {
        if (new File(application.getBaseContext().getDatabasePath(FOOD_DATABASE_NAME).getPath()).exists()) {
            Log.d(FoodRoomDatabase.TAG, "FoodRepository: local db exists");
        } else {
            Log.d(FoodRoomDatabase.TAG, "FoodRepository: No db exists");
            try {
                Log.d(FoodRoomDatabase.TAG, "FoodRepository: Copy asset db to local");
                FileCopy.getInstance().copyAssetDatabase(application.getBaseContext(), FOOD_DATABASE_NAME);
            } catch (IOException e) {
                Log.d(FoodRoomDatabase.TAG, "FoodRepository: Copy failed: " + e.toString());
            }
        }
        Log.d(FoodRoomDatabase.TAG, "FoodRepository: get room database");
        FoodRoomDatabase db = FoodRoomDatabase.getDatabase(application);
        foodDao = db.foodDao();

        favorites = foodDao.getAllFavorites();

    }

    /**
     * Query "foodname_FI" table with name of food.
     * @param foodName String name of food to be queried.
     * @param foodNameStart String Order return list to start whit names of foods that start with foodNameStart.
     * @return LiveData<List<FoodNameFi>> list of names of foods that match name of food queried.
     */
    LiveData<List<FoodNameFi>> findFoodByName(String foodName, String foodNameStart){
        return foodDao.findFoodByName(foodName, foodNameStart);
    }

    /**
     * Returns a list of all favorites in "favorite" table.
     * @return LiveData<List<Favorite>> list of all favorites in "favorite" table.
     */
    LiveData<List<Favorite>> getAllFavorites(){
        return favorites;
    }

    /**
     * Get component values of food.
     * @param foodId int "FOODID" of food which component values are queried from "component_value" table.
     * @return LiveData<List<ComponentValue>> list of component values of food.
     */
    LiveData<List<ComponentValue>> getFoodIdComponetValues(int foodId){
        return foodDao.getFoodIdComponetValues(foodId);
    }

    /**
     * Composes list with human readable words from tables "eufdname_FI.DESCRIPT", "component_value.BESTLOC", "component.COMPUNIT"
     * @param foodId int "FOODID" of food which food component value names, component values and component units are queried.
     * @return LiveData<List<FoodDetails>> list of component value names, component values and component units.
     */
    LiveData<List<FoodDetails>> findFoodDetails(int foodId){
        return foodDao.findFoodDetails(foodId);
    }

    /**
     * Query names of foods in "favorites" table.
     * @return LiveData<List<FoodNameFi>> names of foods in favorites.
     */
    LiveData<List<FoodNameFi>> getFavorites(){
        return foodDao.getFavorites();
    }

    /**
     * Insert foodEaten to "food_eaten" table
     * @param foodEaten FoodEaten insert to table "food_eaten"
     */
    public void insertFoodEaten(FoodEaten foodEaten) {
        new insertAsyncTask(foodDao).execute(foodEaten);
    }

    /**
     * Get list of foods eaten of a specific day from "food_eaten" table.
     * @param date String calendar day which foods eaten is queried. Format "d.m.y"
     * @return LiveData<List<FoodEaten>> list of foods eaten specific day.
     */
    LiveData<List<FoodEaten>> findFoodEatenByDate(String date){
        return foodDao.findFoodEatenByDate(date);
    }

    /**
     * Insert tuple about users day to "users_day". If there is already tuple for that day, it is replaced with updated
     * information from user.
     * @param usersDay UsersDay added to "users_day" table.
     */
    public void insertUsersDay(UsersDay usersDay){
        new insertUsersDayAsyncTask(foodDao).execute(usersDay);
    }

    /**
     * Get user information of specific day with UsersDay object, from "users_day" table.
     * @param date String day which tuple is queried. Format "d.m.y"
     * @return LiveData<UsersDay> users day of a specific day.
     */
    public LiveData<UsersDay> findUsersDayByDate(String date){
        return foodDao.findUsersDayByDate(date);
    }

    /**
     * AsyncTask for insertFoodEaten operation, to not block the UI with potentially long-running operation.
     * Room requires it to be so.
     */
    private static class insertAsyncTask extends AsyncTask<FoodEaten, Void, Void> {

        private FoodDao asyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        /**
         * Insert foodEaten to "food_eaten" table
         * @param params FoodEaten FoodEaten insert to table "food_eaten"
         * @return
         */
        @Override
        protected Void doInBackground(final FoodEaten... params) {
            asyncTaskDao.insertFoodEaten(params[0]);
            return null;
        }
    }

    /**
     * AsyncTask for insertUsersDay operation, to not block the UI with potentially long-running operation.
     * Room requires it to be so.
     */
    private static class insertUsersDayAsyncTask extends AsyncTask<UsersDay, Void, Void> {
        private FoodDao asyncTaskDao;

        public insertUsersDayAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        /**
         * Insert tuple about users day to "users_day". If there is already tuple for that day, it is replaced with updated
         * information from user.
         * @param usersDays UsersDay added to "users_day" table.
         * @return
         */
        @Override
        protected Void doInBackground(UsersDay... usersDays) {
            asyncTaskDao.insertUsersDay(usersDays[0]);
            return null;
        }
    }



}
