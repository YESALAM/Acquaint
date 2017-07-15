package io.github.yesalam.acquaint.Fragments;


import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static io.github.yesalam.acquaint.Util.Maps.getBranchHash;
import static io.github.yesalam.acquaint.Util.Maps.getClientHash;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getClientType;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getLoanTypes;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getStatusType;


/**
 * Created by yesalam on 7/15/17.
 */

public class CaseFilterDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */
    public interface FilterEventListener {
        public void onFilter(CaseFilterDialog dialog);

        public void onCancelFilter(CaseFilterDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    FilterEventListener mListener;

    public void setFilterEventListener(FilterEventListener mListener) {
        this.mListener = mListener;
    }

    @BindView(R.id.filter_client_spinner)
    Spinner client_spinner;
    @BindView(R.id.filter_branch_spinner)
    Spinner branch_spinner;
    @BindView(R.id.filter_contact_spinner)
    Spinner contact_spinner;
    @BindView(R.id.filter_name_edittext)
    EditText name_edittext;
    @BindView(R.id.filter_loantype_spinner)
    Spinner loantype_spinner;
    @BindView(R.id.filter_status_spinner)
    Spinner status_spinner;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Filter");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter_case,null);
        ButterKnife.bind(this,view);
        builder.setView(view);
        init();

        builder.setPositiveButton("Filter", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onFilter(CaseFilterDialog.this);
            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCancelFilter(CaseFilterDialog.this);
            }
        });


        // Create the AlertDialog object and return it
        return builder.create();
    }

    private String result;

    public String getResult(){
        StringBuilder builder = new StringBuilder();
        builder.append("?boid=103");

        try {
            int client = Integer.parseInt(((SpinnerItem) client_spinner.getSelectedItem()).getValue());
            if (client == 0) {
                //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                //return false;
                builder.append("&branchid=0&contactid=0");
            }else{
                builder.append("&clientid="+client+getBranch());
            }
        } catch (NumberFormatException nfe) {
        }

        String name = String.valueOf(name_edittext.getText());
        if(name.equalsIgnoreCase("")) ;
        else builder.append("&name="+name);

        String loan = ((SpinnerItem) loantype_spinner.getSelectedItem()).getValue() ;
        try {
            int loantype = Integer.parseInt(loan);
            if (loantype == 0) {
                //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                //return false;
                //builder.append("&branchid=0&contactid=0");
            }
        } catch (NumberFormatException nfe) {
            builder.append("&loantype="+loan);
        }

        String status = ((SpinnerItem) status_spinner.getSelectedItem()).getValue() ;
        try {
            int statustype = Integer.parseInt(status);
            if (statustype == 0) {
                //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                //return false;
                //builder.append("&branchid=0&contactid=0");
            }
        } catch (NumberFormatException nfe) {
            builder.append("&status="+status);
        }




        return builder.toString();
    }

    private String getBranch(){
        try {
            int branch = Integer.parseInt(((SpinnerItem) branch_spinner.getSelectedItem()).getValue());
            if (branch == 0) {
                //Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                //return false;
                return "&branchid=0&contactid=0";
            }else{
                return "&branchid="+branch+getContact();
            }
        } catch (NumberFormatException nfe) {
        }
        return null;
    }

    private String getContact(){
        try {
            int contact = Integer.parseInt(((SpinnerItem) contact_spinner.getSelectedItem()).getValue());
            return "&contactid="+contact;
        } catch (NumberFormatException nfe) {
        }
        return null;
    }


    private void init(){
        final ArrayAdapter<SpinnerItem> contactadapter = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        contactadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactadapter.add(new SpinnerItem("Select Contact Person", "0"));
        contact_spinner.setAdapter(contactadapter);


        final ArrayAdapter<SpinnerItem> branchadapter = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        branchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchadapter.add(new SpinnerItem("Select Branch", "0"));
        branch_spinner.setAdapter(branchadapter);
        branch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
                int val = Integer.parseInt(item.getValue());
                if (val == 0) return;
                String clientstring = getBranchHash().get(val);
                try {
                    JSONArray array = new JSONArray(clientstring);
                    ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String value = object.getString("id");
                        String name = object.getString("name");
                        list.add(new SpinnerItem(name, value));
                    }
                    contactadapter.clear();
                    contactadapter.addAll(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<SpinnerItem> clientadapter = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        clientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientadapter.addAll(getClientType());
        client_spinner.setAdapter(clientadapter);
        client_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
                int val = Integer.parseInt(item.getValue());
                if (val == 0) return;
                String branchstring = getClientHash().get(val);
                try {
                    JSONArray array = new JSONArray(branchstring);
                    ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String value = object.getString("id");
                        String name = object.getString("name");
                        list.add(new SpinnerItem(name, value));
                    }
                    branchadapter.clear();
                    branchadapter.addAll(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //loantype_spinner
        adapter.addAll(getLoanTypes());
        loantype_spinner.setAdapter(adapter);
        //loan_type_spinner.getSelectedItem();

        ArrayAdapter<SpinnerItem> residence_status = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        residence_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residence_status.addAll(getStatusType());
        status_spinner.setAdapter(residence_status);
    }
}
