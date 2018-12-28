package io.github.hotstu.graphicfun.parlin;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * @author hglf
 * @since 2018/12/26
 */
public class CloudDrawable extends Drawable {
    private final Paint mPaint;
    private final Rect mRect;
    private final double seed;
    private Path mPath;
    private final float waveClipinHeight;


    public CloudDrawable(double seed) {
        this.seed = seed;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mRect = new Rect();
        waveClipinHeight = 100;
        init();
    }

    private void init() {
        mPath = new Path();

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int height = bounds.height();
        int stops = 100;
        for (int i = 0; i < stops; i++) {
            double noise = SimplexNoise.noise(seed, i);
            mPath.lineTo(i*bounds.width()*0.01f, (float) (noise* waveClipinHeight));
        }
        canvas.save();
        canvas.translate(0, height*.5f);

        canvas.drawPath(mPath, mPaint);
        canvas.restore();
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
