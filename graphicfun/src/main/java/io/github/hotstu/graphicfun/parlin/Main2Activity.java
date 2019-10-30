package io.github.hotstu.graphicfun.parlin;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import io.github.hotstu.graphicfun.R;

import static android.animation.ValueAnimator.INFINITE;

public class Main2Activity extends AppCompatActivity {

    private ValueAnimator valueAnimator;
    private WaveDrawable background;
    private WaveDrawable background2;
    private ExpandingBarDrawable background3;
    private WaveDrawable background4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        background = new WaveDrawable();
        background.setWaveStrength(.05f);
        background2 = new CircleWaveDrawable();
        background2.setWaveSpeed(.1f);
        background3 = new ExpandingBarDrawable();
        background4 = new WaveDrawable();
        background4.setDirection(WaveDrawable.RIGHT);
        background.setColor(Color.parseColor("#90CAF9"));
        background2.setColor(Color.parseColor("#90CAF9"));
        background3.setColor(Color.parseColor("#90CAF9"));
        background4.setColor(Color.parseColor("#90CAF9"));
        findViewById(R.id.view).setBackground(background);
        findViewById(R.id.view2).setBackground(background2);
        findViewById(R.id.view3).setBackground(background3);
        findViewById(R.id.view4).setBackground(background4);
        SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setProgress(progress * 0.01f);
                background2.setProgress(progress * 0.01f);
                background3.setProgress(progress * 0.01f);
                background4.setProgress(progress * 0.01f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //background.setColor(Color.parseColor("#f0f0f0"));
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(2000);
        //valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                background.setProgress(animation.getAnimatedFraction());
                background2.setProgress(animation.getAnimatedFraction());
                background3.setProgress(animation.getAnimatedFraction());
                background4.setProgress(animation.getAnimatedFraction());

            }
        });
        valueAnimator.setRepeatCount(INFINITE);

    }


    public void start(View v) {
        valueAnimator.start();
    }

    public void end(View v) {
        valueAnimator.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        valueAnimator.cancel();
    }

    public void animate(View view) {
        if (background.isRunning()) {
            background.stop();
            background2.stop();
            background4.stop();
        } else {
            background.start();
            background2.start();
            background4.start();
        }
    }
}
