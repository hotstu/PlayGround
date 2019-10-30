package com.example.slab.dagger.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.slab.dagger.BR;

/**
 * Created by hotstuNg on 2016/7/15.
 */
public class People extends BaseObservable{

    private String name;

    public People(String name) {

        this.name = name;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
