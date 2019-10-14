package com.example.demofoodshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.demofoodshop.Adapter.AdapterRoomDB;
import com.example.demofoodshop.Helper.RecyclerItemTouchHelperListener;
import com.example.demofoodshop.Models.Food;
import com.example.demofoodshop.Models.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class Offline_mode extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    AdapterRoomDB adapter;
    RecyclerView recyclerView;
    List<Food> foodList;
    private FoodViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);
        recyclerView = findViewById(R.id.recyclerViewoff);
        foodList = new ArrayList<>();
        adapter = new AdapterRoomDB(this,foodList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=
                new com.example.demofoodshop.Helper.ItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);




        viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        viewModel.getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {

               adapter.setData(foods);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onswiped(RecyclerView.ViewHolder viewHolder, int direction, int postion) {

    }
}
