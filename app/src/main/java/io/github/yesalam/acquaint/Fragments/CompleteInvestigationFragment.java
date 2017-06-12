package io.github.yesalam.acquaint.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.yesalam.acquaint.Adapters.InvestigationRecyclerAdapter;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.WaitingForData;

/**
 * Created by yesalam on 08-06-2017.
 */

public class CompleteInvestigationFragment extends Fragment implements WaitingForData {

    InvestigationRecyclerAdapter adapter ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview,container,false);
        adapter = new InvestigationRecyclerAdapter(new ArrayList<InvestigationPojo>());
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void passData(ArrayList<? extends Object> data) {
        adapter.setDataset((ArrayList<InvestigationPojo>) data);
        adapter.notifyDataSetChanged();
    }
}
