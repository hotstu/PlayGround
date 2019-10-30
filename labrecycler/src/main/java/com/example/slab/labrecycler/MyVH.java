package com.example.slab.labrecycler;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyVH extends RecyclerView.ViewHolder {
    View root;
    TextView tv;
    int viewType;

    public MyVH(View itemView, int viewType) {
        super(itemView);
        this.root = itemView;
        this.viewType = viewType;
        tv = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void setText(String item) {
        tv.setText(item);
    }

    public View getRoot() {
        return root;
    }

    public int getViewType() {
        return viewType;
    }
}