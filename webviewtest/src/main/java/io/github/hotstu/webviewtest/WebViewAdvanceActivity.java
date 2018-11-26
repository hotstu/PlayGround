package io.github.hotstu.webviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.netease.nimlib.jsbridge.util.WebViewConfig;

public class WebViewAdvanceActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
    }

        private void initWebView() {
        webView = findViewById(R.id.web);

        WebSettings settings = webView.getSettings();
        WebViewConfig.setWebSettings(this, settings, this.getApplicationInfo().dataDir);
        WebViewConfig.removeJavascriptInterfaces(webView);
        WebViewConfig.setWebViewAllowDebug(false);
        WebViewConfig.setAcceptThirdPartyCookies(webView);

        //webView.loadUrl(LOCAL_ASSET_HTML);
    }
}
