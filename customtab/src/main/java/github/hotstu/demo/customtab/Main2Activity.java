package github.hotstu.demo.customtab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button4) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        if (view.getId() == R.id.button5) {
            Intent i = new Intent(this, ChildActivity.class);
            startActivity(i);
        }

    }
}
