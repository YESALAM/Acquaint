package io.github.yesalam.acquaint.Fragments;

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
import android.widget.TableLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Activity.CaseActivity;
import io.github.yesalam.acquaint.Activity.CoApplicantDialog;
import io.github.yesalam.acquaint.Activity.IndiCaseActivity;
import io.github.yesalam.acquaint.Adapters.CaseRecyclerAdapter;
import io.github.yesalam.acquaint.Adapters.CoapplicantRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.CoApplicantPojo;
import io.github.yesalam.acquaint.Pojo.Card.TelePojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

/**
 * Created by yesalam on 08-06-2017.
 */

public class CaseCoApplicant extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    String LOG_TAG  = "CaseCoApplicant" ;
    IndiCaseActivity activity;
    CoapplicantRecyclerAdapter adapter ;
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.nocoapplicant_textview)
    TextView no_co_tt;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (IndiCaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_coapplicant,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        CoApplicantPojo pojo = new CoApplicantPojo();
        pojo.caseid =  activity.caseid;
        ArrayList<CoApplicantPojo> list = new ArrayList<CoApplicantPojo>();
        list.add(pojo);
        adapter = new CoapplicantRecyclerAdapter(list);
        setupRecyclerView(recyclerView);
        if(activity.co_applicants!=null) update(activity.co_applicants);
        else refreshLayout.setRefreshing(true);
        //loadTesst();
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }



    public void update(List co_applicants) {
        refreshLayout.setRefreshing(false);
        Log.e(LOG_TAG,"called update");
        adapter.setDataset(co_applicants);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        activity.loadCoApplicant();
    }
}
