package io.github.yesalam.acquaint.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.Fragments.CompleteInvestigationFragment;
import io.github.yesalam.acquaint.Fragments.NewInvestigationFragment;
import io.github.yesalam.acquaint.Fragments.TeleVerificationFragment;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 07-06-2017.
 */

public class InvestigationActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewInvestigationFragment(),"New");
        adapter.addFragment(new CompleteInvestigationFragment(),"Complete");
        adapter.addFragment(new TeleVerificationFragment(),"Tele-verification");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onDataParsedPasitive(String response) {

    }

    @Override
    public void onDataParserdNegative(String negative) {

    }
}
