package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DynamicViewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_view_group);
        //DynamicViewGroup.attach2Window(this);
    }
}
