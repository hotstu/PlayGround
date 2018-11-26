package io.github.hotstu.webviewtest;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import io.github.hotstu.webviewtest.bridge.MyWebClient;

public class WebViewBasicActivity extends AppCompatActivity {

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new MyWebClient(null));
        web.addJavascriptInterface(new WebAppInterface(getApplication()), "test");
        web.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onDestroy() {
        web.destroy();
        super.onDestroy();
    }

    public static  class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            int pid = android.os.Process.myPid();
            ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            String currentProcName = "";
            String threadName = Thread.currentThread().getName();
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses())
            {
                if (processInfo.pid == pid)
                {
                    currentProcName = processInfo.processName;
                    break;
                }
            }
            Log.d("mainActivity", "showToast() called with: toast = [" + toast + "]"
                    + currentProcName +
                    "," + threadName
            );
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
