package com.example.slab.customviewplayground;

import org.junit.Test;

import java.util.Calendar;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
    }
}