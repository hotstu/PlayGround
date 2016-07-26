package com.example.slab.appglide.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.slab.appglide.App;

import java.io.InputStream;

/**
 * Created by hotstuNg on 2016/7/25.
 */
public class MyOkHttpGlideModule extends OkHttpGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(App.getAppComponet(context).getProgressOkHttpClient()));
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
    }


    public static void forget(String url) {
        App.getAppComponet().getResponseProgressListener().forget(url);
    }
    public static void expect(String url, UIProgressListener listener) {
        App.getAppComponet().getResponseProgressListener().expect(url, listener);
    }


}
