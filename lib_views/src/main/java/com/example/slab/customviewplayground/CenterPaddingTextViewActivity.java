package com.example.slab.customviewplayground;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.style.ReplacementSpan;

public class CenterPaddingTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_padding_text_view);
        //s.setSpan(new PlaceHolderSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //TextView tv = findViewById(R.id.text);

    }

    static class PlaceHolderSpan extends ReplacementSpan {

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
            return 500;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {

        }
    }
}
