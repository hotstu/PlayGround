package com.example.slab.labrecycler.dragable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.slab.labrecycler.R;

/**
 * Created by hotstuNg on 2016/8/16.
 */
public class TestActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        Log.d("TestActivity", "onCreate: ");
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        System.out.println(imageView.getDrawable().getClass());
        imageView.getDrawable().setLevel(-1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.getDrawable().setLevel((imageView.getDrawable().getLevel() + 1000) );
            }
        });
    }
}
