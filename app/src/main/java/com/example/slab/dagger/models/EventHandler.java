package com.example.slab.dagger.models;

import android.view.View;

import com.example.slab.loader.logger.Log;

import javax.inject.Inject;

/**
 * Created by hotstuNg on 2016/7/16.
 */
public class EventHandler {
    private static final String TAG = "EventHandler";

    @Inject
    public EventHandler() {
    }

    public void onClickHanlder(View v) {
        Log.d(TAG, "onClick:" + v);
    }

    public void onClickHanlder2(People v) {
        Log.d(TAG, "onClick" + v.getName());
        v.setName("Observable" + System.currentTimeMillis());
    }
}
