package io.github.hotstu.graphicfun.bezier;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hotstuNg on 2016/8/24.
 */

public class BezierWaterDropView extends View {
    private static final String TAG = "BezierWaterDropView";
    private final int originalWidth = 200;
    private final int originalHeight = 200;
    private Paint mPaint;
    RectF ciclerA = new RectF();
    RectF ciclerB = new RectF();
    Matrix scaleMatrix = new Matrix();
    Path mBezierPath = new Path();
    float scale = 1;
    private Paint mTextPaint;

    //region const
    public BezierWaterDropView(Context context) {
        super(context);
        init();
    }

    public BezierWaterDropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierWaterDropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BezierWaterDropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //endregion
    //region override method
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                resolveSize(originalWidth + getPaddingLeft() + getPaddingRight(), widthMeasureSpec),
                resolveSize(originalHeight + getPaddingTop()+ getPaddingBottom(), heightMeasureSpec));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        float horizialScale = (float) w/originalWidth;
        float verticalScale = (float) h/originalHeight;
        scale = Math.min(horizialScale, verticalScale);
        //居中并对齐长宽的较小者
        //scaleMatrix.setScale(1, 1);
        scaleMatrix.reset();
        scaleMatrix.postTranslate((w - originalWidth) >> 1, (h - originalHeight) >> 1);
        scaleMatrix.preScale(scale, scale, originalWidth>>1, originalHeight>>1);

    }

    //endregion
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
        //mPaint.setStrokeWidth(5);
        ciclerA.set(30, 30, 80, 80);
        ciclerB.set(100, 100, 180, 180);
    }


    private int capturedPoint = -1;
    private float lastX = -1;
    private float lastY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                capturedPoint = 1;
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (capturedPoint == 1) {
                    ciclerA.offset((x - lastX)/scale, (y - lastY)/scale);
                    lastX = x;
                    lastY = y;
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                capturedPoint = -1;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.concat(scaleMatrix);
        canvas.drawRect(0, 0, originalWidth, originalHeight, mPaint);
        canvas.drawOval(ciclerA, mPaint);
        canvas.drawOval(ciclerB, mPaint);
        drawBezierPath(canvas);
        canvas.restore();
    }


    private void drawBezierPath(Canvas canvas) {
        //https://isux.tencent.com/qq-mobile-off-duty.html
        //计算中线同侧的梯形 两点P1, P2
        //p1,p2 是两圆切线上的两点
        float x1 = ciclerA.centerX();
        float y1 = ciclerA.centerY();
        float r1 = ciclerA.width() *.5f;
        float x2 = ciclerB.centerX();
        float y2 = ciclerB.centerY();
        float r2 = ciclerB.width() *.5f;

        float distance = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        float gama = (float) Math.asin((ciclerB.width() - ciclerA.width()) / distance);
        //float alpha = (float) Math.asin((y2 - y1) / distance);
        float alpha = (float) Math.atan2(y2 - y1, x2 - x1);
        float theta = (float) (Math.PI*.5f + alpha + gama);

        PointF pointP1 = new PointF(((float) (x1 + r1 * Math.cos(theta))), (float)(y1 + r1 * Math.sin(theta)));
        PointF pointP2 = new PointF(((float) (x2 + r2 * Math.cos(theta))), (float)(y2 + r2 * Math.sin(theta)));
        //同理计算p3 p4
        float theta2 = (float) (-Math.PI*.5f + alpha  -gama);
        PointF pointP3 = new PointF(((float) (x1 + r1 * Math.cos(theta2))), (float)(y1 + r1 * Math.sin(theta2)));
        PointF pointP4 = new PointF(((float) (x2 + r2 * Math.cos(theta2))), (float)(y2 + r2 * Math.sin(theta2)));

        //p5 是p1和p4的中点 p6是p2和p3的中点
        PointF pointP5 = new PointF((pointP1.x + pointP4.x)*.5f, (pointP1.y + pointP4.y)*.5f);
        PointF pointP6 = new PointF((pointP2.x + pointP3.x)*.5f, (pointP2.y + pointP3.y)*.5f);

        canvas.drawText("P1", pointP1.x, pointP1.y, mTextPaint);
        canvas.drawText("P2", pointP2.x, pointP2.y, mTextPaint);
        canvas.drawText("P3", pointP3.x, pointP3.y, mTextPaint);
        canvas.drawText("P4", pointP4.x, pointP4.y, mTextPaint);
        canvas.drawText("P5", pointP5.x, pointP5.y, mTextPaint);
        canvas.drawPoint(pointP5.x, pointP5.y, mTextPaint);
        canvas.drawText("P6", pointP6.x, pointP6.y, mTextPaint);
        canvas.drawPoint(pointP6.x, pointP6.y, mTextPaint);
        mBezierPath.reset();
        mBezierPath.moveTo(pointP1.x, pointP1.y);
        mBezierPath.lineTo(ciclerA.centerX(), ciclerA.centerY());
        mBezierPath.lineTo(pointP3.x, pointP3.y);
        mBezierPath.quadTo(pointP6.x, pointP6.y, pointP4.x, pointP4.y);
        mBezierPath.lineTo(ciclerB.centerX(), ciclerB.centerY());
        mBezierPath.lineTo(pointP2.x, pointP2.y);
        mBezierPath.quadTo(pointP5.x, pointP5.y, pointP1.x, pointP1.y);
        canvas.drawPath(mBezierPath, mPaint);
    }

}
