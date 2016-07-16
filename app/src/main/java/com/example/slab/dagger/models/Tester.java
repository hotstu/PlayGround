package com.example.slab.dagger.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.slab.dagger.BR;

import javax.inject.Inject;


/**
 * Created by hotstuNg on 2016/7/16.
 */
public class Tester  extends BaseObservable{
    private boolean isAdult;

    @Inject
    public Tester() {
        this.isAdult = true;
    }

    @Bindable
    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
        notifyPropertyChanged(BR.adult);
    }
}
