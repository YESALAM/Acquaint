package io.github.yesalam.acquaint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.yesalam.acquaint.Activity.FieldInvestigationDialog;
import io.github.yesalam.acquaint.Activity.FieldInvestigationOfficeDialoog;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 08-06-2017.
 */

public class InvestigationRecyclerAdapter extends RecyclerView.Adapter<InvestigationRecyclerAdapter.ViewHolder> {

    boolean isnewInvestigation;
    public InvestigationRecyclerAdapter(boolean isnewInvestigation){
        this.isnewInvestigation = isnewInvestigation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_investigation,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            if(isnewInvestigation){
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context,FieldInvestigationDialog.class);
                        TextView textView = (TextView) v.findViewById(R.id.investigationId_investigation_card);
                        String investigationid = (String) textView.getText();
                        intent.putExtra("investigationid",investigationid);
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
                        String investigationid = (String) textView.getText();
                        intent.putExtra("investigationid",investigationid);
                        context.startActivity(intent);
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView ;
        }
    }
}
