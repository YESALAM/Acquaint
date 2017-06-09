package io.github.yesalam.acquaint.Adapters;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.yesalam.acquaint.Fragments.CoApplicantDialog;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CoapplicantRecyclerAdapter extends RecyclerView.Adapter<CoapplicantRecyclerAdapter.ViewHolder> {

    private Fragment fragment;

    public CoapplicantRecyclerAdapter(Fragment fragment){
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_coapplicant,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(fragment.getFragmentManager(),"dialog");*/
                Intent intent = new Intent(v.getContext(), io.github.yesalam.acquaint.Activity.CoApplicantDialog.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
