package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author hglf
 * @since 2018/12/26
 * 演示获取字体path在绘制
 */
public class TextPathView extends View {

    private PathMeasure pathMeasure;
    private Paint drawPaint;
    private Path drawPath;

    public TextPathView(Context context) {
        super(context);
        init();
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawPaint = new Paint();
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeWidth(10f);
        drawPath = new Path();
        TextPaint mTextPaint = new TextPaint();
        mTextPaint.setTextSize(200);
        String text = "O0000";
        Path mFontPath = new Path();
        mTextPaint.getTextPath(text, 0, text.length(), 0, mTextPaint.getTextSize(), mFontPath);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(mFontPath, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPath.reset();
        pathMeasure.getSegment(0, pathMeasure.getLength(), drawPath, true);

        while (pathMeasure.nextContour()) {
            pathMeasure.getSegment(0, pathMeasure.getLength(), drawPath, true);
        }
        canvas.drawPath(drawPath, drawPaint);

    }
}
