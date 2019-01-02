package io.github.hotstu.graphicfun.parlin;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author hglf
 * @since 2018/12/28
 * 说明
 */
public class horizontalWaveDrawable extends Drawable implements Progressable{
    private final Paint mPaint;
    private Path mWavePath;
    private float waveHight;
    private float dt;
    private float mProgress;


    public horizontalWaveDrawable() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        waveHight = -1;
        mWavePath = new Path();
        dt = 0;
    }


    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    public void setProgress(@FloatRange(from = 0.0, to = 1.0) float progress) {
        if (mProgress != progress) {
            mProgress = progress;
            invalidateSelf();
        }
    }

    @Override
    public float getProgress() {
        return 0;
    }

    /**
     * 设置波峰高度，px
     *
     * @param waveHight
     */
    public void setWaveHight(float waveHight) {
        if (this.waveHight != waveHight) {
            this.waveHight = waveHight;
            invalidateSelf();
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (waveHight < 0) {
            waveHight = bounds.height() * .05f;
        }
    }

    /**
     * 设置波浪强度 数值越大波浪运动越快
     * 默认0.05f
     *
     * @param waveHight
     */
    public void setWaveStrenth(float waveHight) {
        if (this.waveHight != waveHight) {
            this.waveHight = waveHight;
            invalidateSelf();
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int height = bounds.height();
        int width = bounds.width();
        float xoff = 0f;
        mWavePath.rewind();
        float waveBias = width * (mProgress) + waveHight * (mProgress) - waveHight * (1 - mProgress);
        //float waveBias = 400;
        for (int i = 0; i <= 320; i += 1) {
            double noise = SimplexNoise.noise(dt, xoff);
            mWavePath.lineTo(
                    map((float) noise, 0, 1, waveBias, waveBias + waveHight),
                    map(i, 0, 320, 0, height));
            xoff += 0.01f;
        }
        dt += 0.05f;
        mWavePath.lineTo(0, height);
        mWavePath.lineTo(0, 0);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    private float map(float v, float fromMin, float fromMax, float toMin, float toMax) {

        if (fromMax <= fromMin || toMax <= toMin) {
            throw new IllegalArgumentException();
        }
        return v / (fromMax - fromMin) * (toMax - toMin) + toMin;
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
}
