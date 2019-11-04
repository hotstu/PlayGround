package io.github.hotstu.webviewtest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int flag = 1 << 2;
        int flag2 = 1 << 4;
        addFlag(flag);
        addFlag(flag2);
        assertTrue(hasFlag(flag));
        assertTrue(hasFlag(flag2));
        removeFlag(flag);
        assertFalse(hasFlag(flag));
        assertTrue(hasFlag(flag2));
        removeFlag(flag2);
        assertFalse(hasFlag(flag));
        assertFalse(hasFlag(flag2));

    }

    private int flagkeep = 0;
    private void addFlag(int flag) {
        flagkeep |= flag;
    }

    private void removeFlag(int flag) {
        flagkeep &= ~flag;
    }

    private boolean hasFlag(int flag) {
        return (flagkeep & flag) != 0;
    }
}