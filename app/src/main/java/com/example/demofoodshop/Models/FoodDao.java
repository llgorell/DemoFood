package com.example.demofoodshop.Models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFood(Food food);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertListFood(List<Food> foodList);


    @Delete
    void deleteFood(Food food);

    @Query("DELETE FROM table_food")
    void deleteAllFood();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFood(Food food);

    @Query("SELECT * FROM table_food")
    LiveData<List<Food>> getallFood();


}
