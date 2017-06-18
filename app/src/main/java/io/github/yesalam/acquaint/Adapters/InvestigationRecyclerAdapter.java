package io.github.yesalam.acquaint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.yesalam.acquaint.Activity.FieldInvestigationDialog;
import io.github.yesalam.acquaint.Activity.FieldInvestigationOfficeDialoog;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 08-06-2017.
 */

public class InvestigationRecyclerAdapter extends RecyclerView.Adapter<InvestigationRecyclerAdapter.ViewHolder> {

    List<InvestigationPojo> dataset ;


    public void setDataset(List<InvestigationPojo> dataset){
        this.dataset = dataset ;
    }

    public InvestigationRecyclerAdapter(List<InvestigationPojo> dataset){
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_investigation,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            if(dataset.get(position).type.equalsIgnoreCase("Residence")){
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context,FieldInvestigationDialog.class);
                        TextView textView = (TextView) v.findViewById(R.id.investigationId_investigation_card);
                        TextView temp = (TextView) v.findViewById(R.id.client_investigation_card);
                        String investigationid = (String) textView.getText();
                        intent.putExtra("investigationid",investigationid);
                        intent.putExtra("client",temp.getText());
                        context.startActivity(intent);
                    }
                });
            }else{
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context,FieldInvestigationOfficeDialoog.class);
                        TextView textView = (TextView) v.findViewById(R.id.investigationId_investigation_card);
                        TextView temp = (TextView) v.findViewById(R.id.client_investigation_card);
                        String investigationid = (String) textView.getText();
                        intent.putExtra("investigationid",investigationid);
                        intent.putExtra("client",temp.getText());
                        context.startActivity(intent);
                    }
                });
            }

        holder.id.setText(dataset.get(position).id) ;
        holder.name.setText(dataset.get(position).name) ;
        holder.type.setText(dataset.get(position).type) ;
        holder.address.setText(dataset.get(position).address) ;
        holder.client.setText(dataset.get(position).client) ;
        holder.casedetial.setText(dataset.get(position).casedetail) ;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView id;
        TextView name;
        TextView type;
        TextView address;
        TextView client;
        TextView casedetial;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView ;
            id = (TextView) view.findViewById(R.id.investigationId_investigation_card);
            name = (TextView) view.findViewById(R.id.applicant_name_investigation_card);
            type = (TextView) view.findViewById(R.id.location_type_investigation_card);
            address = (TextView) view.findViewById(R.id.address_investigation_card);
            client = (TextView) view.findViewById(R.id.client_investigation_card);
            casedetial = (TextView) view.findViewById(R.id.case_detail_investigation_card);
        }
    }
}
