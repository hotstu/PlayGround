package com.example.slab.appglide;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.slab.appglide.dagger.AppComponet;
import com.example.slab.appglide.dagger.AppModule;
import com.example.slab.appglide.dagger.DaggerAppComponet;

/**
 * Created by hotstuNg on 2016/7/25.
 */
public class App extends Application {
    private static App sApp;
    AppComponet mAppComponet;
    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mAppComponet = DaggerAppComponet.builder()
                .appModule(new AppModule(this)).build();
    }

    public static @NonNull AppComponet getAppComponet(Context context) {
        App app = (App) context.getApplicationContext();
        return app.mAppComponet;
    }

    public static @Nullable AppComponet getAppComponet() {
        if (sApp != null) {
            return sApp.mAppComponet;
        }
        return null;
    }
}
