package io.github.yesalam.acquaint.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.github.yesalam.acquaint.Fragments.InvestigationFilterDialog.FilterEventListener;
import io.github.yesalam.acquaint.WebHelper.CallBack;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.yesalam.acquaint.Activity.InvestigationActivity;
import io.github.yesalam.acquaint.Adapters.InvestigationRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WaitingForData;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.getAge;
import static io.github.yesalam.acquaint.WebHelper.INTERNET_ERROR;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 08-06-2017.
 */

public class NewInvestigationFragment extends Fragment implements WaitingForData, Callback, OnRefreshListener, CallBack,
        FilterEventListener {

    private String LOG_TAG = "NewInvestiFragment";

    InvestigationRecyclerAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    InvestigationActivity activity;
    View parentView;

    boolean filter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (InvestigationActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter:
                Log.e(LOG_TAG,"filter clicked");
                InvestigationFilterDialog dialog = new InvestigationFilterDialog();
                dialog.setNew(true);
                dialog.setFilterEventListener(this);
                dialog.show(getFragmentManager(),"filter");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_card, container, false);
        refreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview);
        adapter = new InvestigationRecyclerAdapter(new ArrayList<InvestigationPojo>());
        setupRecyclerView(recyclerView);

        try {
            List<InvestigationPojo> cachedEntries_newfield = (List<InvestigationPojo>) Util.readObject(getContext(), "newfield");
            if (cachedEntries_newfield.size() > 0) {

                passData(cachedEntries_newfield);
                long age = getAge(getContext(), "newfield");
                if (age == -1 || age > 30) {
                    loadData();
                }
            } else {
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            loadData();
        }

        return parentView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void passData(List<? extends Object> data) {
        adapter.setDataset((ArrayList<InvestigationPojo>) data);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    private void loadData() {
        refreshLayout.setRefreshing(true);
        filter = false ;
        String PAGE_URL = "/Users/FieldInvestigation/NewInvestigation";
        String LOAD_50 = "?pno=1&psize=50" ;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + PAGE_URL+LOAD_50)
                .build();

        WebHelper.getInstance(getContext()).requestCall(request,this);
    }

    private void loadData(String param){
        refreshLayout.setRefreshing(true);
        filter = true ;
        String PAGE_URL = "/Users/FieldInvestigation/NewInvestigation";

        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + PAGE_URL+param)
                .build();

        WebHelper.getInstance(getContext()).requestCall(request,this);

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

                    newInvestigationsResponesReader(html);

            }
        });
    }

    public void newInvestigationsResponesReader(String html) {
        Log.e(LOG_TAG, "called newField");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("searchClient");
        if (element == null) {
            //
            Log.e(LOG_TAG, "newField not loaded");
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                //progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                Toast.makeText(activity, "Service Unavailable! Please try later", Toast.LENGTH_SHORT).show();
            } else {
                //credentials mismatch
                Log.e(LOG_TAG, "not LoggedIn. try to login");
                //activity.login();
                loadData();
            }
        } else {
            Log.e(LOG_TAG, "newField loaded");
            ArrayList<InvestigationPojo> dataset = parseData(html);
            passData(dataset);
        }

    }

    private ArrayList<InvestigationPojo> parseData(String html) {
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

        // Save the list of entries to internal storage


        try {
            if(!filter)Util.writeObject(activity.getApplicationContext(), "newfield", dataset);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dataset;
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onPositiveResponse(final String html) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e(LOG_TAG, "newField loaded");
                ArrayList<InvestigationPojo> dataset = parseData(html);
                passData(dataset);

            }
        });
    }

    @Override
    public void onNegativeResponse(int code) {
        switch (code){
            case NO_CONNECTION:
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(refreshLayout.isRefreshing() && isVisible()){
                            Snackbar.make(parentView, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                                    //.setAction(R.string.snackbar_action, myOnClickListener)
                                    .show(); // Don’t forget to show!
                            refreshLayout.setRefreshing(false);

                        }else{
                            Toast.makeText(activity,"Internet Unavailable",Toast.LENGTH_SHORT).show();
                        }
                        Log.e(LOG_TAG,"No connection");
                    }
                });
                break;
            case INTERNET_ERROR:
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(refreshLayout.isRefreshing() && isVisible()){
                            Snackbar.make(parentView, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                                    //.setAction(R.string.snackbar_action, myOnClickListener)
                                    .show(); // Don’t forget to show!
                            refreshLayout.setRefreshing(false);

                        }else{
                            Toast.makeText(activity,"Internet Unavailable",Toast.LENGTH_SHORT).show();
                        }
                        Log.e(LOG_TAG,"Network failure");

                    }
                });
                break;


        }
    }

    @Override
    public void onFilter(InvestigationFilterDialog dialog) {
        String result = dialog.getResult();
        Log.e(LOG_TAG,result);
        loadData(result);
    }

    @Override
    public void onCancelFilter(InvestigationFilterDialog dialog) {

    }
}
