package com.example.slab.desionlib;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class MDActivity extends AppCompatActivity {

    ViewGroup content;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);
        content = findView(R.id.content);
        fab = findView(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(content, "hello, snackbar", Snackbar.LENGTH_SHORT)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
    }

    private <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}
