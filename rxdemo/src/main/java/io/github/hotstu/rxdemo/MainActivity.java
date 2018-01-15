package io.github.hotstu.rxdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxSeekBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_info)
    TextView mInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn1)
    Button btn1;
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                RxSeekBar.userChanges(mSeekBar)
                        .map(integer -> integer + "")
                        .subscribe(RxTextView.text(mInfo))
        );
        compositeDisposable.add(RxView.clicks(btn1)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .map(o -> !btn1.isSelected())
                .subscribe(RxView.selected(btn1))
        );

        new RxPermissions(this).request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
                            Log.d(TAG, "granted:" + aBoolean);
                        },
                        System.err::println,
                        () -> {
                        });


    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
