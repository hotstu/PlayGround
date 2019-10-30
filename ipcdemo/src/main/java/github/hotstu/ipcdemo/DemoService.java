package github.hotstu.ipcdemo;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import androidx.annotation.Nullable;

import timber.log.Timber;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/3/1
 */
public class DemoService extends Service {
    static {
        Timber.plant(new Timber.DebugTree());
    }
    public DemoService() {
        super();
        Timber.d("init");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Timber.d("onBind");
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
        //return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Timber.d("onConfigurationChanged");

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.d("onLowMemory");

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Timber.d("onTrimMemory");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Timber.d("onUnbind");
        return super.onUnbind(intent);

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Timber.d("onRebind");

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Timber.d("onTaskRemoved");

    }
}
