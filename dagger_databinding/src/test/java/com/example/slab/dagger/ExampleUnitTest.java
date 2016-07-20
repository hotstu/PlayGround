package com.example.slab.dagger;

import com.example.slab.dagger.models.SayHelloer;
import com.example.slab.dagger.modules.AppComponet;
import com.example.slab.dagger.modules.AppModule;
import com.example.slab.dagger.modules.DaggerAppComponet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void natureCall() throws Exception {
        AppComponet c = DaggerAppComponet.builder().appModule(new AppModule()).build();
//        c.getSayHelloer();
//        c.getSayHelloer();

        SayHelloer s = new SayHelloer();
        c.inject(s);
    }
}