package com.example.slab.dagger.modules;

import com.example.slab.dagger.MainActivity;
import com.example.slab.dagger.models.SayHelloer;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hotstuNg on 2016/7/14.
 */
@Component(modules = AppModule.class)
@Singleton
@MyScop
public interface AppComponet {
    SayHelloer getSayHelloer();

    void inject(SayHelloer sayHelloer);

    void inject(MainActivity mainActivity);
}
