package com.example.demofoodshop.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofoodshop.Item.Item;
import com.example.demofoodshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {
    Context context;
    List<Item> list;

    public AdapterRecyclerView(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item = list.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(item.getPrice());
        Picasso.with(context)
                .load(item.getThumbnail())
                .into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView description;
        private ImageView imageview;
        public RelativeLayout viewBackground,viewForeground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            imageview = itemView.findViewById(R.id.thumbnail);
        }
    }
    public void removeItem(int postion){
        list.remove(postion);
        notifyItemRemoved(postion);
    }
    public void restoreItem(Item item, int postion){
        list.add(postion,item);
        notifyItemInserted(postion);

    }
}
