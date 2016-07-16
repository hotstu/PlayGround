package com.example.slab.dagger;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.slab.dagger.databinding.ActivityMainBinding;
import com.example.slab.dagger.models.EventHandler;
import com.example.slab.dagger.models.People;
import com.example.slab.dagger.models.Tester;
import com.example.slab.loader.activities.SampleActivityBase;
import com.example.slab.loader.logger.LogFragment;

import javax.inject.Inject;

public class MainActivity extends SampleActivityBase {
    @Inject
    People mPeople;
    @Inject
    EventHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MyApp.getAppComponet(this).inject(this);
        binding.setUser(mPeople);
        binding.setEventHandler(mHandler);
        Tester tester = new Tester();
        tester.setAdult(false);
        binding.setTester(tester);
    }

    @Override
    protected LogFragment getLogFragment() {
        return (LogFragment) getFragmentManager().findFragmentById(R.id.log_fragment);
    }

}
