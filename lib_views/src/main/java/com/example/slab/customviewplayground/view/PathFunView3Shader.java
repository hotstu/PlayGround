package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hotstuNg on 2016/8/9.
 */
public class PathFunView3Shader extends View {
    private Paint mPaint;
    private Path path_srarch;

    public PathFunView3Shader(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PathFunView3Shader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PathFunView3Shader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initPaint();
        initPath();
    }

    private void initPath() {
        path_srarch = new Path();
        // 注意,不要到360度,否则内部会自动优化,测量不能取到需要的数值
        RectF oval1 = new RectF(-50, -50, 50, 50);          // 放大镜圆环
        path_srarch.addArc(oval1, 45, 359f);

        RectF oval2 = new RectF(-100, -100, 100, 100);      // 外部圆环
        path_srarch.addArc(oval2, 45, 359.9f);

        RectF oval3 = new RectF(-100, -300, 200, 200);      // 外部圆环
        path_srarch.arcTo(oval3, 45, 359.9f, true);


    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        float strokeWidth = 50;
        PathEffect pathEffect = new PathDashPathEffect(getCicle(strokeWidth),
                strokeWidth,
                0.0f,
                PathDashPathEffect.Style.ROTATE);
        mPaint.setPathEffect(pathEffect);
    }

    private Path getTriangle(float size) {
        Path path = new Path();
        float half = size / 2;
        path.moveTo(-half, -half);
        path.lineTo(half, -half);
        path.lineTo(0, half);
        path.close();
        return path;
    }

    private Path getCicle(float size) {
        Path path = new Path();
        float half = size / 2;
        path.addCircle(0, 0, half, Path.Direction.CW);
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawPath(path_srarch, mPaint);
    }
}
