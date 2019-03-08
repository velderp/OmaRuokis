package com.example.omaruokis.food_details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository mRepository;
    private LiveData<List<Favorite>> allFavorites;
    private LiveData<List<FoodNameFi>> foods;

    public FoodViewModel(Application application) {
        super(application);
        mRepository = new FoodRepository(application);
        allFavorites = mRepository.getAllFavorites();
        foods = findFoodByName("riisi");
    }


    LiveData<List<Favorite>> getAllFavorites(){
        return allFavorites;
    }

    LiveData<List<FoodNameFi>> findFoodByName(String foodName){
        foods = mRepository.findFoodByName("%" + foodName + "%", foodName + "%");
        return foods;
    }

    LiveData<List<FoodNameFi>> getFoods(){
        return foods;
    }

    LiveData<List<ComponentValue>> getFoodIdComponetValues(int foodId){
        return mRepository.getFoodIdComponetValues(foodId);
    }

    LiveData<List<FoodDetails>> findFoodDetails(int foodId){
        return mRepository.findFoodDetails(foodId);
    }



}
