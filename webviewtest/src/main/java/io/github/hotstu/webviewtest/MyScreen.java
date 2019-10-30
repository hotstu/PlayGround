package io.github.hotstu.webviewtest;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 10/30/19
 */
public class MyScreen   {
    final Context mContext;
    final WindowManager mWindowManager;
    final Window mWindow;

    public MyScreen(Context context) {
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Window w;
        try {
            Class<?> aClass = Class.forName("com.android.internal.policy.PhoneWindow");
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(Context.class);
            w = (Window) declaredConstructor.newInstance(mContext);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        mWindow = w;
        w.setWindowManager(mWindowManager, null, null);
        w.setGravity(Gravity.CENTER);
        View decorView = mWindow.getDecorView();
        mWindowManager.addView(decorView, mWindow.getAttributes());
    }


}
