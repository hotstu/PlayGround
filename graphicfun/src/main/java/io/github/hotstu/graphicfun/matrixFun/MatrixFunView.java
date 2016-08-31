package io.github.hotstu.graphicfun.matrixFun;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import io.github.hotstu.graphicfun.R;

/**
 * Created by hotstuNg on 2016/8/22.
 */

public class MatrixFunView extends View {
    private static final int ROTATION_MAX = 60;
    private static final int ROTATION_MIN = -ROTATION_MAX;
    private static final int ROTATION_DEFAULT_X = -10;
    private static final int ROTATION_DEFAULT_Y = 15;
    private static final float ZOOM_DEFAULT = 0.6f;
    private static final float ZOOM_MIN = 0.33f;
    private static final float ZOOM_MAX = 2f;

    Drawable mDrawable;
    private Camera camera;
    private Matrix matrix;
    private Paint mPaint;

    private float rotationY = ROTATION_DEFAULT_Y;
    private float rotationX = ROTATION_DEFAULT_X;
    private float zoom = ZOOM_DEFAULT;

    public MatrixFunView(Context context) {
        super(context);
        init();
    }

    public MatrixFunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixFunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MatrixFunView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        camera = new Camera();
        matrix = new Matrix();
        mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.haizewang_150);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.BLUE);
    }

    float lastX;
    float lastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - lastX;
                float dy = event.getY() - lastY;
                float drx = 90* (dx/getWidth());
                float dry = -90* (dy/getHeight());
                // An 'x' delta affects 'y' rotation and vise versa.
                rotationX = Math.max(ROTATION_MIN, Math.min(ROTATION_MAX, rotationX + dry));
                rotationY = Math.max(ROTATION_MIN, Math.min(ROTATION_MAX, rotationY + drx));
                lastX = event.getX();
                lastY = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth() >> 1;
        int cy = getHeight() >> 1;
        canvas.drawColor(Color.DKGRAY);
        canvas.save();

        camera.save();
        camera.rotate(rotationX, rotationY, 0);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(- mDrawable.getIntrinsicWidth()*.5f, - mDrawable.getIntrinsicHeight()*.5f );
        matrix.postTranslate(cx, cy );
        canvas.concat(matrix);

        canvas.drawRect(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight(), mPaint);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        mDrawable.draw(canvas);

        canvas.restore();
    }
}
