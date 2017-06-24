package io.github.yesalam.acquaint.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.yesalam.acquaint.Activity.CaseActivity;
import io.github.yesalam.acquaint.Activity.CreateCaseDialog;
import io.github.yesalam.acquaint.Activity.Offline;
import io.github.yesalam.acquaint.Adapters.CaseRecyclerAdapter;
import io.github.yesalam.acquaint.Adapters.OfflineCaseAdapter;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.MultipartBody;
import okhttp3.Request;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 25-06-2017.
 */

public class OfflineCases extends Fragment implements SwipeRefreshLayout.OnRefreshListener, WebHelper.CallBack {

    String LOG_TAG = "OfflineCases" ;
    OfflineCaseAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;
    View parentView ;

    String caseno;

    Offline activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (Offline) context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_card,container,false);
        refreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Submitting Data");
        progressDialog.setIndeterminate(true);


        RecyclerView recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview);



        adapter = new OfflineCaseAdapter(this,new ArrayList<CasePojo>());
        try {
            List<CasePojo> pendingCases = (List<CasePojo>) Util.readObject(getContext(), Util.PENDING_CASES);
            if(pendingCases.size()>0){
                passData(pendingCases);
                //progressBar.setVisibility(View.GONE);
            }else{
                //loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //loadData();
        }

        setupRecyclerView(recyclerView);
        return parentView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }



    public void passData(List<? extends Object> data) {
        adapter.setDataset((ArrayList<CasePojo>) data);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public void submit(String caseno){
        progressDialog.show();
        this.caseno = caseno;
        fetchCreateCase();
    }

    private void fetchCreateCase() {
        String CASE_CREATE_URL = "/Users/Cases/Create";
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_CREATE_URL)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + CASE_CREATE_URL);
        WebHelper.getInstance(getContext()).requestCall(request, this);
    }

    private Map<String,String> getMap(String caseno){
        Map<String,String> pendingCase = null ;
        try {
             pendingCase = (Map<String, String>) Util.readObject(getContext(), caseno);

        } catch (IOException e) {
            e.printStackTrace();
            //loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //loadData();
        }

        return pendingCase;
    }

    @Override
    public void onPositiveResponse(final String html) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map = getMap(caseno);
                submitMultiPart(map);
            }
        });
    }

    @Override
    public void onNegativeResponse(int code) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //cacheData();
                Toast.makeText(activity, "Internet Problem!", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });
    }

    public void submitMultiPart(Map<String, String> map) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : map.keySet()) {
            requestBodyBuilder.addFormDataPart(key, map.get(key));
        }

        MultipartBody requestBody = requestBodyBuilder.build();

        String CASE_CREATE_URL = "/Users/Cases/Create";

        Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_CREATE_URL)
                .post(requestBody)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + CASE_CREATE_URL + " submitting data");

        WebHelper.getInstance(getContext()).requestCall(request, new WebHelper.CallBack() {
            @Override
            public void onPositiveResponse(String html) {
                boolean deleted = getContext().deleteFile(caseno);
                Toast.makeText(activity, "Data Submited", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onNegativeResponse(int code) {
                //cacheData();
                Toast.makeText(activity, "Error Occured!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}
