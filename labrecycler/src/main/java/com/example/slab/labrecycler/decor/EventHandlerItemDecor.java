package com.example.slab.labrecycler.decor;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.slab.labrecycler.R;

/**
 * Created by hotstuNg on 2016/8/18.
 */
public class EventHandlerItemDecor extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener, View.OnClickListener {
    public interface OnViewHolderClickListener {
        void onClick(RecyclerView parent, RecyclerView.ViewHolder viewHolder);
    }
    private final static int ATTACH_TAG = R.id.tag_eventHandlerDecor;
    private static final String TAG = "EventHandlerItemDecor";
    private RecyclerView attachParent = null;
    private OnViewHolderClickListener listener;


    public void  attach2RecyclerView(RecyclerView view) {
        if (null != view.getTag(ATTACH_TAG)) {
            EventHandlerItemDecor temp = (EventHandlerItemDecor) view.getTag(ATTACH_TAG);
            temp.detachFromRecyclerView(view);
        }
        view.addOnChildAttachStateChangeListener(this);
        view.setTag(ATTACH_TAG, this);
        this.attachParent = view;
    }

    public void detachFromRecyclerView(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(this);
        view.setTag(ATTACH_TAG, null);
        this.attachParent = null;
    }

    //region OnChildAttachStateChangeListener
    @Override
    public void onChildViewAttachedToWindow(View view) {
        Log.d(TAG, "onChildViewAttachedToWindow() called with: " + "view = [" + view + "]");
        view.setOnClickListener(this);
    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {
        Log.d(TAG, "onChildViewDetachedFromWindow() called with: " + "view = [" + view + "]");
        view.setOnClickListener(this);
    }

    //endregion
    @Override
    public void onClick(View v) {
        if (attachParent == null || listener == null) {
            return;
        }
        RecyclerView.ViewHolder holder = attachParent.getChildViewHolder(v);
        listener.onClick(attachParent, holder);
    }

    public void setListener(OnViewHolderClickListener listener) {
        this.listener = listener;
    }
}
