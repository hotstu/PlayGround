package github.hotstu.demo.customtab;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
