package com.hotstu.coordinator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yifeng.mdstudysamples.R;

/**
 * @author hglf
 * @since 2018/10/9
 */
public class GrowPaddingWithOffsetBehavior extends CoordinatorLayout.Behavior {
    private int targetId;

    public GrowPaddingWithOffsetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GrowPaddingWithOffsetBehavior);
        targetId = a.getResourceId(R.styleable.GrowPaddingWithOffsetBehavior_target, 0);
        a.recycle();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        MyBottomSheetBehavior<View> bottomSheetBehavior = MyBottomSheetBehavior.from(dependency);
        //child.setY(dependency.getY() + dependency.getHeight());
        int top = dependency.getTop();
        int minOffset = bottomSheetBehavior.getMinOffset();
        int height = parent.getHeight();
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == targetId;
    }
}
