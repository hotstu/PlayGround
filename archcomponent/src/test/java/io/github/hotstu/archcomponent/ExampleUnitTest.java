package io.github.hotstu.archcomponent;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static class Parent {
        protected String x = "parent";
        public void print() {
            System.out.println(x);
        }
    }
    public static class Child extends Parent {
        protected String x = "child";

        public void print() {
            System.out.println(x);
        }

    }
    @Test
    public void addition_isCorrect() throws Exception {
        Child c = new Child();
        c.print();
        Parent p = (Parent) c;
        p.print();
        System.out.println(c.x);
        System.out.println(p.x);
    }
}