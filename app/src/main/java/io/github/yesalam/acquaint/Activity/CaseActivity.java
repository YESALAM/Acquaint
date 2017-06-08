package io.github.yesalam.acquaint.Activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.Fragments.CompleteCaseFragment;
import io.github.yesalam.acquaint.Fragments.NewCaseFragment;
import io.github.yesalam.acquaint.R;

public class CaseActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewCaseFragment(),"New");
        adapter.addFragment(new CompleteCaseFragment(),"Complete");
        viewPager.setAdapter(adapter);
    }


}
