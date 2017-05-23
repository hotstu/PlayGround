package com.example.slab.propertyanim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.slab.loader.activities.BaseLogActivity;
import com.example.slab.loader.logger.Log;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

public class MainActivity extends BaseLogActivity {

    private ImageView image;
    private Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = ((ImageView) findViewById(R.id.image));
    }

    public void btn1(View view) {
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 2f, 1);
//        valueAnimator.setEvaluator(new FloatEvaluator());
//        valueAnimator.setDuration(500);
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.v("BTN1", animation.getAnimatedFraction() + ","
//                        + animation.getAnimatedValue());
//                image.setScaleX((Float) animation.getAnimatedValue());
//                image.setScaleY((Float) animation.getAnimatedValue());
//            }
//        });
//        //valueAnimator.setRepeatCount(1);
//        valueAnimator.start();
        Log.e("BTN1", "btn1: start");


// Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

// Add a spring to the system.
        Spring spring = springSystem.createSpring();

// Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                Log.e("BTN1", "btn1: start" + value);
                float scale = value;
                image.setScaleX(scale);
                image.setScaleY(scale);
            }
        });

// Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);


    }

    public void btn2(View view) {
        Log.v("BTN2", "rotation:"+ image.getRotation());
        image.setRotation(0);
        image.animate().rotation(360)
                .x(50f).y(100f)
                .start();
    }

    public void btn3(View view) {
        if (animator == null) {
            animator = AnimatorInflater.loadAnimator(this, R.animator.exp);
            animator.setTarget(image);
        }
        animator.start();
    }

    @Override
    protected void onDestroy() {
        if (animator != null && animator.isStarted()) {
            animator.cancel();
            animator = null;
        }
        super.onDestroy();
    }

    public void cancel(View view) {
        if (animator != null) {
            animator.cancel();
        }
    }

    public void end(View view) {
        if (animator != null) {
            animator.end();
        }
    }
}
