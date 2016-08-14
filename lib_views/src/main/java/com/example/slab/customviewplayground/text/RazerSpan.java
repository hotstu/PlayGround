package com.example.slab.customviewplayground.text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.ReplacementSpan;
import android.util.Log;

/**
 * Created by hotstuNg on 2016/8/5.
 */
public class RazerSpan extends ReplacementSpan {
    private static final String TAG = "RazerSpan";
    Rect r;

    public RazerSpan() {
        this.r = new Rect();
        r.set(0, 0, 300, 200);
    }

    /**
     *
     * @param paint
     * @param text
     * @param start
     * @param end
     * @param fm
     * @return 需要的宽度，例如文字的长度， 注意 这里高度是通过设置fm去设置的
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Log.d(TAG, "getSize() called with: " + "paint = [" + paint + "], text = [" + text + "], start = [" + start + "], end = [" + end + "], fm = [" + fm + "]");

        if (fm != null) {
            fm.ascent = -r.bottom;
            fm.descent = 0;

            fm.top = fm.ascent;
            fm.bottom = 0;
        }
        paint.getFontMetrics();
        return r.right;
    }

    /**
     * @param canvas
     * @param text
     * @param start
     * @param end
     * @param x x-coordinate where to draw the text
     * @param top top of the line
     * @param y the baseline
     * @param bottom bottom of the line
     * @param paint
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Log.d(TAG, "draw() called with: " + "canvas = [" + canvas + "], text = [" + text + "], start = [" + start + "], end = [" + end + "], x = [" + x + "], top = [" + top + "], y = [" + y + "], bottom = [" + bottom + "], paint = [" + paint + "]");
        //paint.setColor(Color.BLACK);
        //canvas.drawRect(x, top, x + r.width(), bottom, paint);
        paint.setColor(Color.argb((int) (0.2 * 255), 55, 55, 250));
        //paint.setTextSize(100);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawText(text, start, end, x, y, paint);
    }
}
