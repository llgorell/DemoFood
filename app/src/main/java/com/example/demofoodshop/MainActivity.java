package com.example.demofoodshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.demofoodshop.Adapter.AdapterRecyclerView;
import com.example.demofoodshop.Helper.Common;
import com.example.demofoodshop.Helper.ItemTouchHelperonline;
import com.example.demofoodshop.Helper.RecyclerItemTouchHelperListener;
import com.example.demofoodshop.Item.Item;
import com.example.demofoodshop.Models.DataBaseFood;
import com.example.demofoodshop.Models.Food;
import com.example.demofoodshop.Models.FoodViewModel;
import com.example.demofoodshop.Remote.ItemRequest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {


    private final String URL_API = "https://api.androidhive.info/json/menu.json";
    private RecyclerView recyclerView;
    private List<Item> list;
    private AdapterRecyclerView adapter;
    private ConstraintLayout root;
    FoodViewModel viewModel;

    private String name;
    private String desc;
    private int price;
    private String image_db;
    ItemRequest mservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        root = findViewById(R.id.rootlayout);


        mservice = Common.getItemRequest();//create obj for getRequest api
        list = new ArrayList<>();


        adapter = new AdapterRecyclerView(this,list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=
                new ItemTouchHelperonline(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        //observ on room database
            viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
            viewModel.getAllFood().observe(this, new Observer<List<Food>>() {
                @Override
                public void onChanged(List<Food> foodList) {
                    if (foodList == null || foodList.isEmpty()){
                        addItemToCart();//get data from api with retrofit and insert data
                    }else {
                        addupdatecart();////get data from api with retrofit and update data
                    }

                }
            });



    }

    private void addItemToCart() {
        mservice.getItemList(URL_API).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {
                list.clear();
                list.addAll(response.body());
                adapter.notifyDataSetChanged();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 8; i++) {
                            name = response.body().get(i).getName();
                            desc = response.body().get(i).getDescription();
                            price = response.body().get(i).getPrice();
                            image_db = response.body().get(i).getThumbnail();
                            Food food = new Food(name, desc, price, image_db);
                            DataBaseFood.getInstance(MainActivity.this).foodDao().insertFood(food);
                        }
                    }
                });
                thread.start();


            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
    private void addupdatecart() {
        mservice.getItemList(URL_API).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {
                list.clear();
                list.addAll(response.body());
                adapter.notifyDataSetChanged();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 8; i++) {
                            name = response.body().get(i).getName();
                            desc = response.body().get(i).getDescription();
                            price = response.body().get(i).getPrice();
                            image_db = response.body().get(i).getThumbnail();
                            Food food = new Food(name, desc, price, image_db);
                            DataBaseFood.getInstance(MainActivity.this).foodDao().updateFood(food);

                        }
                    }
                });
                thread.start();

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onswiped(RecyclerView.ViewHolder viewHolder, int direction, int postion) {
        if (viewHolder instanceof AdapterRecyclerView.MyViewHolder){

            String name = list.get(viewHolder.getAdapterPosition()).getName();

            final Item deletedItem = list.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            Snackbar snackbar = Snackbar.make(root,name +"removed from cart!",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    adapter.restoreItem(deletedItem,deleteIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.offline_mode:
                Intent intent = new Intent(MainActivity.this, Offline_mode.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
