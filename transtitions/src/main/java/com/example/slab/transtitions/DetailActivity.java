package com.example.slab.transtitions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int resid = getIntent().getIntExtra("image", -1);
        ((ImageView) findViewById(R.id.image)).setImageResource(resid);
    }
}
