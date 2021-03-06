package com.example.slab.customviewplayground;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.slab.customviewplayground.view.DynamicVIew;

public class DynamicViewActivity extends AppCompatActivity {

    private DynamicVIew dynamicVIew;
    private View textView;
    private String[] crappy = {"富强","民主","文明"," 和谐"," 自由","平等","公正","法治","爱国","敬业","诚信","友善"};
    private int count = 0;
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
        dynamicVIew.inspect(view, crappy[count]);
        count = (count + 1)% crappy.length;
    }
}
