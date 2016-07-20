package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hotstuNg on 2016/7/10.
 */
public class MyView extends View {
    private static final String TAG = "MyView";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent start:" + event) ;
        boolean r = super.dispatchTouchEvent(event);
        Log.i(TAG, "dispatchTouchEvent end: " + r + "," + event) ;
        ViewGroup vg = (ViewGroup) getParent();
        vg.requestDisallowInterceptTouchEvent(true);
        return r;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean r = super.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent:" + r);
        return r;
    }
}
