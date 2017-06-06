package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.net.wifi.WifiEnterpriseConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import io.github.yesalam.acquaint.BaseWebActivity;

public class MainActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        webView.setVisibility(View.VISIBLE);
        setContentView(webView);





    }


}
