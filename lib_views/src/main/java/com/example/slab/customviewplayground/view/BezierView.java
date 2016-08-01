package com.example.slab.customviewplayground.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hotstuNg on 2016/7/31.
 */
public class BezierView extends View {
    private static final String TAG = "BezierView";
    private  float capRegion = 20;
    private float density;
    private float width;
    private float height;
    private PointF[] basePoints;
    private  int N = 3;
    private PointF capturedPoint = null;
    private Paint pointPaint;
    private Paint pointLabelPaint;
    private Paint pathPaint;
    private Path linePath;
    private ValueAnimator valueAnimator;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.density = context.getResources().getDisplayMetrics().density;
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setColor(Color.RED);
        pointPaint.setStrokeWidth(2* density);

        pointLabelPaint = new Paint();
        pointLabelPaint.setColor(Color.BLACK);
        pointLabelPaint.setAntiAlias(true);
        pointLabelPaint.setTextSize(40);

        capRegion = 20*density;
        linePath = new Path();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.YELLOW);
        pathPaint.setStrokeWidth(2*density);
        pathPaint.setStyle(Paint.Style.STROKE);

    }

    public void setLevel(int level) {
        int old = getLevel();
        this.N = level;
        if (old != getLevel()) {
            buildPoints(false);
            invalidate();
        }
    }

    public int getLevel() {
        return this.N;
    }

    /**
     *
     *
     * @param from
     * @return
     */
    private PointF generateRandomPoint(PointF from) {
        if (from == null) {
            from = new PointF(width*.5f, height*.5f);
        }
        float Rw = (float) (width / 5 * ( 2 * Math.random() + 1.5f));
        float Rh = (float) (height / 5 * ( 2 * Math.random() + 1.5f));
        PointF result = new PointF();
        double rad = Math.random() * 2* Math.PI;
        result.x = (float) (from.x + Rw * Math.cos(rad));
        result.y = (float) (from.y + Rh * Math.sin(rad));
        result.x = clamp(result.x, 10*density, width - 10*density);
        result.y = clamp(result.y, 10*density, height - 10*density);
        return result;
    }

    private PointF capturePoint(float x, float y) {
        RectF rectF = new RectF();
        for (PointF point : basePoints) {
            rectF.set(point.x - capRegion, point.y - capRegion, point.x + capRegion, point.y + capRegion);
            if (rectF.contains(x, y)) {
                return point;
            }
        }
        return null;
    }

    private float clamp(float value, float min, float max) {
        float d = value - min;
        if (d < 0) {
            value -= 2*d;
        }
        d = max - value;
        if (d < 0) {
            value += 2*d;
        }
        return Math.min(max, Math.max(min, value));
    }

    private void buildPoints(boolean sizeChenged) {
        if (basePoints == null ) {
            basePoints = new PointF[N];
        }
        if (sizeChenged) {
            basePoints[0] = generateRandomPoint(null);
            PointF temp = basePoints[0];
            for (int i = 1; i < basePoints.length; i++) {
                basePoints[i] = generateRandomPoint(temp);
                //theta = (float) (Math.atan2(( temp.y - array[i].y ), ( temp.x - array[i].x)));
                temp = basePoints[i];
            }
        } else {
            PointF[] oldArray = basePoints;
            basePoints = new PointF[N];
            int i;
            for(i = 0; i < basePoints.length && i < oldArray.length; i++) {
                basePoints[i] = oldArray[i];
            }
            for(; i < basePoints.length; i++) {
                if (i == 0) {
                    basePoints[i] = generateRandomPoint(null);
                } else {
                    basePoints[i] = generateRandomPoint(basePoints[i - 1]);
                }
            }
        }

    }

    private PointF compute(PointF[] points, float fraciton) {
        for (int i = 1; i < points.length; i++) {
            for(int j = 0; j < points.length - i; j++){
                points[j].x = points[j].x + (points[j+1].x - points[j].x) * fraciton;
                points[j].y = points[j].y + (points[j+1].y - points[j].y) * fraciton;
            }
        }
        return points[0];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                capturedPoint = capturePoint(x ,y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (capturedPoint != null) {
                    capturedPoint.x = clamp(x, 10 * density, width - 10 * density);
                    capturedPoint.y = clamp(y, 10 * density, height - 10 * density);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                capturedPoint = null;

                showAnimate();
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;

        buildPoints(true);

        showAnimate();
    }

    private void showAnimate() {
        if (valueAnimator != null && valueAnimator.isStarted()) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
        linePath.reset();
        linePath.moveTo(basePoints[0].x, basePoints[0].y);
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000);

        final PointF[] deepCopy = new PointF[basePoints.length];
        for (int i = 0; i < deepCopy.length; i++) {
            deepCopy[i] = new PointF();
            deepCopy[i].set(basePoints[i]);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = compute(deepCopy, animation.getAnimatedFraction());
                linePath.lineTo(p.x, p.y);
                invalidate();
            }
        });
        valueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < basePoints.length; i++) {
            canvas.drawCircle(basePoints[i].x, basePoints[i].y, 5*density, pointPaint);
            canvas.drawText("" + i, basePoints[i].x+ 2*5*density, basePoints[i].y + 2*5*density, pointLabelPaint);
        }
        canvas.drawPath(linePath, pathPaint);

    }
}
