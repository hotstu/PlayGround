package io.github.hotstu.graphicfun.pathFun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hotstuNg on 2016/8/9.
 */
public class PathFunView2Progressing extends View {
    private Paint mPaint;
    private Path path_srarch;
    private PathMeasure mMeasure;
    private float fraction = 0f;
    private float density = 0;

    public PathFunView2Progressing(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PathFunView2Progressing(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PathFunView2Progressing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        density = getResources().getDisplayMetrics().density;
        initPaint();
        initPath();
    }

    private void initPath() {
        path_srarch = new Path();

        mMeasure = new PathMeasure();
        // 注意,不要到360度,否则内部会自动优化,测量不能取到需要的数值
        RectF oval1 = new RectF(-50 * density, -50 * density, 50 * density, 50 * density);          // 放大镜圆环
        path_srarch.addArc(oval1, 45, 360f);

        float[] pos = new float[2];
        float[] tan = new float[2];
        mMeasure.setPath(path_srarch, true);               // 放大镜把手的位置
        Matrix matrix = new Matrix();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10 * density);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public float getFraction() {
        return fraction;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        float len = mMeasure.getLength();
        float stop = len * fraction;
        float start = stop - (0.5f - Math.abs(fraction - 0.5f)) * len * 0.5f;
        Path drawPath = new Path();
        mMeasure.getSegment(start, stop, drawPath, true);
        canvas.drawPath(drawPath, mPaint);
    }
}
