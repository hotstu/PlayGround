package io.github.hotstu.graphicfun;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import io.github.hotstu.graphicfun.astart.AStarView;

public class AStarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astar);
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_selectMode);
        final AStarView astar = (AStarView) findViewById(R.id.astar);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_start:
                        astar.setSelectMode(AStarView.SELECT_TARGET_START);
                        break;
                    case R.id.rb_end:
                        astar.setSelectMode(AStarView.SELECT_TARET_END);
                        break;
                    case R.id.rb_brick:
                        astar.setSelectMode(AStarView.SELECT_TARGET_WALL);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
