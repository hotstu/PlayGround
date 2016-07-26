package com.example.slab.appglide.dagger;

import android.content.Context;

import com.example.slab.appglide.ImageWithProgressActivity;
import com.example.slab.appglide.glide.ResponseProgressListener;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by hotstuNg on 2016/7/25.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponet {
    OkHttpClient getOkHttpClient();

    @ProgressClient OkHttpClient getProgressOkHttpClient();

    Context getApplicationContext();

    ResponseProgressListener getResponseProgressListener();

    void inject(ImageWithProgressActivity activity);
}
