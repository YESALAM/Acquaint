package io.github.yesalam.acquaint.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.yesalam.acquaint.Activity.InvestigationActivity;
import io.github.yesalam.acquaint.Adapters.InvestigationRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WaitingForData;

/**
 * Created by yesalam on 08-06-2017.
 */

public class NewInvestigationFragment extends Fragment implements WaitingForData {

    InvestigationRecyclerAdapter adapter;
    InvestigationActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (InvestigationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview,container,false);
        adapter = new InvestigationRecyclerAdapter(new ArrayList<InvestigationPojo>());
        setupRecyclerView(recyclerView);

        try {
            List<InvestigationPojo> cachedEntries_newfield = (List<InvestigationPojo>) Util.readObject(getContext(), "newfield");
            if(cachedEntries_newfield.size()>0){
                passData(cachedEntries_newfield);
            }else{
                activity.loadNewFieldInvestigation();
            }
        } catch (IOException e) {
            e.printStackTrace();
            activity.loadNewFieldInvestigation();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            activity.loadNewFieldInvestigation();
        }

        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void passData(List<? extends Object> data) {
        adapter.setDataset((ArrayList<InvestigationPojo>) data);
        adapter.notifyDataSetChanged();
    }
}
