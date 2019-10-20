package com.example.demofoodshop.Models;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Food.class},version = 4,exportSchema = false)
public abstract class DataBaseFood extends RoomDatabase {

    public static final String NAME_DATABASE = "food_database.db";
    public static DataBaseFood instance;
    public abstract FoodDao foodDao();

    public static synchronized DataBaseFood getInstance(Context context){

        if (instance == null) {
            return Room.databaseBuilder(context.getApplicationContext()
                    , DataBaseFood.class, NAME_DATABASE)
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
