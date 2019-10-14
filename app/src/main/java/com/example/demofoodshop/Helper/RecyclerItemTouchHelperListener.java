package com.example.demofoodshop.Helper;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener {
    void onswiped(RecyclerView.ViewHolder viewHolder,int direction,int postion);
}
