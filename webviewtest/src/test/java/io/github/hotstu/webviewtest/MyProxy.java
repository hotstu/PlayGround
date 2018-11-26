package io.github.hotstu.webviewtest;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hglf
 * @since 2018/7/11
 */
public class MyProxy {
    @Test
    public void main() {
        Class<H> hClass = H.class;
        Object o = Proxy.newProxyInstance(hClass.getClassLoader(), new Class[]{hClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(args[0]);
                return null;
            }
        });
        ((H) o).hello("hello");
    }
}
