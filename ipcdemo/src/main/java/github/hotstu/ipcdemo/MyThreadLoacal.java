package github.hotstu.ipcdemo;

import java.util.HashMap;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/3/1
 */
public class MyThreadLoacal {
    private final static HashMap<String, Object> storage;

    static {
        storage = new HashMap<>();
    }

    public static void set(Object value) {
        storage.put(Thread.currentThread().getName(), value);

    }

    public static Object get() {
        return storage.get(Thread.currentThread().getName());
    }
}
