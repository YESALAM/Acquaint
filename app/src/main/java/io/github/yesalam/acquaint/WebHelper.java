package io.github.yesalam.acquaint;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;

/**
 * Created by yesalam on 16-06-2017.
 */

public class WebHelper implements Callback {
    String LOG_TAG = "WebHelper";
    public static final int NO_CONNECTION = 786;

    public SharedPreferences app_preferences;
    public OkHttpClient okHttpClient;
    public int count = 0;
    public boolean logged = false;
    private volatile boolean loginrequest = false;
    private volatile boolean ongoingrequest = false;
    private Context context;
    private Queue<Request> requests = new LinkedList<>();
    private Queue<CallBack> callbacks = new LinkedList<>();

    private static WebHelper helper;

    public static WebHelper getInstance(Context context) {
        if (helper == null) {
            helper = new WebHelper(context);
        }
        return helper;
    }

    private WebHelper(Context context) {
        this.context = context;
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(1);
        dispatcher.setMaxRequests(1);
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .dispatcher(dispatcher)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(context);


    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    synchronized public void requestCall(Request request, CallBack callback) {
        if (!isConnected()) {
            callback.onNegativeResponse(NO_CONNECTION);
            return;
        }
        requests.add(request);
        callbacks.add(callback);
        if (!(logged || loginrequest)) {
            login();
            return;
        }

        if (!(loginrequest || ongoingrequest)) callNext();
    }


    public void login() {
        Log.e(LOG_TAG, "trying to login");
        loginrequest = true;
        String userid = app_preferences.getString(USER_ID_KEY, "NA");
        String password = app_preferences.getString(PASSWORD_KEY, "NA");
        if (userid.equalsIgnoreCase("NA")) {
            //Called from Login Activity
            /*if (!requests.isEmpty()) {
                Request request = requests.peek();
                Log.e(LOG_TAG,request.url().toString()+"  "+request.method()+" ");
                okHttpClient.newCall(requests.peek()).enqueue(this);
                loginrequest = true;
                ongoingrequest = true;
            }*/
        } else {
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
        if (!requests.isEmpty()) {
            final CallBack temp = callbacks.remove();
            requests.remove();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    temp.onNegativeResponse(NO_CONNECTION);
                }
            });
        }

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (!response.isSuccessful()) {
            //throw new IOException("Unexpected code " + response);
            if (!requests.isEmpty()) {
                ResponseBody body = response.body();
                Log.e(LOG_TAG,body.toString());
                final CallBack temp = callbacks.remove();
                requests.remove();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        temp.onNegativeResponse(response.code());
                    }
                });
            }
        }
        ResponseBody body = response.body();
        MediaType type = body.contentType();
        if (type.type().equalsIgnoreCase("application")) {
            jsonResponseReader(body.string());
        } else {
            final String html = body.string();
            if (loginrequest) loginResponseReader(html);
            else htmlResponseReader(html, call, response);
        }

    }


    private void loginResponseReader(String html) {
        Document document = Jsoup.parse(html);
        Log.e(LOG_TAG, "login request");
        Element welcome = document.getElementById("wel");
        if (welcome == null) {
            logged = false;
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                if (count < 1) {
                    login();
                } else {
                    count = 0;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context.getApplicationContext(), "Service Unavailable! Please Try later", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                //credentials mismatch
                //should not happen
                count = 0;
                Log.e(LOG_TAG, "credential mismatch");
            }
        } else {
            count = 0;
            //logged in
            logged = true;
            Log.e(LOG_TAG, "login successfull.New Session started ");
            //NEw session started

            callNext();
        }
    }

    private void htmlResponseReader(final String html, Call call, Response response) {
        Document document = Jsoup.parse(html);
        Log.e(LOG_TAG, "Response :" + call.request().url() + " method:" + call.request().method());
        Element body = document.getElementById("body");
        if (body == null) {
            //noservice
            logged = false;
            Log.e(LOG_TAG, "problem with service.retrying");
            if (count < 1) {
                login();
            } else {
                count = 0;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context.getApplicationContext(), "Service Unavailable! Please Try later", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                count = 0;
                logged = true;
                Log.e(LOG_TAG, "response OK");
                if (!requests.isEmpty()) {
                    final CallBack temp = callbacks.remove();
                    requests.remove();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            temp.onPositiveResponse(html);
                        }
                    });
                }

                callNext();
            } else {
                logged = false;
                //credentials mismatch
                //should not happen
                Log.e(LOG_TAG, "not logged in");
                count = 0;
                login();
            }

        }

    }

    private void jsonResponseReader(final String json) {
        Log.e(LOG_TAG, "json Response OK");
        if (!requests.isEmpty()) {
            final CallBack temp = callbacks.remove();
            requests.remove();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    temp.onPositiveResponse(json);
                }
            });
        }
        callNext();
    }

    private void callNext() {
        if (!requests.isEmpty()) {
            Request request = requests.peek();
            Log.e(LOG_TAG, request.url().toString() + "  " + request.method() + " ");
            okHttpClient.newCall(requests.peek()).enqueue(this);
            loginrequest = false;
            ongoingrequest = true;
        } else {
            loginrequest = false;
            ongoingrequest = false;
        }
    }


    public interface CallBack {
        void onPositiveResponse(String html);

        void onNegativeResponse(int code);
    }
}
