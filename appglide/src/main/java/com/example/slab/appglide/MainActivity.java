package com.example.slab.appglide;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Glide.with(this)
//                .load("http://img13.360buyimg.com/n0/jfs/t2287/44/1515846482/119958/e7670fbe/56b16a69N6d8f1de7.jpg")
//                .placeholder(R.mipmap.ic_launcher)
//                .skipMemoryCache(true)
//                //.crossFade(500)
//                .centerCrop()
//                .into((ImageView) findViewById(R.id.image));
//
//        Glide.with(this)
//                .load("http://img13.360buyimg.com/n0/jfs/t2287/44/1515846482/119958/e7670fbe/56b16a69N6d8f1de7.jpg")
//                .placeholder(R.mipmap.ic_launcher)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                //.crossFade(500)
//                .animate(R.anim.slide_left_in)
//                .fitCenter()
//                .into((ImageView) findViewById(R.id.image2));
//
//        test();
        Bundle b = new Bundle();
        System.out.println(b.hashCode()+"  ==");
        Handler h = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                System.out.println(msg.obj+" --");
                return true;
            }
        });
        h.obtainMessage(0, b).sendToTarget();
        b.putString("k", "v");


    }

    private void test() {
        Glide.with(this)
                .using(new MyModelLoader(), String.class)
                .from(MyModel.class)
                .as(String.class)
                .load(new MyModelImpl())
                .decoder(new ResourceDecoder<String, String>() {
                    @Override
                    public Resource<String> decode(String source, int width, int height) throws IOException {
                        return new SimpleResource<>("decoded " + source);
                    }

                    @Override
                    public String getId() {
                        return "";
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<String>() {
                    @Override
                    public void onResourceReady(String resource, GlideAnimation<? super String> glideAnimation) {
                        System.out.println("" + resource);
                    }
                });



    }

    interface  MyModel {
        public String queryId();
    }

    static class MyModelImpl implements MyModel {

        @Override
        public String queryId() {
            return "fake url";
        }
    }

    static class MyModelLoader implements ModelLoader<MyModel, String> {

        @Override
        public DataFetcher<String> getResourceFetcher(final MyModel model, int width, int height) {
            return new DataFetcher<String>() {
                @Override
                public String loadData(Priority priority) throws Exception {
                    return "hello world";
                }

                @Override
                public void cleanup() {

                }

                @Override
                public String getId() {
                    return model.queryId();
                }

                @Override
                public void cancel() {

                }
            };
        }
    }



}
