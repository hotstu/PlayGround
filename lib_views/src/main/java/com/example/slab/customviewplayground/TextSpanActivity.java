package com.example.slab.customviewplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.widget.TextView;

import com.example.slab.customviewplayground.text.RazerSpan;

public class TextSpanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        TextView tvSpan = (TextView) findViewById(R.id.text);
        SpannableString str = new SpannableString("hello, world");
        str.setSpan(new RazerSpan(), 7, 9, SpannableString.SPAN_MARK_MARK);
        tvSpan.setText(str);
    }
}
