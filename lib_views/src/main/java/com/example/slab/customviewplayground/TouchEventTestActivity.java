package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.slab.customviewplayground.view.BezierView;

/**
 * Created by hotstuNg on 2016/7/10.
 */
public class TouchEventTestActivity extends AppCompatActivity{
    BezierView bv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchevent);
        bv = (BezierView) findViewById(R.id.bv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TouchEventTestActivity", "onTouchEvent " + event);
        return false;
    }

    public void addlevel(View view) {
        bv.setLevel(Math.min(25, bv.getLevel() + 1));
    }

    public void dellevel(View view) {
        bv.setLevel(Math.max(1, bv.getLevel() - 1));
    }
}
