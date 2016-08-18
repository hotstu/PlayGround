package com.example.slab.labrecycler.decor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.slab.labrecycler.R;

/**
 * Created by hotstuNg on 2016/8/17.
 */
public class VerticalStepItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint mPaint;
    private final Paint mPaintDot;
    private float density;
    /** 圆形状态指示图标的半径**/
    private int widthPixel;
    /** 圆形状态指示图标 **/
    @Nullable private Drawable stateIndicator;
    private final Rect r;
    /** 是否未启动在下方**/
    private boolean reversed = false;
    /** 圆下的背景， 用于遮盖直线**/
    private int background;

    //region constructor
    public VerticalStepItemDecoration(Context context) {
        super();
        density = context.getResources().getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(new int[]{R.attr.verticalStemItemDecorationStyle});
        int resourceId = a.getResourceId(0, -1);
        a.recycle();
        if (resourceId != -1) {
            a = context.obtainStyledAttributes(resourceId, R.styleable.verticalStemItemDecoration);
        } else {
            a = context.obtainStyledAttributes(R.styleable.verticalStemItemDecoration);
        }
        setWidthPixel(a.getDimensionPixelSize(R.styleable.verticalStemItemDecoration_slab_width,
                ((int) (density * 20))));
        Drawable temp = a.getDrawable(R.styleable.verticalStemItemDecoration_slab_drawable);
        setStateIndicator( temp);
        background = a.getColor(R.styleable.verticalStemItemDecoration_slab_background, Color.TRANSPARENT);
        a.recycle();

        r = new Rect();

        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);

        mPaintDot = new Paint();
        mPaintDot.setStrokeWidth(4);
        mPaintDot.setStyle(Paint.Style.STROKE);
        mPaintDot.setColor(Color.WHITE);
        mPaintDot.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
    }
    //endregion

    //region property
    public void setWidthPixel(int widthPixel) {
        this.widthPixel = widthPixel;
    }

    public int getWidthPixel() {
        return widthPixel;
    }

    public void setStateIndicator(@Nullable Drawable indicator) {
        this.stateIndicator = indicator;
    }

    @Nullable public Drawable getStateIndicator() {
        return stateIndicator;
    }

    /**
     *
     * @param reversed
     */
    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public boolean isReversed() {
        return reversed;
    }

    //endregion

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = left + getWidthPixel();
        final int childCount = parent.getChildCount();
        Path mPath = new Path();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int top = child.getTop();
            final int bottom = child.getBottom();
            RecyclerView.ViewHolder vh = parent.getChildViewHolder(child);
            Stepable step = (Stepable) vh;

            r.set(0, 0, getWidthPixel(), getWidthPixel());
            r.offset(((int) ((right + left) * .5f - r.centerX())),
                    ((int) (( top)  - r.top)));
            //c.drawRect(left, top, parent.getWidth() - parent.getPaddingRight(), bottom, mPaintDot);
            int level = (int) (100 * step.getCompleteFraction());

            mPath.reset();
            //画线
            if (vh.getAdapterPosition() == 0) {
                mPath.moveTo(r.centerX(), r.centerY());
                mPath.lineTo(r.centerX(), bottom);
            }
            else if (vh.getAdapterPosition() == parent.getAdapter().getItemCount() - 1) {
                mPath.moveTo(r.centerX(), top);
                mPath.lineTo(r.centerX(), r.centerY());
            } else {
                mPath.moveTo(r.centerX(), top);
                mPath.lineTo(r.centerX(), bottom);
            }
            mPaint.setColor(Color.WHITE);
            //如果已完成（大于10000） 或者完成中并且未开始的在上方 则画实线
            c.drawPath(mPath, (level > 0 && !isReversed()) || level >=10000 ? mPaint: mPaintDot);
            mPaint.setColor(background);
            c.drawCircle(r.centerX(), r.centerY(), getWidthPixel()*.38f, mPaint);
            //画圆
            if (stateIndicator != null) {
                stateIndicator.setLevel(level);
                stateIndicator.setBounds(r);
                stateIndicator.draw(c);
            }

        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = widthPixel;
        //如果view的高度达不到要的宽度，需要加上一些padding
        //TODO 无法获取view的高宽，因为没有测量
    }
}
