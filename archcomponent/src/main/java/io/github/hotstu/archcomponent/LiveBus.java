package io.github.hotstu.archcomponent;

import androidx.lifecycle.LiveData;

/**
 * 使用LiveData实现eventbus，
 * event is always sticky event and strict to the auto lifecycle management
 * @author hglf
 * @since 2018/1/19
 */
public class LiveBus extends LiveData<Object>{
    private static LiveBus sInstance;
    private LiveBus() {
    }

    public static LiveBus getInstance() {
        if (sInstance == null) {
            synchronized (LiveBus.class) {
                if (sInstance == null) {
                    sInstance = new LiveBus();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void postValue(Object value) {
        super.postValue(value);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
    }


}
