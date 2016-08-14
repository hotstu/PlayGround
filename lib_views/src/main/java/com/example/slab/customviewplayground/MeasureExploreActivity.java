package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.slab.loader.activities.BaseLogActivity;
import com.example.slab.loader.logger.Log;

public class MeasureExploreActivity extends BaseLogActivity {

    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_explore);
        v = new TextView(this);
        TextView t = (TextView) v;
        t.setText("123123123");
        t.setTextSize(10);
        v.setLayoutParams(new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void atmost(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.AT_MOST);
        v.measure(spec, spec);
        Log.d(TAG, "after onMeasure()  : " + "width = [" + v.getMeasuredWidth() + "], height = [" + v.getMeasuredHeight() + "]");

    }

    public void exactly(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        v.measure(spec, spec);
        Log.d(TAG, "after onMeasure()  : " + "width = [" + v.getMeasuredWidth() + "], height = [" + v.getMeasuredHeight() + "]");

    }

    public void undefined(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.UNSPECIFIED);
        v.measure(spec, spec);
        Log.d(TAG, "after onMeasure()  : " + "width = [" + v.getMeasuredWidth() + "], height = [" + v.getMeasuredHeight() + "]");

    }
}
