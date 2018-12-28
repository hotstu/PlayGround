package io.github.hotstu.graphicfun;

import org.junit.Test;

import io.github.hotstu.graphicfun.parlin.SimplexNoise;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(SimplexNoise.noise(10, i*0.01));

        }
    }
}