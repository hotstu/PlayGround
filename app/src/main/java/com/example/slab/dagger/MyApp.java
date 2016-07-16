package com.example.slab.dagger;

import android.app.Application;
import android.content.Context;

import com.example.slab.dagger.modules.AppComponet;
import com.example.slab.dagger.modules.DaggerAppComponet;

/**
 * Created by hotstuNg on 2016/7/16.
 */
public class MyApp extends Application {
    AppComponet mAppComponet;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponet = DaggerAppComponet.create();
    }

    public static AppComponet getAppComponet(Context ctx) {
        MyApp app = (MyApp) ctx.getApplicationContext();
        return app.mAppComponet;
    }
}
