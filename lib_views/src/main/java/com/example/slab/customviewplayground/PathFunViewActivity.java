package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.example.slab.customviewplayground.view.PathFunView2Progressing;

public class PathFunViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_fun_view);
        AppCompatSeekBar seekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);
        final PathFunView2Progressing pathfun = (PathFunView2Progressing) findViewById(R.id.pathfun);
        assert seekBar != null;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pathfun.setFraction((progress % 100)* 0.01f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
