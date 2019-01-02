package io.github.hotstu.graphicfun.parlin;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author hglf
 * @since 2018/12/26
 */
public class ExpandingBarDrawable extends Drawable implements Progressable {
    private final Paint mPaint;
    private final Rect mRect;
    private float mProgress;
    private float mBias;

    public ExpandingBarDrawable() {
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        mRect.set(bounds);
        mRect.right = (int) (bounds.right * getBias() + bounds.right * (1 - getBias()) * getProgress());
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != mPaint.getAlpha()) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * Returns the current progress of the arrow.
     */
    @FloatRange(from = 0.0, to = 1.0)
    public float getProgress() {
        return mProgress;
    }

    /**
     * Set the progress of the arrow.
     *
     * <p>A value of {@code 0.0} indicates that the arrow should be drawn in its starting
     * position. A value of {@code 1.0} indicates that the arrow should be drawn in its ending
     * position.</p>
     */
    public void setProgress(@FloatRange(from = 0.0, to = 1.0) float progress) {
        if (mProgress != progress) {
            mProgress = progress;
            invalidateSelf();
        }
    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }
    /**
     * Returns the current progress of the arrow.
     */
    @FloatRange(from = 0.0, to = 1.0)
    public float getBias() {
        return mBias;
    }

    /**
     * Set the progress of the arrow.
     *
     * <p>A value of {@code 0.0} indicates that the arrow should be drawn in its starting
     * position. A value of {@code 1.0} indicates that the arrow should be drawn in its ending
     * position.</p>
     */
    public void setBias(@FloatRange(from = 0.0, to = 1.0) float progress) {
        if (mBias != progress) {
            mBias = progress;
            invalidateSelf();
        }
    }

}
