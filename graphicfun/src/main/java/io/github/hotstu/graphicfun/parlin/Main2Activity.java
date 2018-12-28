package io.github.hotstu.graphicfun.parlin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.hotstu.graphicfun.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.view).setBackground(new CloudDrawable(1));
    }
}
