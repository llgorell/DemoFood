package com.example.demofoodshop.Models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;





import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository repository;
    LiveData<List<Food>> allFood;



    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFood = repository.getAllFood();
    }

    public void insertFood(Food food) {
        repository.insertFood(food);
    }


    public LiveData<List<Food>> getAllFood() {
        return allFood;
    }
}
