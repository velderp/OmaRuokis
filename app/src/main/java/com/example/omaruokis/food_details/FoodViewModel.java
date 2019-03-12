package com.example.omaruokis.food_details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * FoodViewModel provides data to the UI and survives configuration changes. Acts between FoodRepository and UI.
 * Activities can be destroyed and created many times, such as when the device is rotated.
 * @author Mika
 */
public class FoodViewModel extends AndroidViewModel {

    private FoodRepository repository;
    private LiveData<List<Favorite>> allFavorites;
    private LiveData<List<FoodNameFi>> foods;

    public FoodViewModel(Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFavorites = repository.getAllFavorites();
    }

    /**
     * Returns a list of all favorites in "favorite" table.
     * @return LiveData<List<Favorite>> list of all favorites in "favorite" table.
     */
    LiveData<List<Favorite>> getAllFavorites(){
        return allFavorites;
    }
    /**
     * Query "foodname_FI" table with name of food.
     * @param foodName String name of food to be queried.
     * @return LiveData<List<FoodNameFi>> list of names of foods that match name of food queried.
     */
    LiveData<List<FoodNameFi>> findFoodByName(String foodName){
        foods = repository.findFoodByName("%" + foodName + "%", foodName + "%");
        return foods;
    }

    /**
     * Get component values of food.
     * @param foodId int "FOODID" of food which component values are queried from "component_value" table.
     * @return LiveData<List<ComponentValue>> list of component values of food.
     */
    LiveData<List<ComponentValue>> getFoodIdComponetValues(int foodId){
        return repository.getFoodIdComponetValues(foodId);
    }

    /**
     * Composes list with human readable words from tables "eufdname_FI.DESCRIPT", "component_value.BESTLOC", "component.COMPUNIT"
     * @param foodId int "FOODID" of food which food component value names, component values and component units are queried.
     * @return LiveData<List<FoodDetails>> list of component value names, component values and component units.
     */
    LiveData<List<FoodDetails>> findFoodDetails(int foodId){
        return repository.findFoodDetails(foodId);
    }

    /**
     * Query names of foods in "favorites" table.
     * @return LiveData<List<FoodNameFi>> names of foods in favorites.
     */
    LiveData<List<FoodNameFi>> getFavorites(){
        return repository.getFavorites();
    }

    /**
     * Insert foodEaten to "food_eaten" table
     * @param foodEaten FoodEaten insert to table "food_eaten"
     */
    public void insertFoodEaten(FoodEaten foodEaten){
        repository.insertFoodEaten(foodEaten);
    }

    /**
     * Get list of foods eaten of a specific day from "food_eaten" table.
     * @param date String calendar day which foods eaten is queried. Format "d.m.y"
     * @return LiveData<List<FoodEaten>> list of foods eaten specific day.
     */
    public LiveData<List<FoodEaten>> findFoodEatenByDate(String date){
        return repository.findFoodEatenByDate(date);
    }

    /**
     * Insert tuple about users day to "users_day". If there is already tuple for that day, it is replaced with updated
     * information from user.
     * @param usersDay UsersDay added to "users_day" table.
     */
    public void insertUsersDay(UsersDay usersDay){
        repository.insertUsersDay(usersDay);
    }

    /**
     * Get user information of specific day with UsersDay object, from "users_day" table.
     * @param date String day which tuple is queried. Format "d.m.y"
     * @return LiveData<UsersDay> users day of a specific day.
     */
    public LiveData<UsersDay> findUsersDayByDate(String date){
        return repository.findUsersDayByDate(date);
    }



}
