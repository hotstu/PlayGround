package com.example.slab.customviewplayground;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.slab.customviewplayground.view.DynamicVIew;
import com.example.slab.customviewplayground.view.MyFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hotstuNg on 2016/7/11.
 */
public class ViewGroupGestrureViewActivity extends AppCompatActivity {

    private DynamicVIew dynamicVIew;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scrollview);
        dynamicVIew = DynamicVIew.attach2Window(this);
        mListView = (ListView) findViewById(R.id.list);
        List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health", "Function", "Supports", "Healthy", "Fat",
                "Metabolism", "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));
        final MyFrameLayout layout = (MyFrameLayout)findViewById(R.id.refreshlayout);
        layout.setOnRefreshListener(new MyFrameLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.reset();
                        dynamicVIew.inspect(layout, "refreshed!!!");
                    }
                }, 2000);
            }
        });
    }
    public void xu5(View view) {
        dynamicVIew.inspect(view, "+5s");
    }

    public void xu10(View view) {
        dynamicVIew.inspect(view, "+10s");
    }
}
