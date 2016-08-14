package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure() called with: " + "widthMeasureSpec = [" + MeasureSpec.toString(widthMeasureSpec) + "], heightMeasureSpec = [" + MeasureSpec.toString(heightMeasureSpec) + "]");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(TAG, "after onMeasure()  : " + "width = [" + getMeasuredWidth() + "], height = [" + getMeasuredHeight() + "]");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout() called with: " + "changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged() called with: " + "w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(77, 55, 55, 255));
    }
}
