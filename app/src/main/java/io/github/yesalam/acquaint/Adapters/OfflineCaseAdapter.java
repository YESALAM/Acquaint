package io.github.yesalam.acquaint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.yesalam.acquaint.Activity.IndiCaseActivity;
import io.github.yesalam.acquaint.Fragments.OfflineCases;
import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 25-06-2017.
 */

public class OfflineCaseAdapter extends RecyclerView.Adapter<OfflineCaseAdapter.ViewHolder> {

    List<CasePojo> dataset = null ;
    OfflineCases fragment ;

    public OfflineCaseAdapter(OfflineCases fragment,List<CasePojo> dataset){
        this.dataset = dataset;
        this.fragment = fragment ;
    }

    public void setDataset(List<CasePojo> dataset){
        this.dataset = dataset;
    }


    @Override
    public OfflineCaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_case,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caseno = dataset.get(position).caseid ;
                fragment.submit(caseno,position);

            }
        });
        holder.caseno.setVisibility(View.GONE);
        holder.name.setText(dataset.get(position).name);
        holder.loantype.setText(dataset.get(position).loantype);
        holder.client.setText(dataset.get(position).client);
        holder.contactperson.setText(dataset.get(position).contactperson);
        holder.pickupdate.setText(dataset.get(position).pickupdate);
        holder.punchedby.setText(dataset.get(position).punchedby);
        holder.status.setText(dataset.get(position).status);
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView caseno;
        TextView name ;
        TextView loantype;
        TextView client;
        TextView contactperson;
        TextView pickupdate;
        TextView punchedby;
        TextView status;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            caseno = (TextView) view.findViewById(R.id.caseno_case_card);
            name = (TextView) view.findViewById(R.id.applicant_name_case_card);
            loantype = (TextView) view.findViewById(R.id.loan_type_case_card);
            client = (TextView) view.findViewById(R.id.client_case_card);
            contactperson = (TextView) view.findViewById(R.id.contact_person_case_card);
            pickupdate = (TextView) view.findViewById(R.id.pickup_date_case_card);
            punchedby = (TextView) view.findViewById(R.id.punchedby_case_card);
            status = (TextView) view.findViewById(R.id.status_case_card);
        }
    }
}
