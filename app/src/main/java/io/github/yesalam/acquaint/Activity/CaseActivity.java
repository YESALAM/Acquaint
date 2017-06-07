package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.net.wifi.WifiEnterpriseConfig;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.R;

import static io.github.yesalam.acquaint.Util.Util.USER_KEY;

public class CaseActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar.setTitle("Case");
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
