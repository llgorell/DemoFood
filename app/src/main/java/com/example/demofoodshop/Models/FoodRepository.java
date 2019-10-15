package com.example.demofoodshop.Models;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;


import com.example.demofoodshop.Item.Item;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    LiveData<List<Food>> allFood;

    public FoodRepository(Application application) {
        DataBaseFood dataBase =DataBaseFood.getInstance(application);
        foodDao = dataBase.foodDao();
        allFood = foodDao.getallFood();
    }



    public void insertFood(Food food){
        new InsertAsynTask(foodDao).execute(food);
    }
    public void updateFood(Food food){
        new UpdateFood(foodDao).execute(food);
    }
    public void deleteFood(Food food){new DeleteFood(foodDao).execute(food);}
    public LiveData<List<Food>>getAllFood(){
        return allFood;
    }

    public static class InsertAsynTask extends AsyncTask<Food,Void,Void> {

        private FoodDao foodDao;

        public InsertAsynTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.insertFood(foods[0]);
            return null;
        }
    }
    public class UpdateFood extends AsyncTask<Food,Void,Void>{
        private FoodDao foodDao;

        public UpdateFood(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.updateFood(foods[0]);
            return null;
        }
    }
    public class DeleteFood extends AsyncTask<Food,Void,Void>{
        private FoodDao foodDao;

        public DeleteFood(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.deleteFood(foods[0]);
            return null;
        }
    }



}
