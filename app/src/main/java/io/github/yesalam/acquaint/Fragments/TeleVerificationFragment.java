package io.github.yesalam.acquaint.Fragments;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.yesalam.acquaint.Activity.InvestigationActivity;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.Pojo.Card.TelePojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Adapters.TeleVeriRecyclerAdapter;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WaitingForData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 08-06-2017.
 */

public class TeleVerificationFragment extends Fragment implements WaitingForData, Callback {

    private String LOG_TAG = "TeleVeriFragment";

    TeleVeriRecyclerAdapter adapter;
    ProgressBar progressBar;

    InvestigationActivity activity;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (InvestigationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        adapter = new TeleVeriRecyclerAdapter(new ArrayList<TelePojo>());
        setupRecyclerView(recyclerView);

        try {
            List<InvestigationPojo> cachedEntries_tele = (List<InvestigationPojo>) Util.readObject(getContext(), "tele");
            if(cachedEntries_tele.size()>0){
                progressBar.setVisibility(View.GONE);
                passData(cachedEntries_tele);
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
        adapter.setDataset((ArrayList<TelePojo>) data);
        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        String PAGE_URL = "/Users/Verifications";
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + PAGE_URL)
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

                    teleVerificationResponesReader(html);

            }
        });
    }

    public void teleVerificationResponesReader(String html) {
        Log.e(LOG_TAG, "called teleVerification");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("searchClient");
        if (element == null) {
            //
            Log.e(LOG_TAG, "teleVerification not loaded");
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
        } else {
            Log.e(LOG_TAG, "teleVerification loaded");
            progressBar.setVisibility(View.GONE);
            ArrayList<TelePojo> dataset = parseData(html);
            passData(dataset);
        }

    }

    private ArrayList<TelePojo> parseData(String html){
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
        try {
            Util.writeObject(getContext(), "tele", dataset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }
}
