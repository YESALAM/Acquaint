package io.github.yesalam.acquaint.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import io.github.yesalam.acquaint.Util.RefreshValues;
import io.github.yesalam.acquaint.Util.RefreshValues.ProgressReceiver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util.*;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.IS_LOGGED_KEY;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_KEY;
import static io.github.yesalam.acquaint.Util.WebUtil.byteCodeit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Callback, ProgressReceiver {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    //private View mProgressView;
    private View mLoginFormView;
    private ProgressDialog progressDialog;

    String LOG_TAG = "LoginActivity" ;
    String userid;
    String password;

    static int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging you in");
        progressDialog.setCancelable(false);
    }






    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        userid = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userid)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(userid)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            progressDialog.show();
            login();

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return email.length()>1;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }




    private void login(){
        Log.e(LOG_TAG,"login called");
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();


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


    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        progressDialog.cancel();
        Snackbar.make(mEmailView,"Internet Unavailable!",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Call call,final Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        final String html = response.body().string();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    loginResponseReader(html);

            }
        });
    }


    private void loginResponseReader(String html){
        Document document = Jsoup.parse(html);
        Log.e(LOG_TAG,"login request");
        Element welcome = document.getElementById("wel");
        if (welcome == null) {
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                if(count<2){
                    login();
                    progressDialog.setMessage("Taking its time");
                }else{
                    count=0;
                    progressDialog.cancel();
                    Toast.makeText(this, "Service Unavailable! Please Try later", Toast.LENGTH_LONG).show();
                }
            } else {
                //credentials mismatch
                count=0;
                progressDialog.cancel();
                Log.e(LOG_TAG, "credential mismatch");
                mEmailView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        } else {
            count=0;
            //logged in
            progressDialog.setMessage("Login Successfull");
            Element span = welcome.getElementsByTag("span").first();
            String username = span.text();
            Log.e(LOG_TAG, "login successfull. calling main");
            SharedPreferences app_preferences =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putBoolean(IS_LOGGED_KEY,true);
            editor.putString(USER_KEY,username);
            editor.putString(USER_ID_KEY,userid);
            editor.putString(PASSWORD_KEY,password);
            editor.commit();


            RefreshValues refreshValues = new RefreshValues(this);
            refreshValues.setProgressReceiver(this);
            refreshValues.refresh();

        }
    }


    public void onPositiveResponse(final String htmldoc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginResponseReader(htmldoc);
            }
        });
    }

    @Override
    public void onProgress(String progress) {
        progressDialog.setMessage(progress);
    }

    @Override
    public void onFinishedProgress() {
        progressDialog.cancel();

        Intent intent = new Intent(this,CaseActivity.class) ;
        startActivity(intent);
        finish();
    }

    @Override
    public void onNegativeProgress(){
        progressDialog.cancel();
        Toast.makeText(this, "Refresh data from refresh menu", Toast.LENGTH_SHORT).show();
    }
}

