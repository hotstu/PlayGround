package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;


/**
 * @author songwd
 * @since 2018/12/21
 */
public class CenterPaddingStartTextView extends AppCompatTextView {
    public CenterPaddingStartTextView(Context context) {
        super(context);
    }

    public CenterPaddingStartTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterPaddingStartTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final Layout layout = getLayout();
        if (layout != null) {
            final int lineCount = layout.getLineCount();
            if (lineCount > 0) {
                final int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
                if (ellipsisCount > 0) {
                    setSingleLine(false);
                    setMaxLines(2);

                    final TypedArray a = getContext().obtainStyledAttributes(null,
                            androidx.appcompat.appcompat.R.styleable.TextAppearance,
                            android.R.attr.textAppearanceMedium,
                            android.R.style.TextAppearance_Medium);
                    final int textSize = a.getDimensionPixelSize(
                            androidx.appcompat.appcompat.R.styleable.TextAppearance_android_textSize, 0);
                    if (textSize != 0) {
                        // textSize is already expressed in pixels
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    }
                    a.recycle();

                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
