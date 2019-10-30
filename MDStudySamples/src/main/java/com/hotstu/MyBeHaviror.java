package com.hotstu;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowInsetsCompat;
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