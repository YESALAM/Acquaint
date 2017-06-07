package io.github.yesalam.acquaint.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static io.github.yesalam.acquaint.Util.Util.IS_LOGGED_KEY;

/**
 * Created by yesalam on 06-06-2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        boolean logged = app_preferences.getBoolean(IS_LOGGED_KEY,false);

        Log.e(this.getLocalClassName(),""+logged);

        Intent intent ;
        if(logged)
            intent = new Intent(this,CaseActivity.class);
        else
            intent = new Intent(this,LoginActivity.class);

        startActivity(intent);

        finish();
    }
}
