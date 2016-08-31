package io.github.hotstu.graphicfun.pathFun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hotstuNg on 2016/8/9.
 */
public class PathFunView extends View {
    private Paint mPaint;
    private Path path_srarch;
    private PathMeasure mMeasure;
    private Path horizLine;

    public PathFunView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PathFunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PathFunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initPaint();
        initPath();
    }

    private void initPath() {
        path_srarch = new Path();
        horizLine = new Path();
        horizLine.moveTo(-200, 0);
        horizLine.lineTo(100, 0);
        mMeasure = new PathMeasure();
        // 注意,不要到360度,否则内部会自动优化,测量不能取到需要的数值
        RectF oval1 = new RectF(-50, -50, 50, 50);          // 放大镜圆环
        path_srarch.addArc(oval1, 45, 359f);

        RectF oval2 = new RectF(-100, -100, 100, 100);      // 外部圆环
        path_srarch.addArc(oval2, 45, 359.9f);

        RectF oval3 = new RectF(-100, -300, 200, 200);      // 外部圆环
        path_srarch.arcTo(oval3, 45, 359.9f, true);

        float[] pos = new float[2];
        float[] tan = new float[2];
        mMeasure.setPath(path_srarch, true);               // 放大镜把手的位置
        Matrix matrix = new Matrix();
        while (mMeasure.nextContour()) {
            System.out.println("++++++++++++");
            System.out.println("length--->" + mMeasure.getLength());
            mMeasure.getPosTan(0, pos, tan);
            mMeasure.getMatrix(0, matrix, PathMeasure.POSITION_MATRIX_FLAG);
            Log.d("initPath", "matrix with flag positon: " + matrix);
            mMeasure.getMatrix(0, matrix, PathMeasure.TANGENT_MATRIX_FLAG);
            Log.d("initPath", "matrix with flag positon: " + matrix);
            mMeasure.getMatrix(0, matrix, PathMeasure.TANGENT_MATRIX_FLAG|PathMeasure.POSITION_MATRIX_FLAG);
            Log.d("initPath", "matrix with flag tangent & position : " + matrix);
            Log.d("initPath", String.format("%.2f, %.2f, %.2f, %.2f", pos[0], pos[1], tan[0], tan[1]));
            Log.d("initPath", "degree " + Math.toDegrees(Math.atan2(tan[1], tan[0])));
        }
        horizLine.transform(matrix);

        //path_srarch.lineTo(pos[0], pos[1]);                 // 放大镜把手
        //

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawPath(path_srarch, mPaint);
        canvas.drawPath(horizLine, mPaint);
    }
}
