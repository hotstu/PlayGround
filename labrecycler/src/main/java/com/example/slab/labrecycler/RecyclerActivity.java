package com.example.slab.labrecycler;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 *
 */
public class RecyclerActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerActivity";
    RecyclerView mRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MyAdapter myAdapter = new MyAdapter();

        mRecycler.setAdapter(myAdapter);
        myAdapter.showProgress(true);
        mRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> array = new ArrayList<>();
                array.add("item 1: tom");
                array.add("item 2: jerry");
                array.add("item 3: dog");
                ((MyAdapter) mRecycler.getAdapter()).showProgress(false);
                ((MyAdapter) mRecycler.getAdapter()).setDataSet(array);

            }
        }, 3000);
    }

    public void add(View view) {
        ((MyAdapter) mRecycler.getAdapter()).addItem("new item " + SystemClock.currentThreadTimeMillis());
    }
    public void addr(View view) {
        MyAdapter adapter = (MyAdapter) mRecycler.getAdapter();
        int index = (int) (Math.random() * (adapter.getItemCount()));
        adapter.addItem("new item " + SystemClock.currentThreadTimeMillis(), index);
    }

     public void del(View view) {
         MyAdapter adapter = (MyAdapter) mRecycler.getAdapter();
         if (adapter.getItemCount() == 0) {
             return;
         }
         int index = (int) (Math.random() * (adapter.getItemCount()));
         adapter.removeItem(index);
     }

    public void test(View v) {
        if (mRecycler.getLayoutManager() instanceof GridLayoutManager) {
            mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        else {
            mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
//        Log.e(TAG, "test: "+ getResources().getDisplayMetrics().densityDpi );
//
//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.firefox_128x128);
//        Log.e(TAG, String.format(Locale.ROOT, "height:%d, width:%d",
//                drawable.getIntrinsicHeight(), drawable.getIntrinsicWidth()) );
    }






}
