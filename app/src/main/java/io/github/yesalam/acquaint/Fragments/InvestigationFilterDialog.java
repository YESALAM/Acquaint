package io.github.yesalam.acquaint.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;

import static io.github.yesalam.acquaint.Util.SpinnerLists.getClientType;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getInvestigationType;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getStatusType;


/**
 * Created by yesalam on 7/15/17.
 */

public class InvestigationFilterDialog extends DialogFragment {

    private boolean isNew ;
    private boolean isTele = false;

    public void setNew(boolean isNew){
        this.isNew = isNew;
    }

    public void setTele(boolean isTele){
        this.isTele = isTele ;
    }

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */
    public interface FilterEventListener {
        public void onFilter(InvestigationFilterDialog dialog);

        public void onCancelFilter(InvestigationFilterDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    FilterEventListener mListener;

    public void setFilterEventListener(FilterEventListener mListener) {
        this.mListener = mListener;
    }

    @BindView(R.id.filter_client_spinner)
    Spinner client_spinner;
    @BindView(R.id.filter_name_edittext)
    EditText name_edittext;
    @BindView(R.id.filter_type_spinner)
    Spinner type_spinner;
    @BindView(R.id.filter_status_spinner)
    Spinner status_spinner;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Filter");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter_investigation, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        init();

        builder.setPositiveButton("Filter", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onFilter(InvestigationFilterDialog.this);
            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCancelFilter(InvestigationFilterDialog.this);
            }
        });


        // Create the AlertDialog object and return it
        return builder.create();
    }

    public String getResult() {
        StringBuilder builder = new StringBuilder();
        builder.append("?");

        try {
            int client = Integer.parseInt(((SpinnerItem) client_spinner.getSelectedItem()).getValue());
            if (client == 0) {
                //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                //return false;
                //builder.append("&branchid=0&contactid=0");
            } else {
                builder.append("&clientid=" + client);
            }
        } catch (NumberFormatException nfe) {
        }

        String name = String.valueOf(name_edittext.getText());
        if (name.equalsIgnoreCase("")) {

        } else {
            builder.append("&name=" + name);
        }

       if(!isTele){
           String type = ((SpinnerItem) type_spinner.getSelectedItem()).getValue();
           try {
               int itype = Integer.parseInt(type);
               if (itype == 0) {
                   //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                   //return false;
                   //builder.append("&branchid=0&contactid=0");
               }
           } catch (NumberFormatException nfe) {
               builder.append("&type=" + type);
           }
       }

       if(isNew || isTele){

       }else {
           String status = ((SpinnerItem) status_spinner.getSelectedItem()).getValue();
           try {
               int statustype = Integer.parseInt(status);
               if (statustype == 0) {
                   //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                   //return false;
                   //builder.append("&branchid=0&contactid=0");
               }
           } catch (NumberFormatException nfe) {
               builder.append("&status=" + status);
           }
       }


        return builder.toString();
    }





    private void init() {
        ArrayAdapter<SpinnerItem> clientadapter =
                new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        clientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientadapter.addAll(getClientType());
        client_spinner.setAdapter(clientadapter);


        if(isTele){
            type_spinner.setVisibility(View.GONE);
        }else{
            ArrayAdapter<SpinnerItem> adapter =
                    new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //loantype_spinner
            adapter.addAll(getInvestigationType());
            type_spinner.setAdapter(adapter);
            //loan_type_spinner.getSelectedItem();
        }


        if(isNew || isTele){
            status_spinner.setVisibility(View.GONE);
        }else{
            ArrayAdapter<SpinnerItem> residence_status =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
            residence_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            residence_status.addAll(getStatusType());
            status_spinner.setAdapter(residence_status);
        }

    }
}
