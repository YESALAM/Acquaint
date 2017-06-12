package io.github.yesalam.acquaint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import io.github.yesalam.acquaint.Util.Util;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.WebUtil.byteCodeit;

/**
 * Created by yesalam on 05-06-2017.
 */

public abstract class BaseWebActivity extends AppCompatActivity implements HtmlJsInterface.JsCallbackInterface{
    protected static WebView webView;
    public HtmlJsInterface htmlJsInterface ;
    String LOG_TAG = "BaseWebActivity";

    public static int count = 0;


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

    public void checkLogin() {
        Log.e(LOG_TAG, "checking login");
        htmlJsInterface.setRequestType(Util.AcquaintRequestType.LOGIN);
        webView.loadUrl(ACQUAINT_URL);
    }

    public void login() {
        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userid = app_preferences.getString(USER_ID_KEY, "NA");
        String password = app_preferences.getString(PASSWORD_KEY, "NA");
        if (userid.equalsIgnoreCase("NA")) {
            //should no happen
        } else {
            Log.e(LOG_TAG, "trying to login");
            htmlJsInterface.setRequestType(Util.AcquaintRequestType.LOGIN);
            webView.postUrl(ACQUAINT_URL, byteCodeit(userid, password));

        }
    }



}
