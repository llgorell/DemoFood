package com.example.demofoodshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.demofoodshop.Adapter.AdapterBasket;
import com.example.demofoodshop.Models.Basket;
import com.example.demofoodshop.Models.DataBaseBasket;
import com.example.demofoodshop.Models.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Basket> basketList;
    FoodViewModel viewModel;
    TextView total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        DataBaseBasket db =DataBaseBasket.getInstance(this);
        recyclerView=findViewById(R.id.recyclerview_basket);
        total_price = findViewById(R.id.total_price);
        basketList = new ArrayList<>();
        final AdapterBasket adapter = new AdapterBasket(getApplicationContext(),basketList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        basketList=DataBaseBasket.getInstance(this).basketDao().getallBasketoff();
//        adapter.setData(basketList);

        viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        viewModel.getAllBasket().observe(this, new Observer<List<Basket>>() {
            @Override
            public void onChanged(List<Basket> baskets) {
                basketList.clear();
                basketList.addAll(baskets);
                adapter.setData(basketList);

            }
        });

    }
}
