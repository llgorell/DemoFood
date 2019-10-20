package com.example.demofoodshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofoodshop.Models.Basket;
import com.example.demofoodshop.Models.DataBaseBasket;
import com.example.demofoodshop.Models.Food;
import com.example.demofoodshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

//adapter for recyclerView in offline mode
public class AdapterRoomDB extends RecyclerView.Adapter<AdapterRoomDB.MyHolder> {
    Context context;
    List<Food>foodList;



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
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        Food food =foodList.get(position);
        holder.name.setText(food.getName());
        holder.description.setText(food.getDescription());
        holder.price.setText(Integer.toString(food.getPrice()));
        Picasso.with(context)
                .load(food.getThumbnail())
                .into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        final DataBaseBasket dbbasket = DataBaseBasket.getInstance(context);

        private TextView name;
        private TextView price;
        private TextView description;
        private ImageView thumbnail;
        public Button btn_add;
        public Button btn_remove;
        public TextView counter;
        public RelativeLayout viewBackground,viewForeground;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            btn_add = itemView.findViewById(R.id.btn_add_shop);
            btn_remove = itemView.findViewById(R.id.btn_decrement);
            counter = itemView.findViewById(R.id.counter);

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int postion = getAdapterPosition();
                    if (postion != RecyclerView.NO_POSITION){
                        Basket basket = dbbasket.basketDao().selectbyId(foodList.get(postion).getId());
                        if (basket == null){
                            Basket newRecord= new Basket(foodList.get(postion).getId(),foodList.get(postion).getName(),foodList.get(postion).getDescription(), foodList.get(postion).getPrice(),foodList.get(postion).getThumbnail());
                            dbbasket.basketDao().insertBasket(newRecord);
                        }
                        else {
                            basket.Counter++;
                            dbbasket.basketDao().updateBasket(basket);

                        }

                    }
                }
            });
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Basket foundRecord = dbbasket.basketDao().selectbyId(foodList.get(position).getId());
                        if (foundRecord == null){
                            Toast.makeText(v.getContext(), "There is no Item for Deleted", Toast.LENGTH_SHORT).show();
                        }else if (foundRecord.Counter <= 1){
                            int idRemove = foodList.get(position).getId();
                            dbbasket.basketDao().DeleteById(idRemove);
                        }else {
                            foundRecord.Counter --;
                            dbbasket.basketDao().updateBasket(foundRecord);
                        }
                    }
                }
            });

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
