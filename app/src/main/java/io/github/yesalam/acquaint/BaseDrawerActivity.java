package io.github.yesalam.acquaint;

import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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
import io.github.yesalam.acquaint.Activity.LoginActivity;
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
import static io.github.yesalam.acquaint.Util.Util.deleteCache;
import static io.github.yesalam.acquaint.Util.Util.deletePreference;

/**
 * Created by yesalam on 07-06-2017.
 */

public abstract class BaseDrawerActivity extends AppCompatActivity  {

    protected DrawerLayout mDrawerLayout;



    String LOG_TAG = "BaseDrawerActivity" ;
    private SharedPreferences app_preferences;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateView();
    }

    public void onCreateView() {
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

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
                        finish();
                        break;
                    case R.id.investigation_menu_drawer:
                        intent = new Intent(getApplicationContext(), InvestigationActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.signout_menu_drawer:

                        signOut();
                        break;
                }

                return true;
            }
        });
    }


    public void signOut(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        app_preferences.edit().clear().commit();
                        deleteCache(getApplicationContext());

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


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


}
