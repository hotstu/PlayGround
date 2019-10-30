package com.example.slab.desionlib;

import android.content.Context;
import android.content.res.Resources;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public  class MySwipeRefreshLayout extends ViewGroup implements NestedScrollingParent {

    private static final String TAG = "MySwipeRefreshLayout";
    private NestedScrollingParentHelper mParentHelper;
    private View mTarget;
    private int mTotalUnconsumed = 0;
    private boolean mNestedScrollInProgress = false;
    public MySwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");

        if (getChildCount() > 0) {
            View v = getChildAt(0);
            Log.d(TAG, v.getClass().getName());
            Log.d(TAG, "" + ViewCompat.isNestedScrollingEnabled(v));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
    }

    private void ensureTarget() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                    mTarget = child;
                    break;
            }
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll");
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d(TAG, "onNestedScrollAccepted");
        //init
        mParentHelper.onNestedScrollAccepted(child, target, axes);
        mTotalUnconsumed = 0;
        mNestedScrollInProgress = true;
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (!canChildScrollUp()) {
            mTotalUnconsumed += dy;
            Log.d(TAG, "onNestedPreScroll: " + mTotalUnconsumed);
        }
    }
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //Log.d(TAG, "onNestedScroll" + dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.d(TAG, "onStopNestedScroll");
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling");
        return false;
    }
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling");
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.d(TAG, "getNestedScrollAxes");
        return mParentHelper.getNestedScrollAxes();
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    public static final int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}