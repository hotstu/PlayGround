package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by hotstuNg on 2016/8/10.
 */
public class ToyScrollLayout extends FrameLayout {

    public ToyScrollLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ToyScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ToyScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }
    //endregion

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    }
}
