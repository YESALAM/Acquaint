package io.github.yesalam.acquaint.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.Fragments.CompleteInvestigationFragment;
import io.github.yesalam.acquaint.Fragments.NewInvestigationFragment;
import io.github.yesalam.acquaint.Fragments.OfflineCases;
import io.github.yesalam.acquaint.Fragments.OfflineInvestigation;
import io.github.yesalam.acquaint.Fragments.TeleVerificationFragment;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 25-06-2017.
 */

public class Offline extends BaseDrawerActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new OfflineInvestigation(), "Investigation");
        adapter.addFragment(new OfflineCases(), "New Cases");
        viewPager.setAdapter(adapter);
    }


}
