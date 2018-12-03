package com.example.slab.labrecycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slab.labrecycler.customlayoutmgr.StickyHeaders;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyVH> implements StickyHeaders {
    private static final String TAG = "MyAdapter";
    List<String> mList;

    public MyAdapter() {
        this.mList = new ArrayList<>();
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if (1 == viewType) {
            v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            v = inflater.inflate(R.layout.item_progress, parent, false);
        }
        return new MyVH(v, viewType);


    }

    @Override
    public void onBindViewHolder(final MyVH holder, int position) {
        if (1 == holder.getViewType()) {
            holder.setText(getItem(position));
            if (position == 6) {
                //holder.root.setBackgroundColor(Color.MAGENTA);
            }
        } else {
            holder.getRoot().findViewById(R.id.progress_bar).setVisibility(View.GONE);
            holder.getRoot().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);

                    ViewGroup.LayoutParams layoutParams = holder.getRoot().getLayoutParams();
                    View view = (View) holder.getRoot().getParent();
                    layoutParams.height = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
                    Log.e(TAG, "onLayoutChange: parent:" + view + ",height" + layoutParams.height);
                    holder.getRoot().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).startsWith("pro") ? -1 : 1;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public final void setDataSet(List<? extends String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public final String getItem(int position) {
        return mList.get(position);
    }

    public final void addItem(String object) {
        mList.add(object);
        notifyItemInserted(mList.size() - 1);
    }

    public final void addItem(String object, int position) {
        mList.add(position, object);
        notifyItemInserted(position);
    }

    public final void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public final void showProgress(boolean show) {
        if (show) {
            mList.clear();
            mList.add("progress");
        } else {
            // we do not need to clear list if we have already changed
            // data set or we have no ProgressItem to been cleared
            if (mList.size() == 1 && getItemViewType(0) == -1) {
                mList.clear();
            }
        }
    }

    @Override
    public boolean isStickyHeader(int position) {
        return position == 6;
    }
}