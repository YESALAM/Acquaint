package io.github.yesalam.acquaint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yesalam.acquaint.Activity.TeleVerificationDialog;
import io.github.yesalam.acquaint.Pojo.Card.TelePojo;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 08-06-2017.
 */

public class TeleVeriRecyclerAdapter extends RecyclerView.Adapter<TeleVeriRecyclerAdapter.ViewHolder> {
    private ArrayList<TelePojo> dataset;


    public void setDataset(ArrayList<TelePojo> dataset) {
        this.dataset = dataset;
    }

    public TeleVeriRecyclerAdapter(ArrayList<TelePojo> dataset) {
        this.dataset = dataset;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_televerification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, TeleVerificationDialog.class);
                TextView textView = (TextView) v.findViewById(R.id.investigationId_tele_card);
                String investigationId = (String) textView.getText();
                intent.putExtra("investigationid", investigationId);
                context.startActivity(intent);
            }
        });

        holder.id.setText(dataset.get(position).id);
        holder.name.setText(dataset.get(position).name);
        holder.caseid.setText(dataset.get(position).caseid);
        holder.client.setText(dataset.get(position).client);
        holder.pickupdate.setText(dataset.get(position).pickupdate);
        holder.punchedby.setText(dataset.get(position).punchedby);
        holder.status.setText(dataset.get(position).status);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView id;
        TextView name;
        TextView caseid;
        TextView client;
        TextView pickupdate;
        TextView punchedby;
        TextView status;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            id = (TextView) view.findViewById(R.id.investigationId_tele_card);
            name = (TextView) view.findViewById(R.id.applicant_name_tele_card);
            caseid = (TextView) view.findViewById(R.id.caseid_tele_card);
            client = (TextView) view.findViewById(R.id.client_tele_card);
            pickupdate = (TextView) view.findViewById(R.id.pickup_date_tele_card);
            punchedby = (TextView) view.findViewById(R.id.punchedby_tele_card);
            status = (TextView) view.findViewById(R.id.status_tele_card);

        }
    }
}
