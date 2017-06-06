package io.github.yesalam.acquaint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 05-06-2017.
 */

public class BaseWebActivity extends AppCompatActivity {
    protected static WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = (WebView) getLayoutInflater().inflate(R.layout.webview,null);
        webView.loadUrl(ACQUAINT_URL);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
