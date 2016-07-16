package com.example.slab.dagger.models;

import android.view.View;

import com.example.slab.loader.logger.Log;

import javax.inject.Inject;

/**
 * Created by hotstuNg on 2016/7/16.
 */
public class EventHandler {
    private static final String TAG = "EventHandler";
    Tester tester;

    @Inject
    public EventHandler() {
    }

    public void onClickHanlder(View v) {
        Log.d(TAG, "onClick:" + v);
    }

    public void onClickHanlder2(People v) {
        Log.d(TAG, "onClick " + v.getName());
        if (tester != null) {
            tester.setAdult(!tester.isAdult());
        }
        v.setName("Observable " + System.currentTimeMillis());
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }
}
