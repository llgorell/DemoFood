package com.example.demofoodshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

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
import com.steelkiwi.library.view.BadgeHolderLayout;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {


    private final String URL_API = "https://api.androidhive.info/json/menu.json";
    private RecyclerView recyclerView;
    private List<Item> list;
    private List<Food> listFood;
    private AdapterRecyclerView adapter;
    private ConstraintLayout root;
    FoodViewModel viewModel;
    BadgeHolderLayout badge;
    private int counter =0;
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
        badge = findViewById(R.id.badge_counter);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Demo SHop");


        mservice = Common.getItemRequest();//create obj for getRequest api
        list = new ArrayList<>();
        listFood = new ArrayList<>();


        adapter = new AdapterRecyclerView(this,listFood);

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
                        adapter.setData(foodList);

                    }else {
                        addupdatecart();////get data from api with retrofit and update data
                        adapter.setData(foodList);

                    }

                }
            });

            badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,BasketActivity.class);
                    startActivity(intent);
                }
            });



    }

    private void addItemToCart() {
        mservice.getItemList(URL_API).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {


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

            final Food deletedItem = listFood.get(viewHolder.getAdapterPosition());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.offline_mode:
                Intent intent = new Intent(MainActivity.this,Offline_mode.class);
                startActivity(intent);
                break;
            case R.id.setting_menu:
                Toast.makeText(this, "Setting dont created yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_menu:
                finish();
        }
        return true;
    }
}



