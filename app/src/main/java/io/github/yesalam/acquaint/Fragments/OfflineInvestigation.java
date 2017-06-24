package io.github.yesalam.acquaint.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.yesalam.acquaint.Activity.Offline;
import io.github.yesalam.acquaint.Adapters.CaseRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;

/**
 * Created by yesalam on 25-06-2017.
 */

public class OfflineInvestigation extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String LOG_TAG = "OfflineCases" ;
    CaseRecyclerAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    View parentView ;

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


        RecyclerView recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview);



        adapter = new CaseRecyclerAdapter(new ArrayList<CasePojo>());
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
}
