package github.hotstu.demo.customtab;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

//https://developer.chrome.com/multidevice/android/customtabs
//https://qiita.com/droibit/items/66704f96a602adec5a35
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button) {
            String url = "https://github.com/hotstu";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            //custom address bar
            builder.setToolbarColor(Color.RED);
            //ActionButton 注意icon的大小有要求（24dp），不然可能不显示
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_share);
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent activity = PendingIntent.getActivity(this, 0, i, 0);
            builder.setActionButton(bitmap, "测试", activity);
            //menuitem
            builder.addMenuItem("test", activity);

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }
        if (id == R.id.button2) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == R.id.button3) {
            Intent i = new Intent(this, Main2Activity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
