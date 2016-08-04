
package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class VTextView extends View {

//    private static final String FONT_PATH = Environment.getExternalStorageDirectory()
//            .getAbsolutePath()
//            + File.separator + "ipam.ttf";

    private static final int TOP_SPACE = 18;

    private static final int BOTTOM_SPACE = 18;

    private static final int FONT_SIZE = 100;

   // private Typeface mFace;

    private Paint mPaint;
    private Paint helpLinePaint;

    private String text = "";

    private int width;

    private int height;
    private Rect bounds;

    public VTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mFace = Typeface.createFromFile(FONT_PATH);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(FONT_SIZE);
        mPaint.setColor(Color.BLACK);

        helpLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        helpLinePaint.setColor(Color.BLUE);
        helpLinePaint.setStrokeWidth(5);
        helpLinePaint.setStyle(Paint.Style.STROKE);

        //mPaint.setTypeface(mFace);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = getWidth();
        height = getHeight();
    }

    @Override
    public void onDraw(Canvas canvas) {
        float fontSpacing = mPaint.getFontSpacing();
        float lineSpacing = fontSpacing * 2;
        float x = width - lineSpacing;
        float y = TOP_SPACE + fontSpacing * 2;
        String[] s = text.split("");
        boolean newLine = false;
        bounds = new Rect();
        for (int i = 1; i <= text.length(); i++) {
            newLine = false;
            mPaint.getTextBounds(s[i], 0, 1, bounds);
            System.out.println(bounds.toShortString());
            CharSetting setting = CharSetting.getSetting(s[i]);
            if (setting == null) {
                // 文字設定がない場合、そのまま描画

                canvas.drawText(s[i], x, y, mPaint);
                //canvas.drawCircle(x, y, 10, mPaint);
                bounds.offsetTo(((int) (bounds.left + x)), ((int) (bounds.top + y)));
                canvas.drawRect(bounds, helpLinePaint);
            } else {
                // 文字設定が見つかったので、設定に従い描画
                canvas.save();
                canvas.rotate(setting.angle, x, y);
                canvas.drawText(s[i], x + fontSpacing * setting.x, y + fontSpacing * setting.y,
                        mPaint);
                canvas.restore();
            }

            // もう文字が入らない場合
            // まだ文字が入る場合
            newLine = y + fontSpacing > height - BOTTOM_SPACE;

            if (newLine) {
                // 改行処理
                x -= lineSpacing;
                y = TOP_SPACE + fontSpacing;
            } else {
                // 文字を送る
                y += fontSpacing;
            }
        }
    }
}
