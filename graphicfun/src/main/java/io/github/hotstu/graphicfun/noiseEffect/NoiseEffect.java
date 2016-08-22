package io.github.hotstu.graphicfun.noiseEffect;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;

import java.util.Random;

public class NoiseEffect implements Effect{

    private Paint paint = new Paint();
    BitmapShader shader;
    Matrix matrix;
    float scale;

    public NoiseEffect(BitmapDrawable drawable, float scale) {
        shader = new BitmapShader(drawable.getBitmap(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        matrix = new Matrix();
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        paint.setAlpha(255);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        lastGrainOffset = System.currentTimeMillis();
        this.scale=scale;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPaint(paint);
    }

    long lastGrainOffset;

    @Override
    public void update() {
        matrix.reset();
        matrix.setScale(scale, scale);
        matrix.postTranslate(randomRange(-500 * 10f, 500 * 10f),
                randomRange(-500 * 10f, 500 * 10f));
        shader.setLocalMatrix(matrix);
        lastGrainOffset = System.currentTimeMillis();
    }

    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };

    public static Random rand = new Random();
    public static float randomRange(float min, float max) {
        int randomNum = rand.nextInt(((int) max - (int) min) + 1) + (int) min;
        return randomNum;
    }
    public void setNoiseIntensity(float noiseIntensity) {
        paint.setAlpha((int) (255f * noiseIntensity));
    }
}
