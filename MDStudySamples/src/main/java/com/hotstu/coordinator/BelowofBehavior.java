package com.hotstu.coordinator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yifeng.mdstudysamples.R;

/**
 * @author hglf
 * @since 2018/9/30
 */
public class BelowofBehavior extends CoordinatorLayout.Behavior<View> {
    private int targetId;

    public BelowofBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BelowOfBehavior);
        targetId = a.getResourceId(R.styleable.BelowOfBehavior_belowTo, 0);
        a.recycle();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY() + dependency.getHeight());
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == targetId;
    }

}
