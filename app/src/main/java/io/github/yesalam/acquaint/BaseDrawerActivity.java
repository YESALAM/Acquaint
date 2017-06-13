package io.github.yesalam.acquaint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import io.github.yesalam.acquaint.Activity.CaseActivity;
import io.github.yesalam.acquaint.Activity.CreateCaseDialog;
import io.github.yesalam.acquaint.Activity.InvestigationActivity;
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

/**
 * Created by yesalam on 07-06-2017.
 */

public abstract class BaseDrawerActivity extends AppCompatActivity implements Callback {

    protected DrawerLayout mDrawerLayout;

    protected SharedPreferences app_preferences;

    public OkHttpClient okHttpClient;

    String LOG_TAG = "BaseDrawerActivity" ;
    static int count = 0;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setApp_preferences();
        onCreateView();
    }

    public void onCreateView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String name = app_preferences.getString(USER_KEY, null);
        TextView textView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name_header);
        textView.setText(name);

        if (this instanceof CaseActivity) {
            actionBar.setTitle("Case");
            navigationView.getMenu().getItem(0).setChecked(true);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CreateCaseDialog.class);
                    view.getContext().startActivity(intent);
                }
            });
        } else {
            actionBar.setTitle("Field Investigations");
            navigationView.getMenu().getItem(1).setChecked(true);
            fab.setVisibility(View.GONE);
        }

        //CookieJar for webclient
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.case_menu_drawer:
                        intent = new Intent(getApplicationContext(), CaseActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.investigation_menu_drawer:
                        intent = new Intent(getApplicationContext(), InvestigationActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.signout_menu_drawer:
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.remove(USER_KEY);
                        editor.remove(USER_ID_KEY);
                        editor.remove(PASSWORD_KEY);
                        editor.remove(IS_LOGGED_KEY);
                        editor.apply();
                        break;
                }
                finish();
                return true;
            }
        });
    }


    protected void setApp_preferences() {
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract void setupViewPager(ViewPager viewPager);

    public void login(){
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
                if(count<1){
                    login();
                }else{
                    count=0;
                    Toast.makeText(this, "Service Unavailable! Please Try later", Toast.LENGTH_LONG).show();
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
            Element span = welcome.getElementsByTag("span").first();
            String username = span.text();
            Log.e(LOG_TAG, "login successfull.New Session started ");
            //NEw session started
        }
    }
}
