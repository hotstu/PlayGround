package com.example.slab.customviewplayground;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.ViewCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.slab.loader.activities.BaseLogActivity;
import com.example.slab.loader.logger.Log;

public class MainActivity extends BaseLogActivity implements View.OnClickListener {
    View target;
    Scroller mScroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        target = findViewById(android.R.id.text1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mScroller = new Scroller(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int l = target.getLeft();
        int r = target.getRight();
        float x = target.getX();
        float y = target.getY();
        float tx = target.getTranslationX();
        float ty = target.getTranslationY();
        int sx = target.getScrollX();
        int sy = target.getScrollY();
        Log.d("onClick", String.format("l=%d,r=%d,x=%f,y=%f,tx=%f,ty=%f,sx=%d,sy=%d", l,r,x, y, tx, ty, sx, sy));
    }

    public void scroll(View btnScroll) {
        target.setScrollX(target.getScrollX() + 10);
        //target.invalidate();
    }

    public void translationx(View btnTranslationx) {
        ViewCompat.setTranslationX(target, ViewCompat.getTranslationX(target) + 10);
        //target.invalidate();
    }

    public void layoutMargin(View btnLayoutMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) target.getLayoutParams();
        params.leftMargin += 10;
        target.requestLayout();
    }


}
