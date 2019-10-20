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
public interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBasket(Basket basket);

    @Query("DELETE FROM table_basket WHERE id=:id")
    void DeleteById(int id);

    @Update
    void updateBasket(Basket basket);

    @Query("SELECT * FROM table_basket WHERE id =:id")
    Basket selectbyId(int id);

    @Query("SELECT * FROM table_basket")
    LiveData<List<Basket>> getallBasket();

    @Query("SELECT * FROM table_basket")
    List<Basket> getallBasketoff();

}
