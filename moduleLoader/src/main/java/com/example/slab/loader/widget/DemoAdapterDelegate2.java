package com.example.slab.loader.widget;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DemoAdapterDelegate2 implements SlabAdapter2.ArrayAdapterDelegate<DemoAdapterDelegate2.VH, String> {
    private final int layoutRes;
    private final int tvId;

    public DemoAdapterDelegate2() {
        this.layoutRes = 0;
        this.tvId = 0;
    }

    public DemoAdapterDelegate2(@LayoutRes int layoutRes,@IdRes int tvId) {
        this.layoutRes = layoutRes;
        this.tvId = tvId;
    }

    @Override
    public VH onCreateViewHolder(SlabAdapter2<VH, String> adapter, ViewGroup parent, int viewType) {
        View v;
        if (layoutRes > 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(this.layoutRes, parent, false);
        } else {
            v = new TextView(parent.getContext());
        }
        return new VH(v, this.tvId);
    }

    @Override
    public void onBindViewHolder(SlabAdapter2<VH, String> adapter, VH holder, int position) {
        holder.tv.setText(adapter.getItem(position));
    }

    @Override
    public int getItemViewType(SlabAdapter2<VH, String> adapter, int position) {
        return 0;
    }

    @Override
    public long getItemId(SlabAdapter2<VH, String> adapter, int position) {
        return 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;

        public VH(View itemView, @IdRes int tvId) {
            super(itemView);
            if(tvId > 0) {
                tv = (TextView) itemView.findViewById(tvId);
            } else {
                tv = (TextView) itemView;
            }
        }
    }
}
