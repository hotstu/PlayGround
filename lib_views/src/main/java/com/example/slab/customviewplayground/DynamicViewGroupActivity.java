package com.example.slab.customviewplayground;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DynamicViewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_view_group);
        //DynamicViewGroup.attach2Window(this);
    }
}
