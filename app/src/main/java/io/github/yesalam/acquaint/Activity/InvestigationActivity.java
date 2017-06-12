package io.github.yesalam.acquaint.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.Fragments.CompleteCaseFragment;
import io.github.yesalam.acquaint.Fragments.CompleteInvestigationFragment;
import io.github.yesalam.acquaint.Fragments.NewCaseFragment;
import io.github.yesalam.acquaint.Fragments.NewInvestigationFragment;
import io.github.yesalam.acquaint.Fragments.TeleVerificationFragment;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.Pojo.Card.TelePojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util.*;
import io.github.yesalam.acquaint.WaitingForData;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 07-06-2017.
 */

public class InvestigationActivity extends BaseDrawerActivity {

    String LOG_TAG = "InvestigatonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogin();

        getExternalCacheDir();

    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(NewInvestigationFragment.getInstance(), "New");
        adapter.addFragment(CompleteInvestigationFragment.getInstance(), "Complete");
        adapter.addFragment(TeleVerificationFragment.getInstance(), "Tele-verification");
        viewPager.setAdapter(adapter);
    }

    private void loadNewFieldInvestigation() {
        String PAGE_URL = "/Users/FieldInvestigation/NewInvestigation";
        Log.e(LOG_TAG, "loading " + PAGE_URL);
        final String case_url = ACQUAINT_URL + PAGE_URL;
        htmlJsInterface.setRequestType(AcquaintRequestType.NEW_FIELD_INVESTIGATION);
        webView.loadUrl(case_url);
    }

    private void loadComleteFieldInvestigaion() {
        String PAGE_URL = "/Users/FieldInvestigation";
        Log.e(LOG_TAG, "loading " + PAGE_URL);
        final String case_url = ACQUAINT_URL + PAGE_URL;
        htmlJsInterface.setRequestType(AcquaintRequestType.COMPLETE_FIELD_INVESTIGATION);
        webView.loadUrl(case_url);
    }

    private void loadTeleVerification(){
        String PAGE_URL = "/Users/Verifications";
        Log.e(LOG_TAG, "loading " + PAGE_URL);
        final String case_url = ACQUAINT_URL + PAGE_URL;
        htmlJsInterface.setRequestType(AcquaintRequestType.TELE_VERIFICATION);
        webView.loadUrl(case_url);
    }

    @Override
    public void onDataParsedPasitive(String response) {
        if (htmlJsInterface.requestType == AcquaintRequestType.LOGIN) {
            Log.e(LOG_TAG, "login successfull.");
            loadNewFieldInvestigation();
        } else if (htmlJsInterface.requestType == AcquaintRequestType.NEW_FIELD_INVESTIGATION) {
            ArrayList<InvestigationPojo> dataset = parseDataInvesti(response);
            WaitingForData fragment = (NewInvestigationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
            fragment.passData(dataset);
            loadComleteFieldInvestigaion();
            //progressDialog.cancel();
        } else if (htmlJsInterface.requestType == AcquaintRequestType.COMPLETE_FIELD_INVESTIGATION) {
            ArrayList<InvestigationPojo> dataset = parseDataInvesti(response);
            WaitingForData fragment = (CompleteInvestigationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
            fragment.passData(dataset);
            loadTeleVerification();
        } else if(htmlJsInterface.requestType == AcquaintRequestType.TELE_VERIFICATION){
            ArrayList<TelePojo> dataset = parseDataTele(response);
            WaitingForData fragment = (TeleVerificationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 2);
            fragment.passData(dataset);
            //loadTeleVerification();
        }
    }

    @Override
    public void onDataParserdNegative(String negative) {
        if (htmlJsInterface.requestType == AcquaintRequestType.LOGIN) {
            if (negative.equalsIgnoreCase("loginerror")) {
                Log.e(LOG_TAG, "credential mismatch");
                //should not happen
                login();
            } else if (negative.equalsIgnoreCase("noservice")) {
                Log.e(LOG_TAG, "problem with service.retrying");
                if (count < 1) {
                    login();
                    count++;
                }
            }
        }
    }

    private ArrayList<TelePojo> parseDataTele(String html){
        ArrayList<TelePojo> dataset = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Element table = form.getElementsByClass("process-table").first();
        Elements rows = table.getElementsByTag("tr");
        for (int i = 1; i < rows.size(); i++) {
            TelePojo pojo = new TelePojo();
            Elements childs = rows.get(i).getElementsByTag("td");
            pojo.id = childs.get(0).text();
            pojo.caseid = childs.get(1).text();
            pojo.client = childs.get(2).text();
            pojo.name = childs.get(3).text();
            pojo.pickupdate = childs.get(4).text();
            pojo.punchedby = childs.get(5).text();
            pojo.status = childs.get(6).text();
            dataset.add(pojo);
        }

        return dataset;
    }


    private ArrayList<InvestigationPojo> parseDataInvesti(String html) {
        ArrayList<InvestigationPojo> dataset = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Element table = form.getElementsByClass("process-table").first();
        Elements rows = table.getElementsByTag("tr");
        for (int i = 1; i < rows.size(); i++) {
            InvestigationPojo pojo = new InvestigationPojo();
            Elements childs = rows.get(i).getElementsByTag("td");
            pojo.id = childs.get(0).text();
            pojo.casedetail = childs.get(1).text();
            pojo.client = childs.get(2).text();
            pojo.name = childs.get(3).text();
            pojo.type = childs.get(4).text();
            pojo.address = childs.get(5).text();
            pojo.status = childs.get(6).text();
            dataset.add(pojo);
        }

        return dataset;
    }

}
