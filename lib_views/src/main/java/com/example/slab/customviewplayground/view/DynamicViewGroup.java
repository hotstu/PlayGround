package com.example.slab.customviewplayground.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by hotstuNg on 2016/8/10.
 */
public class DynamicViewGroup extends ViewGroup {

    private static final String TAG = "DynamicViewGroup";
    private Paint mPaint;
    //region constructors
    public DynamicViewGroup(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DynamicViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DynamicViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }
    //endregion

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.argb(77, 55, 55, 255));

        //如果为true，viewGroup的onDraw方法不被调用
        setWillNotDraw(false);
        //如果为true，子view的canvas会被限定到扣除父布局的padding后的区域
        //setClipToPadding(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                child.layout(
                        getPaddingLeft() + lp.leftMargin,
                        getPaddingTop() + lp.topMargin,
                        getPaddingLeft() + lp.leftMargin + child.getMeasuredWidth(),
                        getPaddingTop() + lp.topMargin + child.getMeasuredHeight()
                );
            }
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(77, 255, 55, 55));
        canvas.save();
        Log.d(TAG, "onDraw() called with: " + "canvas = [" + canvas + "]");
        canvas.translate(getWidth()*.5f, getHeight()*.5f);
        canvas.drawCircle(0, 0, 100, mPaint);
        canvas.restore();
    }

    //region static methods
    public static void detachFromWindow(Activity activity, DynamicViewGroup dyg) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        rootView.removeView(dyg);
    }

    public static DynamicViewGroup attach2Window(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        DynamicViewGroup v = new DynamicViewGroup(activity);
        rootView.addView(v, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return v;
    }
    //endregion
}
