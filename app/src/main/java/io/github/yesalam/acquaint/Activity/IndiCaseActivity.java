package io.github.yesalam.acquaint.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.Fragments.CaseBasicDetail;
import io.github.yesalam.acquaint.Fragments.CaseCoApplicant;
import io.github.yesalam.acquaint.Fragments.CaseGuarantor;
import io.github.yesalam.acquaint.Pojo.Card.CoApplicantPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.CaseBasicId;
import io.github.yesalam.acquaint.Util.Id.GuarantorId;
import io.github.yesalam.acquaint.Util.Id.OfficeId;
import io.github.yesalam.acquaint.Util.Id.PermanentId;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

public class IndiCaseActivity extends AppCompatActivity implements WebHelper.CallBack {

    public String caseid;
    String LOG_TAG = "IndiCaseActivity";
    public Map<String, String> formMap;
    public Map<String, String> guarMap;
    public List co_applicants;
    public boolean caseUpdate;
    String CASE_EDIT_URL = "/Users/Cases/Edit/";
    WebHelper webHelper;
    CallType callType;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indi_case);

        Intent intent = getIntent();
        //caseid = intent.getStringExtra("caseno");
        caseid = "4845097" ;

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


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        webHelper = WebHelper.getInstance(this);
        //loadBasicDetailPage();
    }

    public void updateData(){
        loadBasicDetailPage();
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
                intent.putExtra("caseid", caseid);
                v.getContext().startActivity(intent);
            }
        });
    }


    public void loadBasicDetailPage() {
        callType = CallType.BASIC_DETAIL;
        Log.e(LOG_TAG, "loading case page");
        //final String CASE_EDIT_URL = "/Users/Cases/Edit/" + caseid;

        Log.e(LOG_TAG, "loading url " + ACQUAINT_URL + CASE_EDIT_URL + caseid);
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_EDIT_URL + caseid)
                .build();

        webHelper.requestCall(request, this);
    }

    private Map<String, String> parseBasicDetail(String html) {
        Map<String, String> map = new HashMap<>();

        Document document = Jsoup.parse(html);
        String emailsent = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody > tr:nth-child(6) > td.table-number > table > tbody > tr > td:nth-child(3)").text();
        String residence = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody > tr:nth-child(7) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(8) > td:nth-child(4)").text();
        String office = document.select("#trOfficeAddress > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        String permanent = document.select("#trPerAddress > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        map.put("emailsentstatus", emailsent);
        map.put("ResidenceStatus", residence);
        map.put(OfficeId.officeStatus, office);
        map.put(PermanentId.perStatus, permanent);

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for (Element input : elements) {
            map.put(input.attr("name"), input.val());
            //Log.e(LOG_TAG,input.id()+" -> "+input.val());
        }

        Elements selects = form.getElementsByTag("select");
        for (Element select : selects) {
            String id = select.id();
            //Log.e(LOG_TAG,id);
            try {
                String value = select.getElementsByAttributeValue("selected", "selected").first().attr("value");
                //Log.e(LOG_TAG,value);
                map.put(id, value);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                map.put(id,"0");
            }
        }


        return map;
    }


    @Override
    public void onPositiveResponse(String htmldoc) {
        switch (callType) {
            case BASIC_DETAIL:
                formMap = parseBasicDetail(htmldoc);
                loadCoApplicant();
                final CaseBasicDetail fragment = (CaseBasicDetail) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (fragment != null) fragment.update(formMap);
                    }
                });
                break;
            case CO_APPLICANT:
                co_applicants = parseCoApplicant(htmldoc);
                loadGuarantor();
                final CaseCoApplicant caseCoApplicant = (CaseCoApplicant) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (caseCoApplicant != null) caseCoApplicant.update(co_applicants);
                    }
                });
                break;
            case GUARANTOR:
                guarMap = parseGuarantor(htmldoc);
                final CaseGuarantor caseGuarantor = (CaseGuarantor) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 2);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (caseGuarantor != null) caseGuarantor.update(guarMap);
                    }
                });
                break;
        }

    }

    @Override
    public void onNegativeResponse(final int code) {

        switch (callType) {
            case BASIC_DETAIL:
                final CaseBasicDetail fragment = (CaseBasicDetail) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
                        Log.e(LOG_TAG,"BASIC DETAIL NEGATIVE");
                        if (fragment != null) fragment.negativeResponse(code);

                break;
            case CO_APPLICANT:
                final CaseCoApplicant caseCoApplicant = (CaseCoApplicant) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
                Log.e(LOG_TAG,"CO-APPLICANT NEGATIVE");
                        if (caseCoApplicant != null) caseCoApplicant.negativeResponse(code);

                break;
           /* case GUARANTOR:
                final CaseGuarantor caseGuarantor = (CaseGuarantor) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 2);
                Log.e(LOG_TAG,"GUARANTOR NEGATIVE");
                        if (caseGuarantor != null) caseGuarantor.negativeResponse(code);

                break;*/
        }

    }

    public void loadCoApplicant() {
        callType = CallType.CO_APPLICANT;
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(CaseBasicId.requestVerificationToken, formMap.get(CaseBasicId.requestVerificationToken));
        formBody.add(CaseBasicId.id, formMap.get(CaseBasicId.id));
        formBody.add(CaseBasicId.hftab_id, formMap.get(CaseBasicId.hftab_id));
        formBody.add(CaseBasicId.personId, formMap.get(CaseBasicId.personId));
        formBody.add(CaseBasicId.addressId, formMap.get(CaseBasicId.addressId));
        formBody.add(CaseBasicId.companyaddressid, formMap.get(CaseBasicId.companyaddressid));
        formBody.add(CaseBasicId.action_changeTab, formMap.get(CaseBasicId.action_changeTab));

        RequestBody requestBody = formBody.build();

        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_EDIT_URL + caseid)
                .post(requestBody)
                .build();
        webHelper.requestCall(request, this);
    }

    private List parseCoApplicant(String html) {
        List<CoApplicantPojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("btnaddcoapplicant");
        if (element != null) {
            Log.e(LOG_TAG, "Oh hell!!!!!!!!!!!!!!!!!!");
            Element tbody = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody").first();
            Elements rows = tbody.getElementsByTag("tr");
            for (int i = 1; i < rows.size(); i++) {
                CoApplicantPojo pojo = new CoApplicantPojo();
                Elements datarows = rows.get(i).getElementsByTag("td");
                pojo.name = datarows.get(0).text();
                Log.e(LOG_TAG, "CoApplicant : " + pojo.name);
                String onClick = datarows.get(0).getElementsByTag("a").first().attr("onclick");
                pojo.addressid = onClick.substring(17, 24);
                pojo.caseid = caseid;
                pojo.address = datarows.get(1).text();
                pojo.mobile = datarows.get(2).text();
                pojo.assignedto = datarows.get(3).text();
                pojo.status = datarows.get(4).text();
                pojo.company_name = datarows.get(5).text();
                pojo.company_address = datarows.get(6).text();
                pojo.company_assignedto = datarows.get(7).text();
                pojo.company_status = datarows.get(8).text();
                list.add(pojo);
            }


        }
        if (list.size() == 0) {
            CoApplicantPojo pojo = new CoApplicantPojo();
            pojo.caseid = caseid;
            list.add(pojo);
        }
        return list;
    }

    public void loadGuarantor() {
        callType = CallType.GUARANTOR;
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(CaseBasicId.requestVerificationToken, formMap.get(CaseBasicId.requestVerificationToken));
        formBody.add(CaseBasicId.id, formMap.get(CaseBasicId.id));
        formBody.add(CaseBasicId.hftab_id, formMap.get(CaseBasicId.hftab_id));
        formBody.add(CaseBasicId.personId, formMap.get(CaseBasicId.personId));
        formBody.add(CaseBasicId.addressId, formMap.get(CaseBasicId.addressId));
        formBody.add(CaseBasicId.companyaddressid, formMap.get(CaseBasicId.companyaddressid));
        formBody.add(GuarantorId.action_changeTab, "Guarantor");

        RequestBody requestBody = formBody.build();

        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_EDIT_URL + caseid)
                .post(requestBody)
                .build();
        webHelper.requestCall(request, this);
    }

    public Map<String, String> parseGuarantor(String html) {
        Map<String, String> map = new HashMap<>();

        Document document = Jsoup.parse(html);

        String statusResidence = document.select("#trGuarResident > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(8) > td:nth-child(2)").text();
        String officeStatus = document.select("#trGuarOfficeDetail > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        map.put(GuarantorId.guarStatus, statusResidence);
        map.put(GuarantorId.guarOfficeStatus,officeStatus);

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for (Element input : elements) {
            String name = input.attr("name");
            String value = input.attr("value");

            String type = input.attr("type");
            if (type.equalsIgnoreCase("hidden")) {

            } else {
                map.put(name, value);
            }

          /*  if (name.equalsIgnoreCase(GuarantorId.haveGuarantor)) {
                String type = input.attr("type");
                if (type.equalsIgnoreCase("checkbox")) {
                    map.put(name, value);
                }
                continue;
            }
            map.put(name, value);*/

        }

        Elements selects = form.getElementsByTag("select");
        for (Element select : selects) {
            String id = select.id();
            //Log.e(LOG_TAG,id);
            try {
                String value = select.getElementsByAttributeValue("selected", "selected").first().attr("value");
                //Log.e(LOG_TAG,value);
                map.put(id, value);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                map.put(id, "");
            }
        }


        return map;
    }


    private enum CallType {
        BASIC_DETAIL,
        CO_APPLICANT,
        GUARANTOR
    }
}
