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


public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {
    Context context;
    List<Food> list ;
    List<Basket> listbasket;

    public AdapterRecyclerView(Context context, List<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final DataBaseBasket dbbasket = DataBaseBasket.getInstance(context);

        Food item = list.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(Integer.toString(item.getPrice()));
        Picasso.with(context)
                .load(item.getThumbnail())
                .into(holder.imageview);
        holder.counter.setVisibility(View.GONE);











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
        public RelativeLayout viewBackground,viewForeground;
        Button btn_add;
        Button btn_remove;
        TextView counter;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            imageview = itemView.findViewById(R.id.thumbnail);
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
                        Basket basket = dbbasket.basketDao().selectbyId(list.get(postion).getId());
                        if (basket == null){
                            Basket newRecord= new Basket(list.get(postion).getId(),list.get(postion).getName(),list.get(postion).getDescription(), list.get(postion).getPrice(),list.get(postion).getThumbnail());
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
                    }
                }
            });
        }
    }
    public void removeItem(int postion){
        list.remove(postion);
        notifyItemRemoved(postion);
    }
    public void restoreItem(Food item, int postion){
        list.add(postion,item);
        notifyItemInserted(postion);

    }

    public void setData(List<Food> listFood){
        this.list=listFood;
        notifyDataSetChanged();
    }
}
