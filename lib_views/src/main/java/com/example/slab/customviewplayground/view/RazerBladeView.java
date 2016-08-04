package com.example.slab.customviewplayground.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hotstuNg on 2016/8/3.
 */
public class RazerBladeView extends View {

    private Path paths;
    private int n;
    private Paint mPaint;
    private float density;

    public RazerBladeView(Context context) {
        this(context, null);
    }

    public RazerBladeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RazerBladeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RazerBladeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        n = 5;
        paths = new Path();
        paths.moveTo(50, 50);
        paths.lineTo(350, 550);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        density = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.argb(51, 50, 50, 255 ));
        for (int i = n; i > 0; i--) {
            mPaint.setStrokeWidth(5*density + 4*i*density);
            canvas.drawPath(paths, mPaint);
        }
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5*density);
        canvas.drawPath(paths, mPaint);
    }
}
