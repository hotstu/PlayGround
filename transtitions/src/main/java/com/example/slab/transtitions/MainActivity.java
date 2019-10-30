package com.example.slab.transtitions;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 使用TransitionManager.beginDelayedTransition
 */
public class MainActivity extends AppCompatActivity {
    ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(root);

    }

    public void onClicnavibar(View view) {
        View navibar = findViewById(android.R.id.navigationBarBackground);
        System.out.println(navibar + "");
        navibar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    public void onClickhello(View view) {
        TransitionManager.beginDelayedTransition(root);//重要，会记录当前帧和下一帧的改变，创建动画效果
        TextView tv = (TextView) findViewById(R.id.hello);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        //layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        //layoutParams.bottomMargin = layoutParams.bottomMargin+200;
        if (layoutParams.getRules()[RelativeLayout.ALIGN_PARENT_BOTTOM] == 0) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

        } else  {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, -1);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        }
        layoutParams.width =
                (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) ?
                        ViewGroup.LayoutParams.WRAP_CONTENT: ViewGroup.LayoutParams.MATCH_PARENT;
        tv.setLayoutParams(layoutParams);
        //setContentView(R.layout.item_list);
    }
}
