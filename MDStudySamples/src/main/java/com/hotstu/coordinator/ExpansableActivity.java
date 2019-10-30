package com.hotstu.coordinator;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yifeng.mdstudysamples.R;

public class ExpansableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansable);
        View bottomSheet = findViewById(R.id.bottumSheet);
        final MyBottomSheetBehavior<View> from = MyBottomSheetBehavior.from(bottomSheet);

        from.setBottomSheetCallback(new MyBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("s", "1--" + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d("s", "2--" + slideOffset);

            }
        });
    }
}
