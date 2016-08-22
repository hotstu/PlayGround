package io.github.hotstu.graphicfun.noiseEffect;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by hotstuNg on 2016/8/21.
 */
public class AnimatorHelper {
    public static final float TARGET_SCALE = 0.5f;
    public static final float TARGET_ROTATION = -50f;
    public static final float TARGET_ROTATION_X = 60f;
    public static final int MOVE_Y_STEP = 15;
    public static final int DURATION = 1100;
    public static final int FISRTDELAY = 300;

    public static final QuintOut VALUEinterpolator = new QuintOut();

    public static class QuintOut implements TimeInterpolator {
        @Override
        public float getInterpolation(float t) {
            return (t-=1)*t*t*t*t + 1;
        }
    }
    public static ValueAnimator exitAnimate(final View target, final float moveY, long delay, int subtractDelay) {

        target.setPivotY(getDistanceToCenter(target));
        target.setPivotX(getDistanceToCenterX(target));
        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);

        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TARGET_ROTATION_X).setDuration(DURATION);
        rotationX.setInterpolator(VALUEinterpolator);
        rotationX.setStartDelay(delay);
        rotationX.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TARGET_SCALE).setDuration(DURATION);
        scaleX.setInterpolator(new QuintOut());
        scaleX.setStartDelay(delay);
        scaleX.start();

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TARGET_SCALE).setDuration(DURATION);
        scaleY.setInterpolator(new QuintOut());
        scaleY.setStartDelay(delay);
        scaleY.start();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TARGET_ROTATION).setDuration(1600);
        rotation.setInterpolator(VALUEinterpolator);
        rotation.setStartDelay(delay);
        rotation.start();

//        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * target.getResources().getDisplayMetrics().density).setDuration(subtractDelay);
//        translationY.setInterpolator(VALUEinterpolator);
//        translationY.setStartDelay(delay);
//        translationY.start();
        return scaleY;
    }

    public static float getDistanceToCenter(View target) {
        float viewCenter = target.getTop() + target.getHeight() / 2f;
        float rootCenter = ((View) target.getParent()).getHeight() / 2;
        return target.getHeight() / 2f + rootCenter - viewCenter;
    }

    public static float getDistanceToCenterX(View target) {
        float viewCenter = target.getLeft() + target.getWidth() / 2f;
        float rootCenter = ((View) target.getParent()).getWidth() / 2;
        return target.getWidth() / 2f + rootCenter - viewCenter;
    }
}
