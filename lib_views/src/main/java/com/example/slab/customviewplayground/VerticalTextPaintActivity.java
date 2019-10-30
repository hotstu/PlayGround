package com.example.slab.customviewplayground;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slab.customviewplayground.view.VTextView;

public class VerticalTextPaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_text_paint);
        VTextView vTv = (VTextView) findViewById(R.id.vtv);
        vTv.setText("苟利国家，生死以\n岂因福祸避趋之");
    }
}
