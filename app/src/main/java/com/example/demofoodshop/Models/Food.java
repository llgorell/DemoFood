package com.example.demofoodshop.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_food")
public class Food {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "price")
    int price;

    @ColumnInfo(name = "thumbnail")
    String thumbnail;



    public Food(String name, String description, int price,String thumbnail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.thumbnail=thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

}
