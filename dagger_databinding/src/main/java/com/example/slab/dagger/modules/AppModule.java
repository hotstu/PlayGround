package com.example.slab.dagger.modules;

import com.example.slab.dagger.models.Logger;
import com.example.slab.dagger.models.People;
import com.example.slab.dagger.models.StdLogger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hotstuNg on 2016/7/14.
 */
@Module
public class AppModule {

    @Provides @MyScop
    Logger provideLogger() {
        return  new StdLogger();
    }

    @Provides
    People providePeope() {
        System.out.println("providePeope");
        return new People("dagger injection dataBinding");
    }

}
