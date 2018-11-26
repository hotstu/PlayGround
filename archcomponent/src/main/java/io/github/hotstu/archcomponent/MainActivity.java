package io.github.hotstu.archcomponent;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.github.hotstu.archcomponent.db.AppDatabase;
import io.github.hotstu.archcomponent.db.UserDao;
import io.github.hotstu.archcomponent.db.UserEntity;

public class MainActivity extends AppCompatActivity implements Observer<Object> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LifecycleObserver observer = new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onStart(){
                Log.d(TAG, "Lifecycle onStart");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onStop() {

                // !!lifecycle onStop event triggered before onSaveInstance
                // onStop 出现在onSaveInstance之前
                Log.d(TAG, "Lifecycle onStop");
            }
        };
        getLifecycle().addObserver(observer);
        final TextView msg = findViewById(R.id.msg);
        final TextView loading = findViewById(R.id.loading);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getRepos().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String repos) {
                msg.setText(repos);
            }
        });
        mainViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    loading.setVisibility(View.VISIBLE);
                }else {
                    loading.setVisibility(View.GONE);
                }
            }
        });
        userDao = AppDatabase.getInMemoryDatabase(getApplicationContext()).UserModel();

    }



    @Override
    protected void onStart() {
        super.onStart();
        LiveBus.getInstance().observe(this, this);
        LiveData<UserEntity> user = userDao.findUserById(1);
        user.observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                Log.d(TAG, "user  onChanged" );
            }
        });
        Log.d(TAG, "activity onStart");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "activity onStop");
        super.onStop();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "activity onSaveInstanceState");

    }

    public void test(View view) {
        //LiveBus.getInstance().setValue("test");
        UserEntity user = new UserEntity(0, "user");
        userDao.addUser(user);
    }

    @Override
    public void onChanged(@Nullable Object o) {
        Log.e(TAG, "LiveBus onChanged");
    }
}