package io.github.yesalam.acquaint.Activity;

import android.content.Intent;
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
        setContentView(R.layout.activity_indi_case);

        Intent intent = getIntent();
        String title = intent.getStringExtra("caseno");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Case "+title);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager,fab);
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager,final FloatingActionButton fab) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CaseBasicDetail(),"Basic Detail");
        adapter.addFragment(new CaseCoApplicant(),"Co-Applicant");
        adapter.addFragment(new CaseGuarantor(),"Guarantor");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 1:
                            fab.show();
                            break;
                        default:
                            fab.hide();
                            break;
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CoApplicantDialog.class);
                v.getContext().startActivity(intent);
            }
        });
    }

}
