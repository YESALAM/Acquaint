package io.github.yesalam.acquaint.Activity;

import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import io.github.yesalam.acquaint.BaseDrawerActivity;
import io.github.yesalam.acquaint.Adapters.FragmentAdapter;
import io.github.yesalam.acquaint.Fragments.CompleteCaseFragment;
import io.github.yesalam.acquaint.Fragments.NewCaseFragment;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WaitingForData;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

public class CaseActivity extends BaseDrawerActivity {

    String LOG_TAG = "CaseActivity" ;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        checkLogin();
    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewCaseFragment(),"New");
        adapter.addFragment(new CompleteCaseFragment(),"Complete");
        viewPager.setAdapter(adapter);

    }


    private void loadNewCasePage(){
        String NEW_CASES_URL = "/Users/Cases/NewCases";
        Log.e(LOG_TAG, "loading "+NEW_CASES_URL);
        final String case_url = ACQUAINT_URL + NEW_CASES_URL;
        htmlJsInterface.setRequestType(Util.AcquaintRequestType.NEW_CASES);
        webView.loadUrl(case_url);
    }

    private void loadCompleteCasePage(){
        String NEW_CASES_URL = "/Users/Cases";
        Log.e(LOG_TAG, "loading "+NEW_CASES_URL);
        final String case_url = ACQUAINT_URL + NEW_CASES_URL;
        htmlJsInterface.setRequestType(Util.AcquaintRequestType.COMPLETE_CASES);
        webView.loadUrl(case_url);
    }


    @Override
    public void onDataParsedPasitive(String response) {
        if (htmlJsInterface.requestType == Util.AcquaintRequestType.LOGIN) {
            Log.e(LOG_TAG, "login successfull.");
            loadNewCasePage();
        } else if(htmlJsInterface.requestType == Util.AcquaintRequestType.NEW_CASES){
            ArrayList<CasePojo> dataset = parseData(response);
            WaitingForData fragment = (NewCaseFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
            fragment.passData(dataset);
            loadCompleteCasePage();
            //progressDialog.cancel();
        } else if(htmlJsInterface.requestType == Util.AcquaintRequestType.COMPLETE_CASES){
            ArrayList<CasePojo> dataset = parseData(response);
            WaitingForData fragment = (CompleteCaseFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
            fragment.passData(dataset);
        }


    }

    @Override
    public void onDataParserdNegative(String negative) {
        if(htmlJsInterface.requestType == Util.AcquaintRequestType.LOGIN){
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


    private ArrayList<CasePojo> parseData(String html){
        ArrayList<CasePojo> dataset = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Element table = form.getElementsByClass("process-table").first();
        Elements rows = table.getElementsByTag("tr");
        for(int i=1;i<rows.size();i++){
            CasePojo casePojo = new CasePojo();
            Elements childs = rows.get(i).getElementsByTag("td");
            casePojo.caseid = childs.get(0).text();
            casePojo.client = childs.get(1).text();
            casePojo.contactperson = childs.get(2).text();
            casePojo.name = childs.get(3).text();
            casePojo.loantype = childs.get(4).text();
            casePojo.pickupdate = childs.get(5).text();
            casePojo.punchedby = childs.get(6).text();
            casePojo.status = childs.get(7).text();
            dataset.add(casePojo);
        }
        return dataset;


    }
}
