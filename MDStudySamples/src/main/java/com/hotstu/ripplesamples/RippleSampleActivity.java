package com.hotstu.ripplesamples;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yifeng.mdstudysamples.R;

public class RippleSampleActivity extends AppCompatActivity {

    private static final String TAG = RippleSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_sample);

    }

    @Override
    public void onBackPressed() {
        boolean b = navigateUpTo(NavUtils.getParentActivityIntent(this));
        Log.d(TAG, "" + b);
    }
}
