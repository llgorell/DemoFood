package com.example.demofoodshop.Helper;

import androidx.recyclerview.widget.RecyclerView;

interface RecyclerItemTouchHelperListener {
    void onswiped(RecyclerView.ViewHolder viewHolder,int direction,int postion);
}
