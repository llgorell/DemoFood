package com.example.demofoodshop.Remote;

import com.example.demofoodshop.Item.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ItemRequest {

    @GET
    Call<List<Item>> getItemList(@Url String url);
}
