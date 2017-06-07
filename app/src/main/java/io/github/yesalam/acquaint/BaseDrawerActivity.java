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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import io.github.yesalam.acquaint.Activity.CaseActivity;
import io.github.yesalam.acquaint.Activity.InvestigationActivity;

import static io.github.yesalam.acquaint.Util.Util.IS_LOGGED_KEY;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_KEY;

/**
 * Created by yesalam on 07-06-2017.
 */

public abstract class BaseDrawerActivity extends BaseWebActivity {

    protected DrawerLayout mDrawerLayout;

    protected SharedPreferences app_preferences;

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
                    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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

}
