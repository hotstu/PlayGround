package com.example.slab.appglide;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        mRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> array = Arrays.asList(
                        // few results from https://www.google.com/search?tbm=isch&q=image&tbs=isz:lt,islt:4mp
                        "http://www.noaanews.noaa.gov/stories/images/goes-12%2Dfirstimage-large081701%2Ejpg",
                        "http://www.spektyr.com/PrintImages/Cerulean%20Cross%203%20Large.jpg",
                        "https://cdn.photographylife.com/wp-content/uploads/2014/06/Nikon-D810-Image-Sample-6.jpg",
                        "https://upload.wikimedia.org/wikipedia/commons/5/5b/Ultraviolet_image_of_the_Cygnus_Loop_Nebula_crop.jpg",
                        "https://upload.wikimedia.org/wikipedia/commons/c/c5/Polarlicht_2_kmeans_16_large.png",
                        "https://www.hq.nasa.gov/alsj/a15/M1123519889LCRC_isometric_min-8000_g0dot5_enhanced_labeled.jpg",
                        "http://oceanexplorer.noaa.gov/explorations/02fire/logs/hirez/octopus_hires.jpg",
                        "https://upload.wikimedia.org/wikipedia/commons/b/bf/GOES-13_First_Image_jun_22_2006_1730Z.jpg",
                        "http://www.zastavki.com/pictures/originals/2013/Photoshop_Image_of_the_horse_053857_.jpg",
                        "http://www.marcogiordanotd.com/blog/wp-content/uploads/2014/01/image9Kcomp.jpg",
                        "https://cdn.photographylife.com/wp-content/uploads/2014/06/Nikon-D810-Image-Sample-7.jpg",
                        "https://www.apple.com/v/imac-with-retina/a/images/overview/5k_image.jpg",
                        "https://www.gimp.org/tutorials/Lite_Quickies/lordofrings_hst_big.jpg",
                        "http://www.cesbio.ups-tlse.fr/multitemp/wp-content/uploads/2015/07/Mad%C3%A8re-022_0_1.jpg",
                        "https://www.spacetelescope.org/static/archives/fitsimages/large/slawomir_lipinski_04.jpg",
                        "https://upload.wikimedia.org/wikipedia/commons/b/b4/Mardin_1350660_1350692_33_images.jpg",
                        "http://4k.com/wp-content/uploads/2014/06/4k-image-tiger-jumping.jpg"
                );
                ((MyAdapter) mRecycler.getAdapter()).setDataSet(array);

            }
        }, 1000);
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
        } else {
            mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
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
            View v = inflater.inflate(R.layout.item_progress, parent, false);
            return new MyVH(v, viewType);
        }

        @Override
        public void onBindViewHolder(final MyVH holder, int position) {
            holder.bind(getItem(position));
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

    }

    private static class MyVH extends RecyclerView.ViewHolder {
        private final MyProgressTarget<Bitmap> target;
        View root;
        TextView tv;
        ImageView imageView;
        int viewType;

        public MyVH(View itemView, int viewType) {
            super(itemView);
            this.root = itemView;
            this.viewType = viewType;
            tv = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            target = new MyProgressTarget<>(new BitmapImageViewTarget(imageView), imageView, tv);
        }

        void bind(String url) {
            target.setModel(url); // update target's cache
            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .asBitmap()
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop() // needs explicit transformation, because we're using a custom target
                    .into(target);
        }


    }
}
