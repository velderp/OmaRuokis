package com.example.omaruokis.food_details;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    @Query("SELECT FOODID FROM favorite WHERE FOODID == :foodid")
    List<Integer> findFavorite(int foodid);

    @Query("SELECT FOODID FROM favorite")
    LiveData<List<Favorite>> getAllFavorites();

    @Delete
    void deleteFavorite(Favorite favorite);

    @Query("SELECT * FROM foodname_FI WHERE FOODNAME LIKE :foodName ORDER BY FOODNAME NOT LIKE :foodNameStart")
    LiveData<List<FoodNameFi>> findFoodByName(String foodName, String foodNameStart);

    @Query("SELECT * FROM component_value WHERE FOODID == :foodId")
    LiveData<List<ComponentValue>> getFoodIdComponetValues(int foodId);

    @Query("select eufdname_FI.DESCRIPT, component_value.BESTLOC, component.COMPUNIT from component_value " +
            "inner join eufdname_FI on component_value.EUFDNAME = eufdname_FI.THSCODE " +
            "inner join component on component_value.EUFDNAME = component.EUFDNAME "+
            "where component_value.FOODID = :foodId")
    LiveData<List<FoodDetails>> findFoodDetails(int foodId);

    @Query("SELECT * FROM foodname_FI INNER JOIN favorite on foodname_FI.FOODID = favorite.FOODID")
    LiveData<List<FoodNameFi>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFoodEaten(FoodEaten foodEaten);

    @Query("SELECT * FROM food_eaten")
    LiveData<List<FoodEaten>> getAllFoodEaten();

    @Query("SELECT * FROM food_eaten WHERE DATE LIKE :date")
    LiveData<List<FoodEaten>> findFoodEatenByDate(String date);

    @Delete
    void deleteFoodEaten(FoodEaten foodEaten);

    @Update
    void updateFoodEatenQuantity(FoodEaten foodEaten);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsersDay(UsersDay usersDay);

    @Query("SELECT * FROM users_day WHERE DATE LIKE :date")
    LiveData<UsersDay> findUsersDayByDate(String date);


}