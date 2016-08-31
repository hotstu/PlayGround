//package io.github.hotstu.graphicfun.bezier;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PointF;
//import android.graphics.RectF;
//import android.os.Build;
//import android.support.v4.view.ViewCompat;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//
///**
// * Created by hotstuNg on 2016/8/24.
// */
//
//public class BezierWaterDropView2 extends View {
//    private static final String TAG = "BezierWaterDropView";
//    private final int originalWidth = 200;
//    private final int originalHeight = 200;
//    private Paint mPaint;
//    float ar = 50;
//    float ax = 80;
//    float ay = 80;
//    float br = 100;
//    float bx = 200;
//    float by = 200;
//    Matrix scaleMatrix = new Matrix();
//    Path mBezierPath = new Path();
//    float scale = 1;
//    private Paint mTextPaint;
//
//    //region const
//    public BezierWaterDropView2(Context context) {
//        super(context);
//        init();
//    }
//
//    public BezierWaterDropView2(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public BezierWaterDropView2(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public BezierWaterDropView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }
//
//    //endregion
//    //region override method
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(
//                resolveSize(originalWidth + getPaddingLeft() + getPaddingRight(), widthMeasureSpec),
//                resolveSize(originalHeight + getPaddingTop()+ getPaddingBottom(), heightMeasureSpec));
//
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
//        float horizialScale = (float) w/originalWidth;
//        float verticalScale = (float) h/originalHeight;
//        scale = Math.min(horizialScale, verticalScale);
//        //居中并对齐长宽的较小者
//        //scaleMatrix.setScale(1, 1);
//        scaleMatrix.reset();
//        scaleMatrix.postTranslate((w - originalWidth) >> 1, (h - originalHeight) >> 1);
//        scaleMatrix.preScale(scale, scale, originalWidth>>1, originalHeight>>1);
//
//    }
//
//    //endregion
//    private void init() {
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.BLUE);
//        mTextPaint = new Paint(mPaint);
//        mTextPaint.setColor(Color.RED);
//    }
//
//
//    private int capturedPoint = -1;
//    private float lastX = -1;
//    private float lastY = -1;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                capturedPoint = 1;
//                lastX = x;
//                lastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (capturedPoint == 1) {
//                    ciclerB.offset((x - lastX)/scale, (y - lastY)/scale);
//                    lastX = x;
//                    lastY = y;
//                    ViewCompat.postInvalidateOnAnimation(this);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                capturedPoint = -1;
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.save();
//        //canvas.translate(getPaddingLeft(), getPaddingTop());
//        canvas.concat(scaleMatrix);
//        canvas.drawRect(0, 0, originalWidth, originalHeight, mPaint);
//        canvas.drawCircle(ax, ay, ar, mPaint);
//        canvas.drawCircle(bx, by, br, mPaint);
//        drawBezierPath2(canvas);
//        canvas.restore();
//    }
//
//    private int _status = 1;
//    private int _maxDistance = 200;
//    private void drawBezierPath2(Canvas canvas) {
//        float _X2 = ciclerB.centerX();
//        float _Y2 = ciclerB.centerY();
//        float _X1 = ciclerA.centerX();
//        float _Y1 = ciclerA.centerY();
//        float _centerDistance = (float) Math.sqrt((_X2 - _X1)*(_X2 - _X1) + (_Y2 - _Y1)*(_Y2 - _Y1));
//
//        if (_centerDistance > _maxDistance) {
//            _status = -1;
//        } else {
//            _status = 1;
//        }
//        if (_status == -1) {
//            return;
//        }
//        float _cosDigree;
//        float _sinDigree;
//        if (_centerDistance == 0) {
//            _cosDigree = 1;
//            _sinDigree = 0;
//        } else {
//            _cosDigree = (_Y2 - _Y1)/_centerDistance;
//            _sinDigree = (_X2 - _X1)/_centerDistance;
//        }
//        float _percentage = _centerDistance/_maxDistance;
//        _R1 = (2 - _percentage/2)*_bubbleWidth/4;
//        _offset1 = _R1*2/3.6;
//        _offset2 = _R2*2/3.6;
//        _pointA = CGPointMake(_X1 - _R1*_cosDigree, _Y1 + _R1*_sinDigree);
//        _pointB = CGPointMake(_X1 + _R1*_cosDigree, _Y1 - _R1*_sinDigree);
//        _pointE = CGPointMake(_X1 - _R1*_sinDigree, _Y1 - _R1*_cosDigree);
//        _pointC = CGPointMake(_X2 + _R2*_cosDigree, _Y2 - _R2*_sinDigree);
//        _pointD = CGPointMake(_X2 - _R2*_cosDigree, _Y2 + _R2*_sinDigree);
//        _pointF = CGPointMake(_X2 + _R2*_sinDigree, _Y2 + _R2*_cosDigree);
//
//        _pointEA2 = CGPointMake(_pointA.x - _offset1*_sinDigree, _pointA.y - _offset1*_cosDigree);
//        _pointEA1 = CGPointMake(_pointE.x - _offset1*_cosDigree, _pointE.y + _offset1*_sinDigree);
//        _pointBE2 = CGPointMake(_pointE.x + _offset1*_cosDigree, _pointE.y - _offset1*_sinDigree);
//        _pointBE1 = CGPointMake(_pointB.x - _offset1*_sinDigree, _pointB.y - _offset1*_cosDigree);
//
//        _pointFC2 = CGPointMake(_pointC.x + _offset2*_sinDigree, _pointC.y + _offset2*_cosDigree);
//        _pointFC1 = CGPointMake(_pointF.x + _offset2*_cosDigree, _pointF.y - _offset2*_sinDigree);
//        _pointDF2 = CGPointMake(_pointF.x - _offset2*_cosDigree, _pointF.y + _offset2*_sinDigree);
//        _pointDF1 = CGPointMake(_pointD.x + _offset2*_sinDigree, _pointD.y + _offset2*_cosDigree);
//
//        _pointTemp = CGPointMake(_pointD.x + _percentage*(_X2 - _pointD.x), _pointD.y + _percentage*(_Y2 - _pointD.y));//关键点
//        _pointTemp2 = CGPointMake(_pointD.x + (2 - _percentage)*(_X2 - _pointD.x), _pointD.y + (2 - _percentage)*(_Y2 - _pointD.y));
//
//        _pointO = CGPointMake(_pointA.x + (_pointTemp.x - _pointA.x)/2, _pointA.y + (_pointTemp.y - _pointA.y)/2);
//        _pointP = CGPointMake(_pointB.x + (_pointTemp2.x - _pointB.x)/2, _pointB.y + (_pointTemp2.y - _pointB.y)/2);
//
//        _offset1 = _centerDistance/8;
//        _offset2 =_centerDistance/8;
//
//        _pointAO1 = CGPointMake(_pointA.x + _offset1*_sinDigree, _pointA.y + _offset1*_cosDigree);
//        _pointAO2 = CGPointMake(_pointO.x - (3*_offset2-_offset1)*_sinDigree, _pointO.y - (3*_offset2-_offset1)*_cosDigree);
//        _pointOD1 = CGPointMake(_pointO.x + 2*_offset2*_sinDigree, _pointO.y + 2*_offset2*_cosDigree);
//        _pointOD2 = CGPointMake(_pointD.x - _offset2*_sinDigree, _pointD.y - _offset2*_cosDigree);
//
//        _pointCP1 = CGPointMake(_pointC.x - _offset2*_sinDigree, _pointC.y - _offset2*_cosDigree);
//        _pointCP2 = CGPointMake(_pointP.x + 2*_offset2*_sinDigree, _pointP.y + 2*_offset2*_cosDigree);
//        _pointPB1 = CGPointMake(_pointP.x - (3*_offset2-_offset1)*_sinDigree, _pointP.y - (3*_offset2-_offset1)*_cosDigree);
//        _pointPB2 = CGPointMake(_pointB.x + _offset1*_sinDigree, _pointB.y + _offset1*_cosDigree);
//
//        //  测试用代码
////    UIBezierPath *helperLine = [UIBezierPath bezierPath];
////    [helperLine moveToPoint:_pointA];
////    [helperLine addLineToPoint:_pointAO1];
////    [helperLine addLineToPoint:_pointAO2];
////    [helperLine addLineToPoint:_pointP];
////    [helperLine addLineToPoint:_pointOD1];
////    [helperLine addLineToPoint:_pointOD2];
////    [helperLine addLineToPoint:_pointD];
////    [helperLine addLineToPoint:_pointC];
////    [helperLine addLineToPoint:_pointCP1];
////    [helperLine addLineToPoint:_pointCP2];
////    [helperLine addLineToPoint:_pointO];
////    [helperLine addLineToPoint:_pointPB1];
////    [helperLine addLineToPoint:_pointPB2];
////    [helperLine addLineToPoint:_pointB];
////    [helperLine addLineToPoint:_pointA];
////    [helperLine closePath];
//
//        _cutePath = [UIBezierPath bezierPath];
//        [_cutePath moveToPoint:_pointB];
//        [_cutePath addCurveToPoint:_pointE controlPoint1:_pointBE1 controlPoint2:_pointBE2];
//        [_cutePath addCurveToPoint:_pointA controlPoint1:_pointEA1 controlPoint2:_pointEA2];
//        [_cutePath addCurveToPoint:_pointO controlPoint1:_pointAO1 controlPoint2:_pointAO2];
//        [_cutePath addCurveToPoint:_pointD controlPoint1:_pointOD1 controlPoint2:_pointOD2];
//
//        [_cutePath addCurveToPoint:_pointF controlPoint1:_pointDF1 controlPoint2:_pointDF2];
//        [_cutePath addCurveToPoint:_pointC controlPoint1:_pointFC1 controlPoint2:_pointFC2];
//        [_cutePath addCurveToPoint:_pointP controlPoint1:_pointCP1 controlPoint2:_pointCP2];
//        [_cutePath addCurveToPoint:_pointB controlPoint1:_pointPB1 controlPoint2:_pointPB2];
//    }
//
//}
