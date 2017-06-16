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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.Fragments.CaseBasicDetail;
import io.github.yesalam.acquaint.Fragments.CaseCoApplicant;
import io.github.yesalam.acquaint.Fragments.CaseGuarantor;
import io.github.yesalam.acquaint.Pojo.ApplicantResidentDetail;
import io.github.yesalam.acquaint.Pojo.CaseBasicDetailPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util.*;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.PASSWORD_KEY;
import static io.github.yesalam.acquaint.Util.Util.USER_ID_KEY;
import static io.github.yesalam.acquaint.Util.WebUtil.byteCodeit;

public class IndiCaseActivity extends BaseWebActivity {

    public String caseid;
    String LOG_TAG = "IndiCaseActivity";


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


    }


    private void loadCasePage() {
        Log.e(LOG_TAG, "loading case page");
        final String case_url = ACQUAINT_URL + "/Users/Cases/Edit/" + "4644878";
        Log.e(LOG_TAG, "loading url " + case_url);


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
                intent.putExtra("caseid",caseid);
                v.getContext().startActivity(intent);
            }
        });
    }




    private void parseAData(String html){
        Map<String,String> map = new HashMap<>();

        Document document = Jsoup.parse(html);
        String emailsent = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody > tr:nth-child(6) > td.table-number > table > tbody > tr > td:nth-child(3)").text();
        String residence = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody > tr:nth-child(7) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(8) > td:nth-child(4)").text();
        String office = document.select("#trOfficeAddress > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        String permanent = document.select("#trPerAddress > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        map.put("emailsentstatus",emailsent);
        map.put("residencestatus",residence);
        map.put("officestatus",office);
        map.put("permanentstatus",permanent);

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for(Element input:elements){
            map.put(input.id(),input.val());
        }

        Elements selects = form.getElementsByTag("select");
        for(Element select:selects){
            String id = select.id();
            String value = select.getElementsByAttributeValue("selected","selected").first().text();
            map.put(id,value);
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
        Element status = tbody.getElementById("Status");
        detail.status = status.val();
        Element email = status.parent().parent().lastElementSibling();
        detail.emailSentOn = email.text();


        ApplicantResidentDetail ardetail = new ApplicantResidentDetail();
        ardetail.name = tbody.getElementById("Name").val();
        ardetail.dateOfBirth = tbody.getElementById("DOB").val();
        ardetail.pan = tbody.getElementById("DOB").val();
        ardetail.gender = tbody.getElementById("Gender").getElementsByAttributeValue(selected,selected).first().text();
        ardetail.address = tbody.getElementById("Address").val();
        ardetail.city = tbody.getElementById("City").val();
        ardetail.state = tbody.getElementById("State").val();
        ardetail.pin = tbody.getElementById("Pin").val();
        ardetail.email = tbody.getElementById("EMail").val();
        ardetail.mobile = tbody.getElementById("Mobile").val();
        ardetail.phone = tbody.getElementById("Phone").val();
        ardetail.assignedTo = tbody.getElementById("AssignedTo").getElementsByAttributeValue(selected,selected).first().text();
        ardetail.status = tbody.getElementById("AssignedTo").parent().parent().lastElementSibling().text();
        ardetail.haveCompany = tbody.getElementById("HaveCompanyAddress").text();




    }

}
