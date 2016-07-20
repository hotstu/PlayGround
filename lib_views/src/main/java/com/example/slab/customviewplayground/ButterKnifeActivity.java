package com.example.slab.customviewplayground;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by hotstuNg on 2016/7/13.
 */
public class ButterKnifeActivity extends AppCompatActivity{
    private static final String TAG = "ButterKnifeActivity";
    @BindViews({R.id.textView, R.id.textView2, R.id.textView3}) List<TextView> mTextViews;
    @BindView(R.id.button)  Button mButton;
    @BindView(R.id.checkBox)  CheckBox mCheckBox;
    @BindView(R.id.switch1)  Switch mASwitch;
    @BindString(R.string.bind_str)  String mString;

    private int clickCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butters1);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + (mButton));
    }

    @OnClick(R.id.button)  void btn1Click(Button btn1) {
        Log.d(TAG, "btn1Click: " + btn1.getId() + mString);
        ButterKnife.apply(mTextViews, new ButterKnife.Action<TextView>() {
            @Override
            public void apply(@NonNull TextView view, int index) {
                view.setText(index != clickCount? "*****": mString);
            }
        });
        clickCount += 1;
        clickCount %= 3;
    }

    @OnCheckedChanged({R.id.checkBox, R.id.switch1})
    void onCheckChanged(CompoundButton view, boolean isChecked) {
        if (view.getId() == R.id.switch1) {
            Toast.makeText(this, "switch1 checked?" + isChecked, Toast.LENGTH_SHORT).show();
        }
        if (view.getId() == R.id.checkBox) {
            Toast.makeText(this, "checkBox checked?" + isChecked, Toast.LENGTH_SHORT).show();
        }
    }
}
