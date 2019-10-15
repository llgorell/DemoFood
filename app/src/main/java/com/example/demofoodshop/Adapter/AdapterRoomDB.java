package com.example.demofoodshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofoodshop.Models.Food;
import com.example.demofoodshop.Models.FoodDao;
import com.example.demofoodshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRoomDB extends RecyclerView.Adapter<AdapterRoomDB.MyHolder> {
    Context context;
    List<Food>foodList;
    public FoodDao foodDao;

    public AdapterRoomDB(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        return new AdapterRoomDB.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Food food =foodList.get(position);
        holder.name.setText(food.getName());
        holder.description.setText(food.getDescription());
        holder.price.setText(food.getPrice());
        Picasso.with(context)
                .load(food.getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView description;
        private ImageView thumbnail;
        public RelativeLayout viewBackground,viewForeground;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }public void setData(List<Food> foodList){
        this.foodList=foodList;
        notifyDataSetChanged();
    }
    public void removeItem(int postion){
        foodList.remove(postion);
        notifyItemRemoved(postion);
    }
    public void restoreItem(Food food, int postion){
        foodList.add(postion,food);
        notifyItemInserted(postion);

    }
}
