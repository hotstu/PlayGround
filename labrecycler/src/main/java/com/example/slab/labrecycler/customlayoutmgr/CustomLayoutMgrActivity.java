package com.example.slab.labrecycler.customlayoutmgr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.slab.labrecycler.MyAdapter;
import com.example.slab.labrecycler.R;

import java.util.ArrayList;
import java.util.List;

public class CustomLayoutMgrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_mgr);
        RecyclerView recycler = findViewById(R.id.recycler);
        MyAdapter adapter = new MyAdapter();
        recycler.setLayoutManager(new StickyHeadersGridLayoutManager(this,2));
        recycler.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
        adapter.setDataSet(list);
    }

}
