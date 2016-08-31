package io.github.hotstu.graphicfun;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class RotationActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    View mTextView;
    private ScalpelFrameLayout scalpelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        scalpelView = (ScalpelFrameLayout) findViewById(R.id.scalpelView);
        scalpelView.setLayerInteractionEnabled(true);
        SeekBar rotationX = (SeekBar) findViewById(R.id.seekBarx);
        SeekBar rotationY = (SeekBar) findViewById(R.id.seekBary);
        SeekBar rotationZ = (SeekBar) findViewById(R.id.seekBarz);
        mTextView = findViewById(R.id.textView);
        mTextView.setCameraDistance(1600*getResources().getDisplayMetrics().density);
        rotationX.setOnSeekBarChangeListener(this);
        rotationY.setOnSeekBarChangeListener(this);
        rotationZ.setOnSeekBarChangeListener(this);

        Switch enabledSwitch = new Switch(this);
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                scalpelView.setLayerInteractionEnabled(isChecked);
                invalidateOptionsMenu();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(enabledSwitch);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        switch (id) {
            case R.id.seekBarx:
                rotationX(progress);
                break;
            case R.id.seekBary:
                rotationY(progress);
                break;
            case R.id.seekBarz:
                rotationZ(progress);
                break;
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        if (!scalpelView.isLayerInteractionEnabled()) {
            return false;
        }
        menu.add("Draw Views")
                .setCheckable(true)
                .setChecked(scalpelView.isDrawingViews())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        scalpelView.setDrawViews(checked);
                        return true;
                    }
                });
        menu.add("Draw IDs")
                .setCheckable(true)
                .setChecked(scalpelView.isDrawingIds())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        scalpelView.setDrawIds(checked);
                        return true;
                    }
                });
        return true;
    }

    private void rotationX(int progress) {
        mTextView.setRotationX(1.80f * progress);
    }

    private void rotationY(int progress) {
        mTextView.setRotationY(1.80f * progress);
    }

    private void rotationZ(int progress) {
        mTextView.setRotation(1.80f * progress);
    }
}
