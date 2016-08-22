package io.github.hotstu.graphicfun;

import android.animation.ValueAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.github.hotstu.graphicfun.noiseEffect.AnimatorHelper;
import io.github.hotstu.graphicfun.noiseEffect.EffectImageView;
import io.github.hotstu.graphicfun.noiseEffect.NoiseEffect;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EffectImageView iv = (EffectImageView) findViewById(R.id.image);
        iv.attachEffect(new NoiseEffect(
                (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.noise), 2f));
        iv.attachEffect(new NoiseEffect(
                (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.noise_scratch), 2f));
        //iv.setCameraDistance(10100);
        //iv.setRotationX(30f);
        iv.setOnClickListener(this);
        valueAnimator = ValueAnimator.ofInt(0, 1, 2, 3, 4, 5,6,7,8);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastValue = -1;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                if (current != lastValue) {
                    iv.update();
                }
                lastValue = current;
                Log.d("onAnimationUpdate",   "animation = [" + animation.getAnimatedValue() + "]");
            }
        });
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDestroy() {
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
            valueAnimator = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        AnimatorHelper.exitAnimate(v, 300, 300, 300);
    }
}
