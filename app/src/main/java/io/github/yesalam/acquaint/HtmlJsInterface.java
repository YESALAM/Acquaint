package io.github.yesalam.acquaint;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Switch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.github.yesalam.acquaint.Util.Util.*;

import static io.github.yesalam.acquaint.Util.Util.AcquaintRequestType.LOGIN;

/**
 * Created by yesalam on 11-06-2017.
 */

public class HtmlJsInterface {
    static int count = 0;
    final String LOG_TAG = "HtmlJsInterface";
    public AcquaintRequestType requestType;
    JsCallbackInterface callback;

    public interface JsCallbackInterface {
        void onDataParsedPasitive(String response);
        void onDataParserdNegative(String negative);
    }

    public void setJsCallbackInterface(JsCallbackInterface jsCallbackInterface) {
        this.callback = jsCallbackInterface;
    }

    public void setRequestType(AcquaintRequestType requestType) {
        this.requestType = requestType;
    }


    @JavascriptInterface
    public void getHtml(final String html) {
        Log.e(LOG_TAG, "Got the html");
        Handler handler = new Handler(Looper.getMainLooper());
        switch (requestType) {
            case NO_LOGIN:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        noLogin(html);
                    }
                });
                break;

            case LOGIN:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        login(html);
                    }
                });
                break;

            case COMPLETE_CASES:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        completeCase(html);
                    }
                });
                break;


        }


    }

    private void noLogin(String html){
        Document document = Jsoup.parse(html);
        Log.e(LOG_TAG,"was not a login reqest");
        Element useridnode = document.getElementById("UserName");
        if (useridnode == null) {
            callback.onDataParserdNegative("noservice");
        } else {
            callback.onDataParsedPasitive("ok");
        }
    }

    private void login(String html){
        Document document = Jsoup.parse(html);
        Log.e(LOG_TAG,"login request");
        Element welcome = document.getElementById("wel");
        if (welcome == null) {
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                callback.onDataParserdNegative("noservice");
            } else {
                //credentials mismatch
                callback.onDataParserdNegative("loginerror");
                Log.e(LOG_TAG, "credential mismatch");
            }
        } else {
            //logged in
            Element span = welcome.getElementsByTag("span").first();
            String username = span.text();
            Log.e(LOG_TAG, "login successfull. calling main");
            callback.onDataParsedPasitive(username);
        }
    }

    private void completeCase(String html){
        Log.e(LOG_TAG,"called completeCase");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("btntab1");
        if(element == null){
            //not a case page
            Log.e(LOG_TAG,"case detail page not loaded ");
            callback.onDataParserdNegative(html);
        }else{
            Log.e(LOG_TAG,"case detail page loaded");
            callback.onDataParsedPasitive(html);
        }
    }
}
