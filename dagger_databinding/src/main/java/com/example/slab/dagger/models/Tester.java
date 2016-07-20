package com.example.slab.dagger.models;

import android.databinding.ObservableBoolean;

import javax.inject.Inject;


/**
 * Created by hotstuNg on 2016/7/16.
 */
public class Tester{
    public ObservableBoolean  isAdult = new ObservableBoolean();

    @Inject
    public Tester(){
    }

}
