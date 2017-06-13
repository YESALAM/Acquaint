package io.github.yesalam.acquaint.Fragments;

import android.app.WallpaperInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.yesalam.acquaint.Activity.CaseActivity;
import io.github.yesalam.acquaint.Adapters.CaseRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WaitingForData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 08-06-2017.
 */

public class NewCaseFragment extends Fragment implements WaitingForData, Callback {

    private String LOG_TAG = "NewCaseFragment" ;

    CaseRecyclerAdapter adapter;
    ProgressBar progressBar;

    CaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (CaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        adapter = new CaseRecyclerAdapter(new ArrayList<CasePojo>());
        setupRecyclerView(recyclerView);

        try {
            List<CasePojo> cachedEntries_newcase = (List<CasePojo>) Util.readObject(getContext(), "newcases");
            if(cachedEntries_newcase.size()>0){
                progressBar.setVisibility(View.GONE);
                passData(cachedEntries_newcase);
            }else{
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
           loadData();
        }


        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void passData(List<? extends Object> data) {
        adapter.setDataset((ArrayList<CasePojo>) data);
        adapter.notifyDataSetChanged();
    }

    private void loadData(){
        String NEW_CASES_URL = "/Users/Cases/NewCases";
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL+NEW_CASES_URL)
                .build();

        activity.okHttpClient.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        final String html = response.body().string();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    newCasesResponesReader(html);

            }
        });
    }

    public void newCasesResponesReader(String html){
        Log.e(LOG_TAG,"called newCases");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("searchBranchOffice");
        if(element == null){
            Log.e(LOG_TAG,"newCases not loaded");
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(activity, "Service Unavailable! Please try later", Toast.LENGTH_SHORT).show();
            } else {
                //credentials mismatch
                Log.e(LOG_TAG, "not LoggedIn. try to login");
                activity.login();
                loadData();
            }
        }else{
            Log.e(LOG_TAG,"newCases loaded");
            progressBar.setVisibility(View.GONE);
            ArrayList<CasePojo> dataset = parseData(html);
            passData(dataset);
        }
    }

    private ArrayList<CasePojo> parseData(String html) {
        ArrayList<CasePojo> dataset = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Element table = form.getElementsByClass("process-table").first();
        Elements rows = table.getElementsByTag("tr");
        for (int i = 1; i < rows.size(); i++) {
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


        try {
            Util.writeObject(getContext(), "newcases", dataset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }
}
