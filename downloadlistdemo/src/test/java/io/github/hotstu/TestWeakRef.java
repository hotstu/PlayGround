package io.github.hotstu;

import java.lang.ref.WeakReference;


public class TestWeakRef {
    @org.junit.Test
    public void test1() {
        WeakReference<Runnable> wref = new WeakReference<Runnable>(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        });
        for (int i = 0; i < 10; i++) {
            System.out.println(wref.get() == null);
            System.gc();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
