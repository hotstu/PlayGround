package com.example.slab.desionlib;

import android.os.Bundle;
import androidx.annotation.IdRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        Toolbar toolbar = findView(R.id.toolbar);
        //TabLayout tabLayout = findView(R.id.tabLayout);
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));

        setSupportActionBar(toolbar);
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
