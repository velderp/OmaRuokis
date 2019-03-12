package com.example.omaruokis.food_details;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * The data access object, or Dao, is an annotated class where you specify SQL
 * queries and associate them with method calls. The compiler checks the SQL for errors,
 * then generates queries from the annotations. For common queries, the libraries provide
 * convenience annotations such as @Insert.
 * Refer to Fineli_Rel_19/descript.txt for all Fineli's table descriptions.
 * @author Mika
 */
@Dao
public interface FoodDao {

    /**
     * Insert favorite to "favorite" table. In case food is already a favorite, and it is tried to be added again, is try ignored.
     * @param favorite Favorite favorite food to be added to "favorite" table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    /**
     * Find if specific food is a favorite.
     * @param foodid int "FOODID" of food queried from "favorite" table.
     * @return List<Integer> list with favorite if it exit in "favorite" table, else returns empty list.
     */
    @Query("SELECT FOODID FROM favorite WHERE FOODID == :foodid")
    List<Integer> findFavorite(int foodid);

    /**
     * Returns a list of all favorites in "favorite" table.
     * @return LiveData<List<Favorite>> list of all favorites in "favorite" table.
     */
    @Query("SELECT FOODID FROM favorite")
    LiveData<List<Favorite>> getAllFavorites();

    /**
     * Remove favorite from "favorite" table
     * @param favorite Favorite favorite to be removed from "favorite" table.
     */
    @Delete
    void deleteFavorite(Favorite favorite);

    /**
     * Query "foodname_FI" table with name of food.
     * @param foodName String name of food to be queried.
     * @param foodNameStart String Order return list to start whit names of foods that start with foodNameStart.
     * @return LiveData<List<FoodNameFi>> list of names of foods that match name of food queried.
     */
    @Query("SELECT * FROM foodname_FI WHERE FOODNAME LIKE :foodName ORDER BY FOODNAME NOT LIKE :foodNameStart")
    LiveData<List<FoodNameFi>> findFoodByName(String foodName, String foodNameStart);

    /**
     * Get component values of food.
     * @param foodId int "FOODID" of food which component values are queried from "component_value" table.
     * @return LiveData<List<ComponentValue>> list of component values of food.
     */
    @Query("SELECT * FROM component_value WHERE FOODID == :foodId")
    LiveData<List<ComponentValue>> getFoodIdComponetValues(int foodId);

    /**
     * Composes list with human readable words from tables "eufdname_FI.DESCRIPT", "component_value.BESTLOC", "component.COMPUNIT"
     * @param foodId int "FOODID" of food which food component value names, component values and component units are queried.
     * @return LiveData<List<FoodDetails>> list of component value names, component values and component units.
     */
    @Query("select eufdname_FI.DESCRIPT, component_value.BESTLOC, component.COMPUNIT from component_value " +
            "inner join eufdname_FI on component_value.EUFDNAME = eufdname_FI.THSCODE " +
            "inner join component on component_value.EUFDNAME = component.EUFDNAME "+
            "where component_value.FOODID = :foodId")
    LiveData<List<FoodDetails>> findFoodDetails(int foodId);

    /**
     * Query names of foods in "favorites" table.
     * @return LiveData<List<FoodNameFi>> names of foods in favorites.
     */
    @Query("SELECT * FROM foodname_FI INNER JOIN favorite on foodname_FI.FOODID = favorite.FOODID")
    LiveData<List<FoodNameFi>> getFavorites();

    /**
     * Insert foodEaten to "food_eaten" table
     * @param foodEaten FoodEaten insert to table "food_eaten"
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFoodEaten(FoodEaten foodEaten);

    /**
     * Returns list of all eaten foods from "food_eaten" table.
     * @return LiveData<List<FoodEaten>> list of all tables "food_eaten" food that have been eaten.
     */
    @Query("SELECT * FROM food_eaten")
    LiveData<List<FoodEaten>> getAllFoodEaten();

    /**
     * Get list of foods eaten of a specific day from "food_eaten" table.
     * @param date String calendar day which foods eaten is queried. Format "d.m.y"
     * @return LiveData<List<FoodEaten>> list of foods eaten specific day.
     */
    @Query("SELECT * FROM food_eaten WHERE DATE LIKE :date")
    LiveData<List<FoodEaten>> findFoodEatenByDate(String date);

    /**
     * Remove eaten food from "food_eaten" table.
     * @param foodEaten FoodEaten foodEaten to be removed from "food_eaten" table.
     */
    @Delete
    void deleteFoodEaten(FoodEaten foodEaten);

    /**
     * Update tuple from "food_eaten" table.
     * @param foodEaten FoodEaten tuple to be updated from "food_eaten" table.
     */
    @Update
    void updateFoodEatenQuantity(FoodEaten foodEaten);

    /**
     * Insert tuple about users day to "users_day". If there is already tuple for that day, it is replaced with updated
     * information from user.
     * @param usersDay UsersDay added to "users_day" table.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsersDay(UsersDay usersDay);

    /**
     * Get user information of specific day with UsersDay object, from "users_day" table.
     * @param date String day which tuple is queried. Format "d.m.y"
     * @return LiveData<UsersDay> users day of a specific day.
     */
    @Query("SELECT * FROM users_day WHERE DATE LIKE :date")
    LiveData<UsersDay> findUsersDayByDate(String date);


}