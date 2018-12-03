package com.hotstu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;

public class MyBeHaviror extends CoordinatorLayout.Behavior<View> {
    public MyBeHaviror() {
    }

    public MyBeHaviror(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, View child, WindowInsetsCompat insets) {
        child.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);
        return insets.consumeSystemWindowInsets();
    }
}