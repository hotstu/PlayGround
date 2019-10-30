package com.example.slab.labrecycler.customlayoutmgr;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hglf
 * @since 2018/11/29
 */
public class OneWorldOneDreamLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "OneWorldOneDream";
    private int mVerticalOffset;
    private int mFirstVisiPos;
    private int mLastVisiPos;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "is pre layout:" + state.isPreLayout());

        if (getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            //Nothing to do during prelayout when empty
            return;
        }
        detachAndScrapAttachedViews(recycler);
        mVerticalOffset = 0;
        mFirstVisiPos = 0;
        mLastVisiPos = getItemCount();
        fill(recycler, state);
    }

    /**
     * 承担了以下任务：
     * 1 回收所有屏幕不可见的子View
     * 2 layout所有可见的子View
     * @param recycler
     * @param state
     */
    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int avaliable = getHeight();
        for (int i = 0; i < state.getItemCount() && avaliable > 0; i++) {
            View viewForPosition = recycler.getViewForPosition(i);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int height = getDecoratedMeasuredHeight(viewForPosition);
            int widght =getDecoratedMeasuredWidth(viewForPosition);
            layoutDecoratedWithMargins(viewForPosition,0, mVerticalOffset, widght, mVerticalOffset +height);
            mVerticalOffset += height;
            avaliable -= height;
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        offsetChildrenVertical(-dy);
        return dy;
    }

}
