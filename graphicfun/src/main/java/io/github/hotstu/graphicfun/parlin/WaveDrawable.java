package io.github.hotstu.graphicfun.parlin;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @since 2018/12/26
 */
public class WaveDrawable extends Drawable implements Progressable, Animatable {
    @Retention(SOURCE)
    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    public @interface Direction {
    }

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    private final Paint mPaint;
    private Path mWavePath;
    private float waveHight;
    private float dt;
    private float mProgress;
    private boolean running;
    private @Direction int direction;


    public WaveDrawable() {
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

    public float getProgress() {
        return mProgress;
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
        System.out.println("draw");
        Rect bounds = getBounds();
        int height = bounds.height();
        int width = bounds.width();
        float xoff = 0f;
        mWavePath.rewind();
        float waveBias = height * (1 - mProgress) + waveHight * (1 - mProgress) - waveHight * (mProgress);
        //float waveBias = 400;
        for (int i = 0; i <= 320; i += 1) {
            double noise = SimplexNoise.noise(dt, xoff);
            mWavePath.lineTo(map(i, 0, 320, 0, width),
                    map((float) noise, 0, 1, waveBias, waveBias + waveHight));
            xoff += 0.01f;
        }
        dt += 0.05f;
        mWavePath.lineTo(width, height);
        mWavePath.lineTo(0, height);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
        if (running) {
            invalidateSelf();
        }

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


    @Override
    public void start() {
        running = true;
        invalidateSelf();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
