package io.github.yesalam.acquaint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.yesalam.acquaint.Activity.IndiCaseActivity;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 08-06-2017.
 */

public class CaseRecyclerAdapter extends RecyclerView.Adapter<CaseRecyclerAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_case,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, IndiCaseActivity.class);
                //TODO MAKE it better
                TextView casenott = (TextView) v.findViewById(R.id.caseno_case_card);
                String caseno = (String) casenott.getText();
                intent.putExtra("caseno",caseno);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return 5;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        //TODO declare all textview to update
        TextView caseno;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

}
