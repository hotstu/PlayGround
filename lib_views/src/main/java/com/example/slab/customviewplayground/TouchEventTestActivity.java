package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by hotstuNg on 2016/7/10.
 */
public class TouchEventTestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchevent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TouchEventTestActivity", "onTouchEvent " + event);
        return false;
    }
}
