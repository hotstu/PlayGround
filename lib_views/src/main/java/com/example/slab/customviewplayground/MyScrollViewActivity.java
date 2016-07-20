package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hotstuNg on 2016/7/11.
 */
public class MyScrollViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scrollview);
        ViewGroup list_container = (ViewGroup) findViewById(R.id.list_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        int windowWidth = getResources().getDisplayMetrics().widthPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup child = (ViewGroup) inflater.inflate(R.layout.list, list_container, false);
            child.getLayoutParams().width = windowWidth ;
            initlist(child, i);
            list_container.addView(child);
        }
    }

    private void initlist(ViewGroup listLayout, int index) {
        TextView tilte = (TextView) listLayout.findViewById(android.R.id.title);
        tilte.setText("list"+  index);
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            array.add("item" + index + "_" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        ListView list = (ListView) listLayout.findViewById(android.R.id.list);
        list.setAdapter(adapter);
    }
}
