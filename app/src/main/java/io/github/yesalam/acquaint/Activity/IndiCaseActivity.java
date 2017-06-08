package io.github.yesalam.acquaint.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.Fragments.CaseBasicDetail;
import io.github.yesalam.acquaint.Fragments.CaseCoApplicant;
import io.github.yesalam.acquaint.Fragments.CaseGuarantor;
import io.github.yesalam.acquaint.Fragments.CompleteCaseFragment;
import io.github.yesalam.acquaint.Fragments.NewCaseFragment;
import io.github.yesalam.acquaint.R;

public class IndiCaseActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_activity_views);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CaseBasicDetail(),"Basic Detail");
        adapter.addFragment(new CaseCoApplicant(),"Co-Applicant");
        adapter.addFragment(new CaseGuarantor(),"Guarantor");
        viewPager.setAdapter(adapter);
    }

}
