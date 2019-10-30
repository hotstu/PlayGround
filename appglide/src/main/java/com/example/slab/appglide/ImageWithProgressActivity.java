package com.example.slab.appglide;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class ImageWithProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_with_progress);
        App.getAppComponet(this).inject(this);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView textView = (TextView) findViewById(R.id.text);
        String url = "http://g.hiphotos.baidu.com/zhidao/pic/item/48540923dd54564ea406b62bb5de9c82d1584f44.jpg";
        MyProgressTarget<GlideDrawable> target = new MyProgressTarget<>(new GlideDrawableImageViewTarget(imageView), imageView, textView);
        target.setModel(url);
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(target);
    }
}
