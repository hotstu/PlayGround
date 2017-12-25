package com.example.slab.desionlib;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.slab.loader.widget.DemoAdapterDelegate2;
import com.example.slab.loader.widget.SlabAdapter2;

import java.util.ArrayList;
import java.util.List;


public class MDActivity2 extends AppCompatActivity {


    private SlabAdapter2 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md2);
        RecyclerView mRecyclerView = findView(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SlabAdapter2<>(new DemoAdapterDelegate2());
        mRecyclerView.setAdapter(mAdapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
        mAdapter.setDataSet(list);
    }


    private <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}
