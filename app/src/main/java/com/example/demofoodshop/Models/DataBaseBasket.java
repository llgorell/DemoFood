package com.example.demofoodshop.Models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Basket.class},version = 1,exportSchema = false)
public abstract class DataBaseBasket extends RoomDatabase {

    public static final String TABLE_BASKET = "table_basket.db";
    public static DataBaseBasket instance;
    public abstract BasketDao basketDao();

    public static synchronized DataBaseBasket getInstance(Context context){

        if (instance == null){
         return Room.databaseBuilder(context.getApplicationContext(),
                 DataBaseBasket.class,
                 TABLE_BASKET)
                 .fallbackToDestructiveMigration()
                 .addCallback(roomCallBack)
                 .allowMainThreadQueries()
                 .build();

        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
    };
}
