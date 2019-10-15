package com.example.demofoodshop.Helper;

import androidx.recyclerview.widget.RecyclerView;

//custom on swip for get postion
public interface RecyclerItemTouchHelperListener {
    void onswiped(RecyclerView.ViewHolder viewHolder,int direction,int postion);
}
