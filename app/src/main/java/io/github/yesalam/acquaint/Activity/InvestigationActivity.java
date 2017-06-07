package io.github.yesalam.acquaint.Activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 07-06-2017.
 */

public class InvestigationActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar.setTitle("Field Investigations");
        navigationView.getMenu().getItem(1).setChecked(true);
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
