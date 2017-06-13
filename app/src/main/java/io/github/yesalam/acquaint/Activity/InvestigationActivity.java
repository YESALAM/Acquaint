package io.github.yesalam.acquaint.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.Util.Util.*;
import io.github.yesalam.acquaint.WaitingForData;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 07-06-2017.
 */

public class InvestigationActivity extends BaseDrawerActivity {

    String LOG_TAG = "InvestigatonActivity";
    ProgressDialog progressDialog;
    boolean isLoading;
    boolean isRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data");
        progressDialog.setCancelable(false);

    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewInvestigationFragment(), "New");
        adapter.addFragment(new CompleteInvestigationFragment(), "Complete");
        adapter.addFragment(new TeleVerificationFragment(), "Tele-verification");
        viewPager.setAdapter(adapter);
    }

    public void loadNewFieldInvestigation() {
        if(isLoading){
            isRequested = true ;
            return;
        }
        else isLoading = true ;
        progressDialog.show();
        String PAGE_URL = "/Users/FieldInvestigation/NewInvestigation";
        Log.e(LOG_TAG, "loading " + PAGE_URL);
        final String case_url = ACQUAINT_URL + PAGE_URL;
        htmlJsInterface.setRequestType(AcquaintRequestType.NEW_FIELD_INVESTIGATION);
        webView.loadUrl(case_url);

    }

    public void loadComleteFieldInvestigaion() {
        if(isLoading){
            isRequested = true ;
            return;
        }
        else isLoading = true ;
        progressDialog.show();
        String PAGE_URL = "/Users/FieldInvestigation";
        Log.e(LOG_TAG, "loading " + PAGE_URL);
        final String case_url = ACQUAINT_URL + PAGE_URL;
        htmlJsInterface.setRequestType(AcquaintRequestType.COMPLETE_FIELD_INVESTIGATION);
        webView.loadUrl(case_url);
    }

    public void loadTeleVerification(){
        if(isLoading){
            isRequested = true ;
            return;
        }
        else isLoading = true ;
        progressDialog.show();
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
        } else if (htmlJsInterface.requestType == AcquaintRequestType.NEW_FIELD_INVESTIGATION) {
            isLoading = false ;
            List<InvestigationPojo> dataset = parseDataInvesti(response);
            WaitingForData fragment = (NewInvestigationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
            fragment.passData(dataset);
            if(isRequested) {
                isRequested = false ;
                loadComleteFieldInvestigaion();
            }
            else progressDialog.cancel();

        } else if (htmlJsInterface.requestType == AcquaintRequestType.COMPLETE_FIELD_INVESTIGATION) {
            isLoading = false ;
            List<InvestigationPojo> dataset = parseDataInvesti(response);
            WaitingForData fragment = (CompleteInvestigationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
            fragment.passData(dataset);
            if(isRequested) {
                isRequested = false ;
                loadTeleVerification();
            }
            else progressDialog.cancel();
        } else if(htmlJsInterface.requestType == AcquaintRequestType.TELE_VERIFICATION){
            isLoading = false ;
            List<TelePojo> dataset = parseDataTele(response);
            WaitingForData fragment = (TeleVerificationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 2);
            fragment.passData(dataset);
            if(isRequested) {
                isRequested = false ;
                loadNewFieldInvestigation();
            }
            else progressDialog.cancel();
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

    private List<TelePojo> parseDataTele(String html){
        List<TelePojo> dataset = new ArrayList<>();
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
        try {
            Util.writeObject(this, "tele", dataset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }


    private List<InvestigationPojo> parseDataInvesti(String html) {
        List<InvestigationPojo> dataset = new ArrayList<>();
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

        // Save the list of entries to internal storage

        try {
            if(htmlJsInterface.requestType == AcquaintRequestType.NEW_FIELD_INVESTIGATION)
            Util.writeObject(this, "newfield", dataset);
            else Util.writeObject(this, "completefield", dataset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }

}
