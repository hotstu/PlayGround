package com.example.slab.customviewplayground.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.Scroller;


/**
 * Created by hotstuNg on 2016/7/10.
 */
public class MyFrameLayout extends FrameLayout {
    private static final String TAG = MyFrameLayout.class.getSimpleName();
    private View mTarget;
    private int mTouchSlop;
    private float bounceFraction = 0.55f;
    private Scroller mScroller;
    private int refreshTriggerOffset = 0;
    private RefreshView mRefreshView;

    public MyFrameLayout(Context context) {
        this(context, null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        float density = getResources().getDisplayMetrics().density;
        refreshTriggerOffset = (int) (56*density);// 32dp
        mRefreshView = new RefreshView(getContext());
        addView(mRefreshView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Log.d(TAG, "dispatchTouchEvent start:" + ev) ;
        boolean r = super.dispatchTouchEvent(ev);
        //Log.d(TAG, "dispatchTouchEvent end: " + r + "," + ev) ;
        return r;
    }

    float startX;
    float startY;
    boolean isDraging;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        if(!isEnabled() || canTargetSrollUp()) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }
        Log.d(TAG, "onInterceptTouchEvent: " + ev);

        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                lastX = startX;
                lastY = startY;
                isDraging = false;
                return false;
            case MotionEvent.ACTION_MOVE: {
                final float dx = ev.getX() - startX;
                final float dy = ev.getY() - startY;
                startDrag(dy, dx);
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDraging =  false;
                break;
        }
        return isDraging;
    }

    /**
     * 是否计算了refreshview的初始偏移量， 偏移量= -mRefreshView.getMeasuredHeight()
     */
    boolean mOriginalOffsetCalculated = false;
    /**
     * refreshview 当前位置
     */
    int mCurrentOffsetTop;
    /**
     * refreshview 初始位置
     */
    int mOriginalOffsetTop;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mOriginalOffsetCalculated) {
            mOriginalOffsetCalculated = true;
            mCurrentOffsetTop = mOriginalOffsetTop = -mRefreshView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        int circleWidth = mRefreshView.getMeasuredWidth();
        int circleHeight = mRefreshView.getMeasuredHeight();
        mRefreshView.layout((width / 2 - circleWidth / 2), mCurrentOffsetTop,
                (width / 2 + circleWidth / 2), mCurrentOffsetTop + circleHeight);
    }

    private void startDrag(float dy, float dx) {
        if (Math.abs(dy) < Math.abs(dx)) {
            return;
        }
        if( dy > mTouchSlop) {
            //mRefreshView.stop();
            isDraging = true;
        }

        //当已经下拉了一段距离又向上释放的时候触发
        if (dy < -mTouchSlop && totalOffset > 0) {
            //mRefreshView.stop();
            isDraging = true;
        }
    }


    float lastX;
    float lastY;
    int totalOffset;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if(!isEnabled() || canTargetSrollUp()) {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getX();
                lastY = ev.getY();
                isDraging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                float dy = ev.getY() - lastY;
                if (dy >= 0) {
                    //向下拉(阻尼效果)
                    moveTargetBy(dy * bounceFraction);
                } else {
                    //向上释放（复原）
                    moveTargetBy(dy);
                }
                lastY = ev.getY();
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP: {
                isDraging = false;
                banceBack();
                return false;
            }
            case MotionEvent.ACTION_CANCEL:
                isDraging = false;
                return false;
        }
        return true;

    }

    private void ensureTarget(){
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mRefreshView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    /**
     * @return Whether the SwipeRefreshWidget is actively showing refresh
     *         progress.
     */
    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    private boolean canTargetSrollUp() {
        if (mTarget == null) {
            return false;
        }
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

    private void moveTargetBy(float dy) {
        int target = (int) (totalOffset + dy);
        int offset = target - totalOffset;
        totalOffset = target;
        if (totalOffset < 0) {
            //不允许列表向上超出界限
            offset += Math.abs(totalOffset);
            totalOffset = 0;
        }
        Log.d(TAG, "totalOffset=" + totalOffset);
        ViewCompat.offsetTopAndBottom(mTarget, offset);
        ViewCompat.offsetTopAndBottom(mRefreshView, offset);
        if (isDraging && !isRefreshing()) {
            mRefreshView.onProgress(totalOffset/(float)refreshTriggerOffset);
        }
    }

    private void moveTargetTo(int transition) {
        int offset = transition - totalOffset;
        totalOffset = transition;
        if (totalOffset < 0) {
            offset += Math.abs(totalOffset);
            totalOffset = 0;
        }
        Log.d(TAG, "totalOffset=" + totalOffset);
        ViewCompat.offsetTopAndBottom(mTarget, offset);
        ViewCompat.offsetTopAndBottom(mRefreshView, offset);
        if (isDraging && !isRefreshing()) {
            mRefreshView.onProgress(totalOffset/(float)refreshTriggerOffset);
        }
    }

    boolean needTriggerRefreshEvent = false;
    boolean mIsRefreshing = false;
    private void banceBack() {
        if (totalOffset > refreshTriggerOffset) {
            mScroller.startScroll(0,  totalOffset, 0, (refreshTriggerOffset - totalOffset));
            needTriggerRefreshEvent = true;
        } else {
            mScroller.startScroll(0,  totalOffset, 0, (0-totalOffset));
        }
        invalidate();
    }

    private void resetInner() {
        mScroller.startScroll(0,  totalOffset, 0, (0-totalOffset));
        mIsRefreshing = false;
        mRefreshView.stop();
        invalidate();
    }

    public void reset() {
        resetInner();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            moveTargetTo(mScroller.getCurrY());
            invalidate();
        } else if (needTriggerRefreshEvent) {
            needTriggerRefreshEvent = false;
            if (!mIsRefreshing) {
                doRefresh();
            }
        }
    }

    private void doRefresh() {
        mIsRefreshing = true;
        mRefreshView.doRefresh();
        Log.e(TAG, "trigger refresh event");
        if (this.mListener != null) {
            this.mListener.onRefresh();
        }
    }

    OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener l) {
        this.mListener = l;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    //region deal with compat, copy from SwipRefreshLayout
    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
            // Nope.
        } else {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }
    //endregion

    public static class RefreshView extends android.support.v7.widget.AppCompatImageView {
        private static final int MAX_ALPHA = 255;
        private static final float TRIM_RATE = 0.85f;

        private MyMaterialProgressDrawable mProgress;

        public RefreshView(Context context) {
            super(context);
            mProgress = new MyMaterialProgressDrawable(getContext(), this);
            mProgress.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
            mProgress.updateSizes(MyMaterialProgressDrawable.LARGE);
            mProgress.setAlpha(MAX_ALPHA);
            mProgress.setArrowScale(1.1f);
            setImageDrawable(mProgress);
        }

        public void onProgress(float progress) {
            progress *=.5f;//速度放慢
            float end = TRIM_RATE * (progress > 1? 1: progress);
            mProgress.showArrow(true);
            mProgress.setStartEndTrim(0, end);
            //mProgress.setAlpha((int) (MAX_ALPHA * end));
            mProgress.setProgressRotation(progress);
        }


        public void stop() {
            mProgress.stop();
        }

        public void doRefresh() {
            mProgress.showArrow(false);
            mProgress.start();
        }

        public void setColorSchemeResources(@ColorRes int... colorResIds) {
            final Context context = getContext();
            int[] colorRes = new int[colorResIds.length];
            for (int i = 0; i < colorResIds.length; i++) {
                colorRes[i] = ContextCompat.getColor(context, colorResIds[i]);
            }
            setColorSchemeColors(colorRes);
        }

        public void setColorSchemeColors(@ColorInt int... colors) {
            mProgress.setColorSchemeColors(colors);
        }
    }
}
