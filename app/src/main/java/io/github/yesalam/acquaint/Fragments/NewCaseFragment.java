package io.github.yesalam.acquaint.Fragments;

import android.app.WallpaperInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import io.github.yesalam.acquaint.Fragments.CaseFilterDialog.FilterEventListener;
import io.github.yesalam.acquaint.WebHelper.CallBack;
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
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.getAge;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 08-06-2017.
 */

public class NewCaseFragment extends Fragment implements WaitingForData, Callback, OnRefreshListener, CallBack,
        FilterEventListener {

    private String LOG_TAG = "NewCaseFragment";

    SwipeRefreshLayout refreshLayout;
    CaseRecyclerAdapter adapter;
    CaseActivity activity;
    View parentView;

    boolean isFilter = false ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (CaseActivity) context;

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
                CaseFilterDialog filterDialog = new CaseFilterDialog();
                filterDialog.setNew(true);
                filterDialog.setFilterEventListener(this);
                filterDialog.show(getFragmentManager(),"filter");
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


        adapter = new CaseRecyclerAdapter(new ArrayList<CasePojo>());
        setupRecyclerView(recyclerView);

        try {

            List<CasePojo> cachedEntries_newcase = (List<CasePojo>) Util.readObject(getContext(), "newcases");
            if (cachedEntries_newcase.size() > 0) {
                //progressBar.setVisibility(View.GONE);
                passData(cachedEntries_newcase);
                long age = getAge(getContext(), "newcases");
                if (age == -1 || age > 30) {
                    Log.e(LOG_TAG,"age  "+age);
                    loadData();
                }
            } else {
                //refreshLayout.setRefreshing(true);
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
        adapter.setDataset((ArrayList<CasePojo>) data);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    private void loadData() {
        refreshLayout.setRefreshing(true);
        isFilter = false ;
        //http://myacquaint.com/Users/Cases/NewCases?pno=1&psize=50
        String NEW_CASES_URL = "/Users/Cases/NewCases";
        String LOAD_50 = "?pno=1&psize=50" ;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + NEW_CASES_URL+LOAD_50)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + NEW_CASES_URL);
        WebHelper.getInstance(getContext()).requestCall(request, this);
    }

    private void loadData(String param){
        refreshLayout.setRefreshing(true);
        isFilter = true ;
        //http://myacquaint.com/Users/Cases/NewCases?pno=1&psize=50
        String NEW_CASES_URL = "/Users/Cases/NewCases";
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + NEW_CASES_URL+param)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + NEW_CASES_URL);
        WebHelper.getInstance(getContext()).requestCall(request, this);
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

    public void newCasesResponesReader(String html) {
        Log.e(LOG_TAG, "called newCases");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("searchBranchOffice");
        if (element == null) {
            Log.e(LOG_TAG, "newCases not loaded");
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
            Log.e(LOG_TAG, "newCases loaded");
            //progressBar.setVisibility(View.GONE);
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
            String candb = childs.get(1).html();
            casePojo.client = candb.replaceAll("<br>", "\n");
            casePojo.contactperson = childs.get(2).text();
            casePojo.name = childs.get(3).text();
            casePojo.loantype = childs.get(4).text();
            casePojo.pickupdate = childs.get(5).text();
            casePojo.punchedby = childs.get(6).text();
            casePojo.status = childs.get(7).text();
            dataset.add(casePojo);
        }


        try {
            if(!isFilter) Util.writeObject(activity.getApplicationContext(), "newcases", dataset);
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
                Log.e(LOG_TAG, "newCases loaded");
                //progressBar.setVisibility(View.GONE);
                ArrayList<CasePojo> dataset = parseData(html);
                passData(dataset);
            }
        });
    }

    @Override
    public void onNegativeResponse(int code) {
        switch (code) {
            case NO_CONNECTION:
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayout.isRefreshing() && isVisible()) {
                            Snackbar.make(parentView, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                                    //.setAction(R.string.snackbar_action, myOnClickListener)
                                    .show(); // Don’t forget to show!
                        } else {
                            Toast.makeText(activity, "Internet Unavailable", Toast.LENGTH_SHORT).show();
                        }
                        Log.e(LOG_TAG, "internet error");
                        refreshLayout.setRefreshing(false);
                    }
                });
                break;


        }
    }

    @Override
    public void onFilter(CaseFilterDialog dialog) {
        String filter_string = dialog.getResult();
        loadData(filter_string);
    }

    @Override
    public void onCancelFilter(CaseFilterDialog dialog) {

    }
}
