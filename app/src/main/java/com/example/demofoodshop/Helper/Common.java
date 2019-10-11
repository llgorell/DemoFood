package com.example.demofoodshop.Helper;

import com.example.demofoodshop.Remote.ItemRequest;
import com.example.demofoodshop.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common {

    public static ItemRequest getItemRequest(){
        return RetrofitClient.getClient("\"https://api.androidhive.info/\"").create(ItemRequest.class);
    }
}
