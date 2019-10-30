package com.example.slab.customviewplayground;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Point;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

public class DragActivity1 extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener {


    private static final String TAG = "DragActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag1);
        View v = findViewById(R.id.hello);
        v.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData data = ClipData.newPlainText("label","hello, clipdata");
        v.setOnDragListener(this);
        v.startDrag(data, new View.DragShadowBuilder(v){
            @Override
            public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            }

            @Override
            public void onDrawShadow(Canvas canvas) {
                super.onDrawShadow(canvas);
            }
        }, null, 0);
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Log.d(TAG, "onDrag() called with: " + "v = [" + v + "], event = [" + event + "]");
        return true;
    }
}
