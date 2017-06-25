package io.github.yesalam.acquaint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.yesalam.acquaint.Activity.FieldInvestigationDialog;
import io.github.yesalam.acquaint.Activity.FieldInvestigationOfficeDialoog;
import io.github.yesalam.acquaint.Fragments.OfflineInvestigation;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 06/25/17.
 */

public class OfflineInvestigationAdapter extends RecyclerView.Adapter<OfflineInvestigationAdapter.ViewHolder> {

    List<InvestigationPojo> dataset;
    OfflineInvestigation fragment;


    public void setDataset(List<InvestigationPojo> dataset) {
        this.dataset = dataset;

    }

    public OfflineInvestigationAdapter(OfflineInvestigation fragment, List<InvestigationPojo> dataset) {
        this.dataset = dataset;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_investigation, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.submitData(dataset.get(position));
            }
        });


        holder.id.setText(dataset.get(position).id);
        holder.name.setText(dataset.get(position).name);
        holder.type.setText(dataset.get(position).type);
        holder.address.setText(dataset.get(position).address);
        holder.client.setText(dataset.get(position).client);
        holder.casedetial.setText(dataset.get(position).casedetail);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView id;
        TextView name;
        TextView type;
        TextView address;
        TextView client;
        TextView casedetial;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            id = (TextView) view.findViewById(R.id.investigationId_investigation_card);
            name = (TextView) view.findViewById(R.id.applicant_name_investigation_card);
            type = (TextView) view.findViewById(R.id.location_type_investigation_card);
            address = (TextView) view.findViewById(R.id.address_investigation_card);
            client = (TextView) view.findViewById(R.id.client_investigation_card);
            casedetial = (TextView) view.findViewById(R.id.case_detail_investigation_card);
        }
    }
}
