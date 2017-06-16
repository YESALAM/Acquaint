package io.github.yesalam.acquaint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import io.github.yesalam.acquaint.Util.Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.WebUtil.byteCodeit;

/**
 * Created by yesalam on 05-06-2017.
 */

public abstract class BaseWebActivity extends AppCompatActivity implements Callback {

    String LOG_TAG = "BaseWebActivity";

    public SharedPreferences app_preferences;
    public static OkHttpClient okHttpClient;
    public static int count = 0;
    public static boolean logged = false ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    public void init(){
        //CookieJar for webclient
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(1);
        dispatcher.setMaxRequests(1);
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .dispatcher(dispatcher)
                .build();
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void login(){
        if(logged) return;
        String userid = app_preferences.getString(USER_ID_KEY, "NA");
        String password = app_preferences.getString(PASSWORD_KEY, "NA");
        if (userid.equalsIgnoreCase("NA")) {
            //should no happen
        } else {
            Log.e(LOG_TAG, "trying to login");
            RequestBody formBody = new FormBody.Builder()
                    .add("UserName", userid)
                    .add("Password", password)
                    .add("RememberMe", "true")
                    .build();
            final Request request = new Request.Builder()
                    .url(ACQUAINT_URL)
                    .post(formBody)
                    .build();
            okHttpClient.newCall(request).enqueue(this);
        }


    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call,final Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        final String html = response.body().string();
        loginResponseReader(html);
    }


    private void loginResponseReader(String html){
        Document document = Jsoup.parse(html);
        Log.e(LOG_TAG,"login request");
        Element welcome = document.getElementById("wel");
        if (welcome == null) {
            logged = false ;
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                if(count<1){
                    login();
                }else{
                    count=0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Service Unavailable! Please Try later", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                //credentials mismatch
                //should not happen
                count=0;
                Log.e(LOG_TAG, "credential mismatch");
            }
        } else {
            count=0;
            //logged in
            logged = true ;
            Log.e(LOG_TAG, "login successfull.New Session started ");
            //NEw session started
        }
    }



}
