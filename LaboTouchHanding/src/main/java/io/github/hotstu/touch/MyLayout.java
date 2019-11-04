package io.github.hotstu.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 10/31/19
 */
public class MyLayout extends ViewGroup {
    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }


    private static final int FLAG_HANDLE_SELF = 1 ;
    private static final int FLAG_HANDLE_CHILD = 1 << 1;
    private static final int FLAG_NOT_ALLOW_INTERCEPT = 1 << 2;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return customDispatchTouchEvent(ev);
    }

    /**
     * dispatchTouchEvent 伪代码
     * @param ev
     * @return
     */
    private boolean customDispatchTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        boolean handled = false;

        Log.d("MyLayout", "actionMasked:" + actionMasked);

        if (MotionEvent.ACTION_DOWN == actionMasked) {
            if (!hasFlag(FLAG_NOT_ALLOW_INTERCEPT) && onInterceptTouchEvent(ev)) {
                addFlag(FLAG_HANDLE_SELF);
                handled = onTouchEvent(ev);
            } else {
                handled = getChildAt(0).dispatchTouchEvent(ev);
                if (handled) {
                    addFlag(FLAG_HANDLE_SELF);
                } else {
                    handled = onTouchEvent(ev);
                    if (handled) {
                        addFlag(FLAG_HANDLE_SELF);
                    }
                }
            }

        } else {
            if (hasFlag(FLAG_HANDLE_SELF)) {
                handled = onTouchEvent(ev);
            }
            if (!handled) {
                if (hasFlag(FLAG_HANDLE_CHILD)) {
                    if (!hasFlag(FLAG_NOT_ALLOW_INTERCEPT) && onInterceptTouchEvent(ev)) {
                        addFlag(FLAG_HANDLE_SELF);
                        // add cancel
                        MotionEvent cancel = MotionEvent.obtain(ev);
                        cancel.setAction(MotionEvent.ACTION_CANCEL);
                        handled = getChildAt(0).dispatchTouchEvent(cancel);

                        //handled = onTouchEvent(ev);
                    } else {
                        handled = getChildAt(0).dispatchTouchEvent(ev);
                    }

                } else {
                    //NOOP
                    //child rejected event already
                }
            }
        }
        if (actionMasked == MotionEvent.ACTION_UP|| MotionEvent.ACTION_CANCEL == actionMasked) {
            clearStatus();
        }
        return handled;
    }




    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private int flagkeep = 0;
    private void clearStatus() {
        removeFlag(FLAG_HANDLE_CHILD);
        removeFlag(FLAG_HANDLE_SELF);
        removeFlag(FLAG_NOT_ALLOW_INTERCEPT);
    }

    private void addFlag(int flag) {
        flagkeep |= flag;
    }

    private void removeFlag(int flag) {
        flagkeep &= ~flag;
    }

    private boolean hasFlag(int flag) {
        return (flagkeep & flag) != 0;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        final View child = getChildAt(0);
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
    }
}
