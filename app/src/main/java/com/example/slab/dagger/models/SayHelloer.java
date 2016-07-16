package com.example.slab.dagger.models;


import com.example.slab.dagger.modules.MyScop;

import javax.inject.Inject;

/**
 * Created by hotstuNg on 2016/7/14.
 */
@MyScop
public class SayHelloer {
    private static final String TAG = "SayHelloer";
    @Inject Logger logger;


    @Inject
    public SayHelloer(Logger logger) {
        this.logger = logger;
        logger.d(TAG, "SayHelloer: init" + this + "with logger:" + this.logger);
    }

    public SayHelloer() {
    }

    @Inject
    public void register(People p) {
        logger.d(TAG, "register: "+ p);
    }

    public void sayHello() {
        logger.d(TAG, "sayHello: hello" + this.logger);
    }
}
