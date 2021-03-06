package com.example.slab.labrecycler.dragable;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.util.Pair;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.slab.labrecycler.R;
import com.example.slab.labrecycler.decor.EventHandlerItemDecor;
import com.example.slab.labrecycler.decor.Stepable;
import com.example.slab.labrecycler.decor.VerticalStepItemDecoration;
import com.example.slab.loader.widget.SlabAdapter;

import java.util.ArrayList;

/**
 * 使用自己封装的通用recyclerAdpater，同时实现drag drop的动画效果
 */
public class DragDropActivity1 extends AppCompatActivity implements SlabAdapter.ArrayAdapterDelegate<DragDropActivity1.VH, Pair<Long, String>>{

    private static final String TAG = "DragDropActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setupRecycler();
    }

    private void setupRecycler() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        assert recycler != null;
        final SlabAdapter<Pair<Long, String>> adapter = new SlabAdapter<>(this);
        ArrayList<Pair<Long, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Pair<>((long) i, "流程" + (10 -i)));
        }
        adapter.setDataSet(list);
        //adapter.setHasStableIds(true);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new VerticalStepItemDecoration(this));
        EventHandlerItemDecor eventHandler = new EventHandlerItemDecor();
        eventHandler.attach2RecyclerView(recycler);
        eventHandler.setListener(new EventHandlerItemDecor.OnViewHolderClickListener() {
            @Override
            public void onClick(RecyclerView parent, RecyclerView.ViewHolder viewHolder) {
                Log.d(TAG, "onClick() called with: " + "parent = [" + parent + "], viewHolder = [" + viewHolder + "]");
            }
        });
        //recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public VH onCreateViewHolder(SlabAdapter<Pair<Long, String>> adapter, ViewGroup parent, int viewType) {
        return new VH(getLayoutInflater().inflate(android.R.layout.simple_list_item_1,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final SlabAdapter<Pair<Long, String>> adapter, RecyclerView.ViewHolder holder, final int position) {
        final VH vh = ((VH) holder);
        vh.setSerial(adapter.getItem(position).first);
        vh.setText(adapter.getItem(position).second);
//        vh.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapter.moveItem(vh.getAdapterPosition(), 0);
//            }
//        });
    }


    @Override
    public int getItemViewType(SlabAdapter<Pair<Long, String>> adapter, int position) {
        return 0;
    }

    @Override
    public long getItemId(SlabAdapter<Pair<Long, String>> adapter, int position) {
        Log.d(TAG, "getItemId() called with:  position = [" + position + "]");
        return 1;
    }

    public final static class VH extends RecyclerView.ViewHolder implements Stepable {
        TextView tv;
        private Long first;

        public VH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setText(String text) {
            tv.setText(text + "\n\n描述\n完成度：" + getCompleteFraction() + "%");
        }


        public void setSerial(Long first) {
            this.first = first;
        }

        @Override
        public float getCompleteFraction() {
            if (first < 3) {
                return 0;
            } else if (first == 3) {
                return 50;
            } else {
                return 100;
            }
        }
    }

}
