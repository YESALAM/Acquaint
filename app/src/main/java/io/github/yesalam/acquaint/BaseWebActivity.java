package io.github.yesalam.acquaint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 05-06-2017.
 */

public class BaseWebActivity extends AppCompatActivity implements HtmlJsInterface.JsCallbackInterface{
    protected static WebView webView;
    public HtmlJsInterface htmlJsInterface ;
    String LOG_TAG = "BaseWebActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = (WebView) getLayoutInflater().inflate(R.layout.webview,null);
        //webView.loadUrl(ACQUAINT_URL);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:window.html.getHtml('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                Log.d(getLocalClassName(),"Page loaded with url: "+url);
            }
        });
        CookieManager.getInstance().setAcceptCookie(true);

        htmlJsInterface = new HtmlJsInterface();
        htmlJsInterface.setJsCallbackInterface(this);
        webView.addJavascriptInterface(htmlJsInterface,"html");

    }


    @Override
    public void onDataParsedPasitive(String response) {
        Log.e(LOG_TAG, "login successfull. calling main");
    }

    @Override
    public void onDataParserdNegative(String negative) {

    }
}
