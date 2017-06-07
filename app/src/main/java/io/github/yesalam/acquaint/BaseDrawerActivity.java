package io.github.yesalam.acquaint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import static io.github.yesalam.acquaint.Util.Util.IS_LOGGED_KEY;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_KEY;

/**
 * Created by yesalam on 07-06-2017.
 */

public class BaseDrawerActivity extends BaseWebActivity {

    private DrawerLayout mDrawerLayout;
    SharedPreferences app_preferences;

    public void onCreateDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.case_menu_drawer:
                        //case
                        Toast.makeText(getApplicationContext(),"Case clicked",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.investigation_menu_drawer:
                        //case
                        Toast.makeText(getApplicationContext(), "investigation clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.signout_menu_drawer:
                        //signout
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.remove(USER_KEY);
                        editor.remove(USER_ID_KEY);
                        editor.remove(PASSWORD_KEY);
                        editor.remove(IS_LOGGED_KEY);
                        editor.apply();
                        finish();
                        break;
                }
                return true;
            }
        });
    }


    protected void setApp_preferences(){
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

}
