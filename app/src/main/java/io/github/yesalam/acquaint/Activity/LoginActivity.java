package io.github.yesalam.acquaint.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Build;
import android.os.Bundle;
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

import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util.*;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.IS_LOGGED_KEY;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_KEY;
import static io.github.yesalam.acquaint.Util.WebUtil.byteCodeit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseWebActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String LOG_TAG = "LoginActivity" ;
    String userid;
    String password;

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
        mProgressView = findViewById(R.id.login_progress);

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
            showProgress(true);
            login(userid,password);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void login(String userid,String password){

        htmlJsInterface.setRequestType(AcquaintRequestType.LOGIN);
        webView.postUrl(ACQUAINT_URL,byteCodeit(userid,password));
        /*webView.addJavascriptInterface(new LoginJSInterface(this,userid,password),"html");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgress(false);//TODO make sure we cover this to show everything fine when its gone main should come
                webView.loadUrl("javascript:var x;var a=document.getElementById('wel');if(a===null){var b=document.getElementById('UserName');if(b===null)x='noservice';else x='nologin';}else x=document.getElementById('wel').getElementsByTagName('span')[0].innerHTML;window.html.getName(x);");
            }
        });*/
    }

    @Override
    public void onDataParsedPasitive(String response) {
        Log.e(LOG_TAG, "login successfull. calling main");
        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean(IS_LOGGED_KEY,true);
        editor.putString(USER_KEY,userid);
        editor.putString(USER_ID_KEY,userid);
        editor.putString(PASSWORD_KEY,password);
        editor.apply();
        showProgress(false);
        Intent intent = new Intent(this,CaseActivity.class) ;
        Log.e(LOG_TAG,"starting activity");
        startActivity(intent);
        finish();
    }

    @Override
    public void onDataParserdNegative(String negative) {
        showProgress(false);
        if(negative.equalsIgnoreCase("loginerror")){
            Log.e(LOG_TAG, "credential mismatch");
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }else{
            Log.e(LOG_TAG, "problem with service.retrying");
            Toast.makeText(this, "Service Unavailable! Please Try later", Toast.LENGTH_SHORT).show();
        }
    }


    private class LoginJSInterface{
        private Context context ;
        private String userid;
        private String password;
        LoginJSInterface(Context context,String userid,String password){
            this.context = context ;
            this.userid = userid;
            this.password = password;
        }

        @JavascriptInterface
        public void getName(String name){
            if(name.equalsIgnoreCase("nologin")){
                //Login failed
                Log.e(LOG_TAG, "credential mismatch");
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }else if(name.equalsIgnoreCase("noservice")){
                //Service Unavailable
                Log.e(LOG_TAG, "problem with service.retrying");
                Toast.makeText(context, "Service Unavailable! Please Try later", Toast.LENGTH_SHORT).show();
            }else {
                Log.e(LOG_TAG, "login successfull. calling main");
                SharedPreferences app_preferences =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putBoolean(IS_LOGGED_KEY,true);
                editor.putString(USER_KEY,name);
                editor.putString(USER_ID_KEY,userid);
                editor.putString(PASSWORD_KEY,password);
                editor.apply();
                Intent intent = new Intent(context,CaseActivity.class) ;
                startActivity(intent);
                finish();
            }

        }
    }





}

