package com.example.demofoodshop.Models;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private BasketDao basketDao;
    LiveData<List<Food>> allFood;
    LiveData<List<Basket>> allBasket;


    public FoodRepository(Application application) {
        DataBaseFood dataBase =DataBaseFood.getInstance(application);
        DataBaseBasket dataBaseBasket = DataBaseBasket.getInstance(application);
        foodDao = dataBase.foodDao();
        allFood = foodDao.getallFood();
        basketDao = dataBaseBasket.basketDao();
        allBasket = basketDao.getallBasket();


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
    public LiveData<List<Basket>>getAllBasket(){return allBasket;}

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
