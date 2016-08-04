package com.example.slab.customviewplayground.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 动态添加到activity中
 * Created by hotstuNg on 2016/8/4.
 */
public class DynamicVIew extends View {
    private Paint mPaint;

    public DynamicVIew(Context context) {
        this(context, null);
    }

    public DynamicVIew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicVIew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(new LinearGradient(0, 0, 500, 0,
                new int[]{Color.GREEN, Color.YELLOW}, null, Shader.TileMode.MIRROR));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), mPaint);
    }

    public void detachFromWindow(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        rootView.removeView(this);
    }

    public static DynamicVIew attach2Window(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        DynamicVIew v = new DynamicVIew(activity);
        rootView.addView(v, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return v;
    }
}
