package com.example.slab.labrecycler;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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



    private static class MyAdapter extends RecyclerView.Adapter<MyVH> {
        private static final String TAG = "MyAdapter";
        List<String> mList;

        public MyAdapter() {
            this.mList = new ArrayList<>();
        }

        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v;
            if (1 == viewType) {
                v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            else {
                v = inflater.inflate(R.layout.item_progress, parent, false);
            }
            return new MyVH(v, viewType);


        }

        @Override
        public void onBindViewHolder(final MyVH holder, int position) {
            if (1 == holder.getViewType()) {
                holder.setText(getItem(position));
            }
            else {
                holder.getRoot().findViewById(R.id.progress_bar).setVisibility(View.GONE);
                holder.getRoot().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);

                        ViewGroup.LayoutParams layoutParams = holder.getRoot().getLayoutParams();
                        View view = (View) holder.getRoot().getParent();
                        layoutParams.height = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
                        Log.e(TAG, "onLayoutChange: parent:"+ view + ",height" + layoutParams.height);
                        holder.getRoot().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).startsWith("pro")? -1: 1;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public final void setDataSet(List<? extends String> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        final String getItem(int position) {
            return mList.get(position);
        }

        final void addItem(String object) {
            mList.add(object);
            notifyItemInserted(mList.size() - 1);
        }

        final void addItem(String object, int position) {
            mList.add(position, object);
            notifyItemInserted(position);
        }

        final void removeItem(int position) {
            mList.remove(position);
            notifyItemRemoved(position);
        }

        public final void showProgress(boolean show) {
            if (show) {
                mList.clear();
                mList.add("progress");
            } else {
                // we do not need to clear list if we have already changed
                // data set or we have no ProgressItem to been cleared
                if (mList.size() == 1 && getItemViewType(0) == -1) {
                    mList.clear();
                }
            }
        }
    }

    private static class MyVH extends RecyclerView.ViewHolder {
        View root;
        TextView tv;
        int viewType;
        public MyVH(View itemView, int viewType) {
            super(itemView);
            this.root = itemView;
            this.viewType = viewType;
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setText(String item) {
            tv.setText(item);
        }

        public View getRoot() {
            return root;
        }

        public int getViewType() {
            return viewType;
        }
    }
}
