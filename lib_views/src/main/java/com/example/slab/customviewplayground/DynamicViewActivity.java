package com.example.slab.customviewplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.slab.customviewplayground.view.DynamicVIew;

public class DynamicViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_view);
        final DynamicVIew dynamicVIew = DynamicVIew.attach2Window(this);
        dynamicVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicVIew.detachFromWindow(DynamicViewActivity.this);
            }
        });
    }
}
