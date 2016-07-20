package com.example.slab.dagger.models;

/**
 * Created by hotstuNg on 2016/7/14.
 */
public class StdLogger implements Logger {
    public  void d(String tag, String msg) {
        System.out.println(tag + "-->" + msg);
    }
}
