package com.example.slab.transtitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.slab.loader.widget.SlabAdapter;

import java.util.Arrays;

/**
 * 使用TransitionManager.beginDelayedTransition
 */
public class MainActivity2 extends AppCompatActivity implements SlabAdapter.ArrayAdapterDelegate<RecyclerView.ViewHolder, Integer>, View.OnClickListener {
    RecyclerView root;
    Integer[] resids = new Integer[] {
      R.drawable.x1, R.drawable.x2, R.drawable.x3
    };
    private SlabAdapter<Integer> slabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new RecyclerView(this);
        root.setLayoutManager(new LinearLayoutManager(this));
        slabAdapter = new SlabAdapter<>(this);
        slabAdapter.setDataSet(Arrays.asList(resids));
        root.setAdapter(slabAdapter);
        setContentView(root);
    }




    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("image", (Integer) v.getTag());
        ImageView iv = (ImageView) v.findViewById(R.id.image);
        Bundle b = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, iv, getResources().getString(R.string.tn_listicon))
                .toBundle();
        startActivity(i, b);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(SlabAdapter<Integer> adapter, ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new RecyclerView.ViewHolder(v) {};
    }

    @Override
    public void onBindViewHolder(SlabAdapter<Integer> adapter, RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(slabAdapter.getItem(position));
        ((ImageView) holder.itemView.findViewById(R.id.image)).setImageResource( adapter.getItem(position));
    }

    @Override
    public int getItemViewType(SlabAdapter<Integer> adapter, int position) {
        return 0;
    }

    @Override
    public long getItemId(SlabAdapter<Integer> adapter, int position) {
        return 0;
    }

}
