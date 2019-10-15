package com.example.demofoodshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.demofoodshop.Adapter.AdapterRecyclerView;
import com.example.demofoodshop.Adapter.AdapterRoomDB;
import com.example.demofoodshop.Helper.ItemTouchHelperOffline;
import com.example.demofoodshop.Helper.ItemTouchHelperonline;
import com.example.demofoodshop.Helper.RecyclerItemTouchHelperListener;
import com.example.demofoodshop.Models.Food;
import com.example.demofoodshop.Models.FoodViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Offline_mode extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    AdapterRoomDB adapter;
    RecyclerView recyclerView;
    List<Food> foodList;
    private FoodViewModel viewModel;
    private ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);
        recyclerView = findViewById(R.id.recyclerViewoff);
        root = findViewById(R.id.rootlayoutoff);
        foodList = new ArrayList<>();
        adapter = new AdapterRoomDB(this, foodList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new ItemTouchHelperOffline(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        viewModel.getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {

                foodList.clear();
                foodList.addAll(foods);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onswiped(RecyclerView.ViewHolder viewHolder, int direction, int postion) {

        if (viewHolder instanceof AdapterRoomDB.MyHolder) {

            String name = foodList.get(viewHolder.getAdapterPosition()).getName();

            final Food deletedItem = foodList.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            Snackbar snackbar = Snackbar.make(root, name + "removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    adapter.restoreItem(deletedItem, deleteIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
