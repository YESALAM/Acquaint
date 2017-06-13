package io.github.yesalam.acquaint.Fragments;

import android.app.WallpaperInfo;
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

import io.github.yesalam.acquaint.Activity.CaseActivity;
import io.github.yesalam.acquaint.Adapters.CaseRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WaitingForData;

/**
 * Created by yesalam on 08-06-2017.
 */

public class NewCaseFragment extends Fragment implements WaitingForData {

    CaseRecyclerAdapter adapter;

    CaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (CaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        adapter = new CaseRecyclerAdapter(new ArrayList<CasePojo>());
        try {
            List<CasePojo> cachedEntries_newcase = (List<CasePojo>) Util.readObject(getContext(), "newcases");
            if(cachedEntries_newcase.size()>0){
                passData(cachedEntries_newcase);
            }else{
                activity.loadNewCasePage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            activity.loadNewCasePage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            activity.loadNewCasePage();
        }

        setupRecyclerView(recyclerView);
        return recyclerView;
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
}
