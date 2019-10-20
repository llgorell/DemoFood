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
import com.example.demofoodshop.R;
import com.squareup.picasso.Picasso;
import com.steelkiwi.library.view.BadgeHolderLayout;

import java.util.List;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.MyViewHolder> {
    Context context;
    List<Basket> list;


    public AdapterBasket(Context context, List<Basket> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_basket, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final Basket itemViewModel = list.get(position);
        holder.name.setText(itemViewModel.getName());
        holder.description.setText(itemViewModel.getDescription());
        holder.price.setText(Integer.toString(itemViewModel.getPrice()));
        Picasso.with(context)
                .load(itemViewModel.getThumbnail())
                .into(holder.imageview);
        holder.counter.setText(Integer.toString(itemViewModel.getCounter()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final DataBaseBasket dbbasket = DataBaseBasket.getInstance(context);

        private TextView name;
        private TextView price;
        private TextView description;
        private ImageView imageview;
        public Button btn_add;
        public Button btn_remove;
        public RelativeLayout viewBackground, viewForeground;
        final BadgeHolderLayout badge;
        public TextView counter;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            counter = itemView.findViewById(R.id.counter);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            imageview = itemView.findViewById(R.id.thumbnail);
            btn_add = itemView.findViewById(R.id.btn_add_shop);
            btn_remove = itemView.findViewById(R.id.btn_decrement);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            badge = itemView.findViewById(R.id.badge_counter);


            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int postion = getAdapterPosition();
                    if (postion != RecyclerView.NO_POSITION){
                        Basket basket = dbbasket.basketDao().selectbyId(list.get(postion).getId());
                        if (basket == null){
                            Basket newRecord= new Basket(list.get(postion).getId(),list.get(postion).getName(),list.get(postion).getDescription(), list.get(postion).getPrice(),list.get(postion).getThumbnail());
                            dbbasket.basketDao().insertBasket(newRecord);
                        }
                        else {
                            basket.Counter++;
                            dbbasket.basketDao().updateBasket(basket);

                        }
                        list = dbbasket.basketDao().getallBasketoff();
                        setData(list);

                    }
                }
            });
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Basket foundRecord = dbbasket.basketDao().selectbyId(list.get(position).getId());
                        if (foundRecord == null){
                            Toast.makeText(v.getContext(), "There is no Item for Deleted", Toast.LENGTH_SHORT).show();
                        }else if (foundRecord.Counter <= 1){
                            int idRemove = list.get(position).getId();
                            dbbasket.basketDao().DeleteById(idRemove);
                        }else {
                            foundRecord.Counter --;
                            dbbasket.basketDao().updateBasket(foundRecord);
                        }
                        list = dbbasket.basketDao().getallBasketoff();
                        setData(list);
                    }
                }
            });



        }


    }

    public void removeItem(int postion) {
        list.remove(postion);
        notifyItemRemoved(postion);
    }

    public void restoreItem(Basket itemViewModel, int postion) {
        list.add(postion, itemViewModel);
        notifyItemInserted(postion);

    }



    public void setData(List<Basket> baskets){
        this.list=baskets;
        notifyDataSetChanged();
    }


}
