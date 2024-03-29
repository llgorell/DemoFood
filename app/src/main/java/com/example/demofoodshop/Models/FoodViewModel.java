package com.example.demofoodshop.Models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;





import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository repository;
    LiveData<List<Food>> allFood;
    LiveData<List<Basket>> allBasket;



    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFood = repository.getAllFood();
        allBasket = repository.getAllBasket();
    }

    public void insertFood(Food food) {
        repository.insertFood(food);
    }
    public void  deleteFood(Food food){repository.deleteFood(food);}
    public void updateFood(Food food){repository.updateFood(food);}


    public LiveData<List<Food>> getAllFood() {
        return allFood;
    }
    public LiveData<List<Basket>> getAllBasket(){return allBasket;}
}
