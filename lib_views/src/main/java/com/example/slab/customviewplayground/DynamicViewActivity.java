package com.example.slab.customviewplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.slab.customviewplayground.view.DynamicVIew;

public class DynamicViewActivity extends AppCompatActivity {

    private DynamicVIew dynamicVIew;
    private View textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);
        dynamicVIew = DynamicVIew.attach2Window(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicVIew.inspect(textView, "+1s");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void xu5(View view) {
        dynamicVIew.inspect(view, "+5s");
    }

    public void xu10(View view) {
        dynamicVIew.inspect(view, "+10s");
    }
}
