package io.github.hotstu.graphicfun;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import io.github.hotstu.graphicfun.bezier.BezierView;

/**
 * Created by hotstuNg on 2016/7/10.
 */
public class BezierViewActivity extends AppCompatActivity{
    BezierView bv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        bv = (BezierView) findViewById(R.id.bv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("BezierViewActivity", "onTouchEvent " + event);
        return false;
    }

    public void addlevel(View view) {
        bv.setLevel(Math.min(25, bv.getLevel() + 1));
    }

    public void dellevel(View view) {
        bv.setLevel(Math.max(1, bv.getLevel() - 1));
    }

    public void accelerate(View view) {
        bv.setDuration(Math.max(1000, bv.getDuration() - 1000));
    }

    public void decelerate(View view) {
        bv.setDuration(bv.getDuration() + 1000);
    }
}
