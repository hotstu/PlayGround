package com.example.slab.dagger.binding;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by hotstuNg on 2016/7/18.
 */
public class TextViewBindingAdapter {
    private static final String TAG = "TextViewBindingAdapter";

    private TextViewBindingAdapter() {
    }

//    @BindingAdapter("addition")
//    public static void doAdditionWith(TextView textView, String man) {
//        Log.d(TAG, "doAdditionWith() called with: " + "textView = [" + textView + "], man = [" + man + "]");
//    }
//
//    @BindingAdapter("addition")
//    public static void doAdditionWith(TextView textView, String oldman, String newman) {
//        Log.d(TAG, "doAdditionWith() called with: " + "textView = [" + textView + "], oldman = [" + oldman + "], newman = [" + newman + "]");
//    }


    @BindingAdapter({"addition", "addition2"})
    public static void doAdditionWith1(TextView textView, String oldman, String newman) {
        Log.d(TAG, "doAdditionWith1() called with: " + "textView = [" + textView + "], oldman = [" + oldman + "], newman = [" + newman + "]");
    }

    @BindingAdapter({"addition", "addition2"})
    public static void doAdditionWith2(TextView textView, String oldman, String newman) {
        Log.d(TAG, "doAdditionWith2() called with: " + "textView = [" + textView + "], oldman = [" + oldman + "], newman = [" + newman + "]");
    }
}
