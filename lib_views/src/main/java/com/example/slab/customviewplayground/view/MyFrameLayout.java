package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;


/**
 * Created by hotstuNg on 2016/7/10.
 */
public class MyFrameLayout extends FrameLayout {
    private static final String TAG = "MyFrameLayout";

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent start:" + ev) ;
        boolean r = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent end: " + r + "," + ev) ;
        return r;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean r = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "onInterceptTouchEvent: " + r);
        return r;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean r = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent:" + r +"," + event);
        return r;

    }
}
