package io.github.yesalam.acquaint.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import butterknife.BindView;
import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.Fragments.CaseBasicDetail;
import io.github.yesalam.acquaint.Fragments.CaseCoApplicant;
import io.github.yesalam.acquaint.Fragments.CaseGuarantor;
import io.github.yesalam.acquaint.Pojo.CaseBasicDetailPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util.*;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.WebUtil.byteCodeit;

public class IndiCaseActivity extends BaseWebActivity {

    String caseid;
    String LOG_TAG = "IndiCaseActivity";
    static int count = 0;

    //View Binding
    @BindView(R.id.client_spinner)
    Spinner client_spinner;
    @BindView(R.id.branch_spinner)
    Spinner branch_spinner;
    @BindView(R.id.contact_person_spinner)
    Spinner contact_person_spinner;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indi_case);

        Intent intent = getIntent();
        caseid = intent.getStringExtra("caseno");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Case " + caseid);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager, fab);
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        checkLogin();
    }

    private void checkLogin() {
        Log.e(LOG_TAG, "checking login");
        htmlJsInterface.setRequestType(AcquaintRequestType.LOGIN);
        webView.loadUrl(ACQUAINT_URL);
    }

    private void login() {
        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userid = app_preferences.getString(USER_ID_KEY, "NA");
        String password = app_preferences.getString(PASSWORD_KEY, "NA");
        if (userid.equalsIgnoreCase("NA")) {
            //should no happen
        } else {
            Log.e(LOG_TAG, "trying to login");
            htmlJsInterface.setRequestType(AcquaintRequestType.LOGIN);
            webView.postUrl(ACQUAINT_URL, byteCodeit(userid, password));

        }
    }


    private void loadCasePage() {
        Log.e(LOG_TAG, "loading case page");
        final String case_url = ACQUAINT_URL + "/Users/Cases/Edit/" + "4644878";
        //final String case_url = "http://myacquaint.com/Users/Cases/Edit/4644878";
        Log.e(LOG_TAG, "loading url " + case_url);
        htmlJsInterface.setRequestType(AcquaintRequestType.COMPLETE_CASES);
        webView.loadUrl(case_url);

    }

    private void setupViewPager(ViewPager viewPager, final FloatingActionButton fab) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CaseBasicDetail(), "Basic Detail");
        adapter.addFragment(new CaseCoApplicant(), "Co-Applicant");
        adapter.addFragment(new CaseGuarantor(), "Guarantor");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
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
                Intent intent = new Intent(v.getContext(), CoApplicantDialog.class);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public void onDataParsedPasitive(String response) {
        if (htmlJsInterface.requestType == AcquaintRequestType.LOGIN) {
            Log.e(LOG_TAG, "login successfull.");
            loadCasePage();
        } else if(htmlJsInterface.requestType == AcquaintRequestType.COMPLETE_CASES){
            Document document = Jsoup.parse(response);
            Element element = document.getElementById("ContactId");
            Log.e(LOG_TAG, element.ownText());
            parseData(response);
        }


    }

    @Override
    public void onDataParserdNegative(String negative) {
        if (negative.equalsIgnoreCase("loginerror")) {
            Log.e(LOG_TAG, "credential mismatch");
            //should not happen
        } else if (negative.equalsIgnoreCase("noservice")) {
            Log.e(LOG_TAG, "problem with service.retrying");
            if (count < 1) {
                login();
                count++;
            }
        }
    }


    private void parseData(String html){
        Document document = Jsoup.parse(html);
        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Log.e(LOG_TAG,form.val());
        Element tbody = form.getElementsByTag("tbody").first();
        //CaseBasicDetailPojo
        CaseBasicDetailPojo detail = new CaseBasicDetailPojo();
        String selected = "selected" ;
        detail.client = tbody.getElementById("ClientId").getElementsByAttributeValue(selected,selected).first().text();
        detail.branch = tbody.getElementById("BranchId").getElementsByAttributeValue(selected,selected).first().text();
        detail.contactPerson = tbody.getElementById("ContactId").getElementsByAttributeValue(selected,selected).first().text();
        detail.loantype = tbody.getElementById("LoanType").getElementsByAttributeValue(selected,selected).first().text();
        detail.pickupDate = tbody.getElementById("PickupDate").val();
        detail.isReVerification = tbody.getElementById("isReverification").getElementsByAttributeValue(selected,selected).text() == "Yes" ?true :false;
        detail.loanAmount = tbody.getElementById("LoanAmount").val();
        detail.loanTenure = tbody.getElementById("LoanTenure").val();
        detail.applicationRefNo = tbody.getElementById("ApplicationRefNo").val();
        detail.pickupBy = tbody.getElementById("PunchedBy").getElementsByAttributeValue(selected,selected).first().text();
        detail.status = tbody.getElementById("Status").val();

    }

}
