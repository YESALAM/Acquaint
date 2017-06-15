package io.github.yesalam.acquaint.Adapters;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.Card.CoApplicantPojo;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CoapplicantRecyclerAdapter extends RecyclerView.Adapter<CoapplicantRecyclerAdapter.ViewHolder> {

    List<CoApplicantPojo> list;

    public CoapplicantRecyclerAdapter(List<CoApplicantPojo> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_coapplicant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(list.size() == 1 && list.get(0).addressid == null){
            holder.coapplicant_table.setVisibility(View.GONE);
            holder.no_coapplicant.setVisibility(View.VISIBLE);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), io.github.yesalam.acquaint.Activity.CoApplicantDialog.class);
                    intent.putExtra("caseid",list.get(0).caseid);
                    v.getContext().startActivity(intent);
                }
            });
        }else {

            holder.name.setText(list.get(position).name);
            holder.address.setText(list.get(position).address);
            holder.mobile.setText(list.get(position).mobile);
            holder.assignedto.setText(list.get(position).assignedto);
            holder.status.setText(list.get(position).status);
            holder.companyname.setText(list.get(position).company_name);
            holder.companyaddress.setText(list.get(position).company_address);
            holder.company_assignedto.setText(list.get(position).company_assignedto);
            holder.company_status.setText(list.get(position).company_status);

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), io.github.yesalam.acquaint.Activity.CoApplicantDialog.class);
                    intent.putExtra("caseid",list.get(position).caseid);
                    intent.putExtra("addressid",list.get(position).addressid);
                    v.getContext().startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.coapplicant_card_table)
        TableLayout coapplicant_table;
        @BindView(R.id.name_coapplicant_card)
        TextView name;
        @BindView(R.id.address_coapplicant_card)
        TextView address;
        @BindView(R.id.mobile_coapplicant_card)
        TextView mobile;
        @BindView(R.id.assigned_to_coapplicant_card)
        TextView assignedto;
        @BindView(R.id.status_coapplicant_card)
        TextView status;
        @BindView(R.id.companey_name_coapplicant_card)
        TextView companyname;
        @BindView(R.id.company_address_coapplicant_card)
        TextView companyaddress;
        @BindView(R.id.company_assigned_to_coapplicant_card)
        TextView company_assignedto;
        @BindView(R.id.company_status_coapplicant_card)
        TextView company_status;

        @BindView(R.id.nocoapplicant_textview)
        TextView no_coapplicant;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);


        }
    }
}
