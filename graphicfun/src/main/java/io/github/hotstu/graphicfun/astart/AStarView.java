package io.github.hotstu.graphicfun.astart;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by hotstuNg on 2016/9/4.
 */

public class AStarView extends View {
    private static final String TAG = "AStarView";
    public static final int SELECT_TARGET_START = 0;
    public static final int SELECT_TARET_END = 1;
    public static final int SELECT_TARGET_WALL = 2;

    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.PARAMETER, ElementType.FIELD})
    @IntDef(value = {SELECT_TARET_END,SELECT_TARGET_START, SELECT_TARGET_WALL})
    public @interface SelectMode {}
    int width;
    int height;
    float density = getResources().getDisplayMetrics().density;
    int colsNum = 12;
    int rowsNum = 15;
    float colWeight = 12;
    float rowWeight = 12;
    float colSpanWeight = 1;
    float rowSpanWeight = 1;
    @SelectMode int selectMode = SELECT_TARET_END;
    int from = colsNum + 2;
    int to = colsNum + 2;
    Unit[] units = new Unit[colsNum * rowsNum];
    Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GestureDetectorCompat mGesture;

    public AStarView(Context context) {
        super(context);
        init();
    }

    public AStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintRect.setStyle(Paint.Style.STROKE);
        mPaintRect.setStrokeWidth(4);
        mPaintRect.setColor(Color.BLUE);

        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setStrokeWidth(4);
        mPaintPath.setColor(Color.GREEN);

        mPaintText.setTextSize(density * 12);
        mPaintText.setColor(Color.RED);
        mGesture = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                boolean reCompute = false;
                for (int i = 0; i < units.length; i++) {
                    int row = i / colsNum;
                    int col = i % colsNum;
                    if (row == 0 || col == 0 || row == rowsNum - 1 || col == colsNum - 1) {
                        continue;
                    }
                    if (selectMode == SELECT_TARET_END
                            && !units[i].isWall && units[i].rect.contains(x, y)) {
                        to = i;
                        reCompute = true;
                        break;
                    }
                    if (selectMode == SELECT_TARGET_START
                            && !units[i].isWall && units[i].rect.contains(x, y)) {
                        from = i;
                        reCompute = true;
                        break;
                    }
                    if (selectMode == SELECT_TARGET_WALL
                            && i != from && i != to && units[i].rect.contains(x, y)) {
                            units[i].isWall = !units[i].isWall;
                            reCompute = true;
                            break;
                    }
                }
                if (reCompute) {
                    computePath(from, to);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
        computeGrid();
    }

    private void computeGrid() {
        float xUnit = width / ((colsNum * (rowWeight + rowSpanWeight)) + rowSpanWeight);
        float yUnit = height / ((rowsNum * (colWeight + colSpanWeight)) + colSpanWeight);
        float xshift = 0;
        float yshift = yUnit * colSpanWeight * .5f;
        float yWidth = yUnit * colWeight;
        float xWidth = xUnit * rowWeight;
        for (int i = 0; i < rowsNum; i++) {
            xshift = xUnit * rowSpanWeight * .5f;;
            for (int j = 0; j < colsNum; j++) {
                Unit temp = units[i*colsNum + j];
                if (temp == null) {
                    temp = new Unit();
                    units[i*colsNum + j] = temp;
                }
                if (i == 0 || j == 0 || i == rowsNum - 1 || j == colsNum - 1) {
                    temp.isWall = true;
                }
                temp.rect.set(xshift, yshift, xshift+ xWidth, yshift+ yWidth);
                xshift += xUnit * rowSpanWeight;
                xshift += xWidth;
            }
            yshift += yUnit * colSpanWeight;
            yshift += yWidth;
        }
    }

    Rect r = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        float textW = mPaintText.measureText("99");
        mPaintText.getTextBounds("99", 0, 2, r);
        float textH = r.height();
        for (Unit unit : units) {
            if (unit.isWall) {
                mPaintRect.setStyle(Paint.Style.FILL_AND_STROKE);
            }
            canvas.drawRect(unit.rect, mPaintRect);
            canvas.drawText(unit.G + "", unit.rect.right - textW, unit.rect.top + textH, mPaintText);
            canvas.drawText(unit.H + "", unit.rect.left, unit.rect.bottom - textH, mPaintText);
            if (unit.isWall) {
                mPaintRect.setStyle(Paint.Style.STROKE);
            }
        }

        if (pathList.size() >= 2) {
            Path path = new Path();
            path.moveTo(pathList.get(0).rect.centerX(), pathList.get(0).rect.centerY());
            for (int i = 1; i < pathList.size(); i++) {
                path.lineTo(pathList.get(i).rect.centerX(), pathList.get(i).rect.centerY());
            }
            PathMeasure pm = new PathMeasure(path, false);

            float[] tan = new float[2];
            float[] loc = new float[2];
            pm.getPosTan(0, loc, tan);
            float degree = (float) Math.toDegrees(Math.atan2(tan[1], tan[0]));
            canvas.save();
            canvas.translate(loc[0], loc[1]);
            canvas.rotate(degree +150);
            canvas.drawLine(0, 0, 30, 0, mPaintPath);
            canvas.rotate(-300);
            canvas.drawLine(0, 0, 30, 0, mPaintPath);
            canvas.restore();
            pm.getPosTan(pm.getLength(), loc, tan);
            degree = (float) Math.toDegrees(Math.atan2(tan[1], tan[0]));
            canvas.save();
            canvas.translate(loc[0], loc[1]);
            canvas.rotate(degree + 150);
            canvas.drawLine(0, 0, 30, 0, mPaintPath);
            canvas.rotate( -300);
            canvas.drawLine(0, 0, 30, 0, mPaintPath);
            canvas.restore();
            mPaintPath.setColor(Color.MAGENTA);
            canvas.drawPath(path, mPaintPath);
        }

    }


    public void setSelectMode(@SelectMode int mode) {
        this.selectMode = mode;
    }

    ArrayList<Unit> pathList = new ArrayList<>();
    private void computePath(int from, int to) {
        if (units[to].isWall || units[from].isWall) {
            Log.d(TAG, "computePath() called with: from = [" + from + "], to = [" + to + "]");
            return;
        }
        pathList.clear();
        int row = to / colsNum;
        int col = to % colsNum;
        for (int i = 0; i < units.length; i++) {
            Unit temp = units[i];
            temp.G = Math.abs(col - (i % colsNum)) + Math.abs(row - (i / colsNum));
            temp.H = 99;
            temp.parent = null;
        }
        computeIntenal(from, to);
        Unit swap = units[to];
        while (swap != null) {
            pathList.add(swap);
            swap = swap.parent;
        }
        Collections.reverse(pathList);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void computeIntenal(int from, int to) {
        PriorityQueue<Unit> open = new PriorityQueue<>(units.length / 2);
        ArrayList<Unit> close = new ArrayList<>();
        units[from].H = 0;
        units[from].indexInArray = from;
        open.add(units[from]);
        int[] directions = {1, -1, colsNum, -colsNum};
        int[] costs = {10, 10, 10, 10};//朝对应方向移动的代价
        while (!open.isEmpty()) {
            Unit least = open.poll();
            if (least.indexInArray == to) {
                break;
            }
            for (int i = 0; i < directions.length; i++) {
                int direction = directions[i];
                Unit temp = units[least.indexInArray + direction];
                if (temp.isWall) {
                    continue;
                }
                if (close.contains(temp)) {
                    continue;
                }
                int oldH = -1;
                if (open.contains(temp)) {
                    //如果在open列表中，需要更新
                    open.remove(temp);
                    oldH = temp.H;
                }
                if (oldH >= 0) {
                    temp.H = Math.min(oldH, least.H + costs[i]);
                    temp.parent = oldH < least.H + costs[i]?temp.parent: least;
                } else {
                    temp.H = least.H + costs[i];
                    temp.parent = least;
                }
                temp.indexInArray = least.indexInArray + direction;
                open.add(temp);
            }
            close.add(least);
        }

    }

    static class Unit implements Comparable<Unit>{
        int G;
        int H = 99;
        boolean isWall = false;
        int indexInArray = 0;
        RectF rect = new RectF();
        Unit parent;

        @Override
        public int compareTo(Unit another) {
            if (G + H == another.G + another.H)
                return 0;
            else
                return G + H > another.G + another.H? 1: -1;
        }
    }
}
