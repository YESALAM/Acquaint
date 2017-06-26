package io.github.yesalam.acquaint.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.CaseBasicId;
import io.github.yesalam.acquaint.Util.Id.GuarantorId;
import io.github.yesalam.acquaint.Util.Id.OfficeId;
import io.github.yesalam.acquaint.Util.Id.ResidentialId;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Listener.HaveClickListener;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static io.github.yesalam.acquaint.Util.Maps.*;
import static io.github.yesalam.acquaint.Util.SpinnerLists.*;
import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.ACTION_CANCEL;
import static io.github.yesalam.acquaint.Util.Util.ACTION_REMARK;
import static io.github.yesalam.acquaint.Util.Util.ACTION_SUP_REMARK;
import static io.github.yesalam.acquaint.Util.Util.PENDING_CASES;
import static io.github.yesalam.acquaint.Util.Util.deleteCache;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CreateCaseDialog extends AppCompatActivity implements WebHelper.CallBack {

    private static final String LOG_TAG = "CreateCaseDialog";
    //View Binding
    //basic detail
    @BindView(R.id.client_spinner)
    Spinner client_spinner;
    @BindView(R.id.branch_spinner)
    Spinner branch_spinner;
    @BindView(R.id.contact_person_spinner)
    Spinner contact_person_spinner;
    @BindView(R.id.loan_type_spinner)
    Spinner loan_type_spinner;
    @BindView(R.id.pickup_date_edittext)
    EditText pickupdate_edittext;
    @BindView(R.id.radio_group_reverificatoin)
    RadioGroup reverification_radiogroup;
    @BindView(R.id.pickup_by_spinner)
    Spinner pickupby_spinner;
    @BindView(R.id.loan_amount_edittext)
    EditText loanamount_edittext;
    @BindView(R.id.loan_tenure_edittext)
    EditText loantenure_edittext;
    @BindView(R.id.application_refno_edittext)
    EditText applicationrefno_edittext;
    @BindView(R.id.email_row)
    TableRow email_row;
    @BindView(R.id.status_row)
    TableRow staus_row;

    //applicant_resident_detail
    @BindView(R.id.name_residential_detail_edittext)
    EditText name_resident_edittext;
    @BindView(R.id.dateofbirth_residential_detail_edittext)
    EditText dob_edittext;
    @BindView(R.id.pan_residential_detail_edittext)
    EditText pan_edittext;
    @BindView(R.id.radio_group_gender_residential_detail)
    RadioGroup gender_radiogroup;
    @BindView(R.id.address_residential_detail_edittext)
    EditText address_residential_edittext;
    @BindView(R.id.city_residential_detail_edittext)
    EditText city_residential_edittext;
    @BindView(R.id.state_residential_detail_edittext)
    EditText state_residential_edittxt;
    @BindView(R.id.pin_residential_detail_edittext)
    EditText pin_residential_edittext;
    @BindView(R.id.email_residential_detail_edittext)
    EditText email_residential_edittext;
    @BindView(R.id.mobile_residential_detail_edittext)
    EditText mobile_residential_edittext;
    @BindView(R.id.phon_residential_detail_edittext)
    EditText phone_residential_edittext;
    @BindView(R.id.assigned_to_residential_detail_spinner)
    Spinner assignedto_residential_spinner;
    @BindView(R.id.investigation_status_row_residential_detail)
    TableRow investiagatonstatusrow_residential_tablerow;

    //RAdio have company address
    @BindView(R.id.have_company_address_radiobutton)
    CheckBox havecompany_radiobutton;

    //Applicant_office_detail
    @BindView(R.id.applicant_office_details_frame)
    FrameLayout applicant_office_frame;
    @BindView(R.id.company_name_office_detail_edittext)
    EditText companyname_office_edittext;
    @BindView(R.id.address_office_detail_edittext)
    EditText address_office_edittext;
    @BindView(R.id.city_office_detail_edittext)
    EditText city_office_edittext;
    @BindView(R.id.state_office_detail_edittext)
    EditText state_office_edittext;
    @BindView(R.id.mobile_office_detail_edittext)
    EditText mobile_office_edittext;
    @BindView(R.id.phon_office_detail_edittext)
    EditText phone_office_edittext;
    @BindView(R.id.need_verification_office_detail_radiobutton)
    CheckBox needverification_office_radiobutton;
    @BindView(R.id.assigned_to_office_detail_spinner)
    Spinner assignedto_office_spinner;
    @BindView(R.id.status_row_office_detail)
    TableRow statusrow_office_tablerow;

    @BindView(R.id.have_guarantor_residential_radiobutton)
    CheckBox have_guarantor_radiobutton;
    @BindView(R.id.guarantor_residential_detail_frame)
    LinearLayout guarantor_residential_frame;
    @BindView(R.id.name_guarantor_residential_detail_edittext)
    EditText name_guarantor_edittext;
    @BindView(R.id.dateofbirth_guarantor_residential_detail_edittext)
    EditText dob_guarantor_edittext;
    @BindView(R.id.pan_guarantor_residential_detail_edittext)
    EditText pan_guarantor_edittext;
    @BindView(R.id.radio_group_gender_guarantor_residential_detail)
    RadioGroup gender_guarantor_radiogroup;
    @BindView(R.id.address_guarantor_residential_detail_edittext)
    EditText address_guarantor_edittext;
    @BindView(R.id.city_guarantor_residential_detail_edittext)
    EditText city_guarantor_edittext;
    @BindView(R.id.state_guarantor_residential_detail_edittext)
    EditText state_guarantor_edittext;
    @BindView(R.id.pin_guarantor_residential_detail_edittext)
    EditText pin_guarantor_edittext;
    @BindView(R.id.email_guarantor_residential_detail_edittext)
    EditText email_guarantor_edittext;
    @BindView(R.id.mobile_guarantor_residential_detail_edittext)
    EditText mobile_guarantor_edittext;
    @BindView(R.id.phon_guarantor_residential_detail_edittext)
    EditText phone_guarantor_edittex;
    @BindView(R.id.need_verification_guarantor_residential_radiobutton)
    CheckBox needverificaton_guarantor_radiobutton;
    @BindView(R.id.assigned_to_guarantor_residential_detail_spinner)
    Spinner assignedto_guarantor_spinner;

    //RAdio button have office address
    @BindView(R.id.have_guarantor_office_address)
    CheckBox haveguarantoroffice_radiobutton;

    //Guarantor_office_detail
    @BindView(R.id.guarantor_office_details_frame)
    FrameLayout guarantor_office_frame;
    @BindView(R.id.company_name_guarantor_office_details_edittext)
    EditText companyname_guarantoroffice_edittext;
    @BindView(R.id.address_guarantor_office_details_edittext)
    EditText address_guarantoroffice_edittext;
    @BindView(R.id.city_guarantor_office_details_edittext)
    EditText city_guarantoroffice_edittext;
    @BindView(R.id.state_guarantor_office_details_edittext)
    EditText state_guarantoroffice_edittext;
    @BindView(R.id.mobile_guarantor_office_details_edittext)
    EditText mobile_guarantoroffice_edittext;
    @BindView(R.id.phon_guarantor_office_details_edittext)
    EditText phone_guarantoroffice_edittext;
    @BindView(R.id.need_verification_guarantor_office_details_radiobutton)
    CheckBox needverification_guarantoroffice_radiobutton;
    @BindView(R.id.assigned_to_guarantor_office_details_spinner)
    Spinner assignedto_guarantoroffice_spinner;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_case);
        ButterKnife.bind(this);

        prepareForm();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Detail");
        progressDialog.setIndeterminate(true);
    }


    private void prepareForm() {


        email_row.setVisibility(View.GONE);
        staus_row.setVisibility(View.GONE);
        investiagatonstatusrow_residential_tablerow.setVisibility(View.GONE);

        final ArrayAdapter<SpinnerItem> contactadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        contactadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactadapter.add(new SpinnerItem("Select Contact Person", "0"));
        contact_person_spinner.setAdapter(contactadapter);


        final ArrayAdapter<SpinnerItem> branchadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
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


        ArrayAdapter<SpinnerItem> clientadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
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


        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //loantype_spinner
        adapter.addAll(getLoanTypes());
        loan_type_spinner.setAdapter(adapter);
        //loan_type_spinner.getSelectedItem();


        pickupdate_edittext.setOnClickListener(new DateClick(this));


        ArrayAdapter<SpinnerItem> pickupbyadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        pickupbyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickupbyadapter.addAll(getPickupByType());
        pickupby_spinner.setAdapter(pickupbyadapter);

        dob_edittext.setOnClickListener(new DateClick(this));


        ArrayAdapter<SpinnerItem> assignedtoadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        assignedtoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedtoadapter.addAll(getAssignedToType());
        assignedto_residential_spinner.setAdapter(assignedtoadapter);


        havecompany_radiobutton.setChecked(false);
        applicant_office_frame.setVisibility(View.GONE);
        HaveClickListener haveClickListener = new HaveClickListener(applicant_office_frame);
        havecompany_radiobutton.setOnClickListener(haveClickListener);

        ArrayAdapter<SpinnerItem> assignedto_officeadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        assignedto_officeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_officeadapter.addAll(getAssignedToType());
        assignedto_office_spinner.setAdapter(assignedto_officeadapter);

        guarantor_residential_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantorListener = new HaveClickListener(guarantor_residential_frame);
        have_guarantor_radiobutton.setOnClickListener(haveguarantorListener);

        dob_guarantor_edittext.setOnClickListener(new DateClick(this));

        ArrayAdapter<SpinnerItem> assignedto_gurantor = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        assignedto_gurantor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantor.addAll(getAssignedToType());
        assignedto_guarantor_spinner.setAdapter(assignedto_gurantor);

        guarantor_office_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantoroffice = new HaveClickListener(guarantor_office_frame);
        haveguarantoroffice_radiobutton.setOnClickListener(haveguarantoroffice);


        ArrayAdapter<SpinnerItem> assignedto_gurantoroffice = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        assignedto_gurantoroffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantoroffice.addAll(getAssignedToType());
        assignedto_guarantoroffice_spinner.setAdapter(assignedto_gurantoroffice);


    }


    public void save(View view) {
        if (checkFields()) {
            //asked for seriousness
            areYouSure();
        }

    }

    private void fetchCreateCase(WebHelper webHelper) {
        String CASE_CREATE_URL = "/Users/Cases/Create";
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_CREATE_URL)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + CASE_CREATE_URL);
        webHelper.requestCall(request, this);
    }

    private void cacheData() {
        Map map = getValues();
        CasePojo pojo = new CasePojo();
        Date date = new Date();
        pojo.caseid = String.valueOf(date.hashCode());
        pojo.name = String.valueOf(name_resident_edittext.getText());
        pojo.client = client_spinner.getSelectedItem().toString();
        pojo.branch = branch_spinner.getSelectedItem().toString();
        pojo.contactperson = contact_person_spinner.getSelectedItem().toString();
        pojo.loantype = loan_type_spinner.getSelectedItem().toString();
        pojo.pickupdate = String.valueOf(pickupdate_edittext.getText());
        pojo.punchedby = pickupby_spinner.getSelectedItem().toString();

        try {
            Util.writeObject(getApplicationContext(), pojo.caseid, map);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "map writing error");
        }


        try {
            List<CasePojo> pendingCases = (List<CasePojo>) Util.readObject(getApplicationContext(), Util.PENDING_CASES);
            if (pendingCases.size() > 0) {
                //progressBar.setVisibility(View.GONE);
                //passData(cachedEntries_newcase);
                pendingCases.add(pojo);
            } else {
                //refreshLayout.setRefreshing(true);
                pendingCases.add(pojo);
            }
            Util.writeObject(getApplicationContext(), PENDING_CASES, pendingCases);
        } catch (FileNotFoundException e) {
            List<CasePojo> pendingCases = new ArrayList<>();
            pendingCases.add(pojo);
            try {
                Util.writeObject(getApplicationContext(), PENDING_CASES, pendingCases);
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.e(LOG_TAG, "new pendingCase writing error");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "pendingCase writing error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Connection Unavailable! Data will be Saved", Toast.LENGTH_SHORT).show();
    }

    public void areYouSure() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        WebHelper webHelper = WebHelper.getInstance(getApplicationContext());
                        if (webHelper.isConnected()) {
                            progressDialog.show();
                            fetchCreateCase(webHelper);
                        } else {
                            cacheData();
                        }
                        //finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


    public void cancel(View view) {
        finish();
    }

    private boolean checkFields() {
        try {
            int client = Integer.parseInt(((SpinnerItem) client_spinner.getSelectedItem()).getValue());
            if (client == 0) {
                Toast.makeText(this, "Client is Missing", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException nfe) {
        }

        try {
            int branch = Integer.parseInt(((SpinnerItem) branch_spinner.getSelectedItem()).getValue());
            if (branch == 0) {
                Toast.makeText(this, "Branch is Missing", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException nfe) {
        }

        try {
            int contachPerson = Integer.parseInt(((SpinnerItem) contact_person_spinner.getSelectedItem()).getValue());
            if (contachPerson == 0) {
                Toast.makeText(this, "Contact Person is Missing", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException nfe) {
        }

        try {
            int loanType = Integer.parseInt(((SpinnerItem) loan_type_spinner.getSelectedItem()).getValue());
            if (loanType == 0) {
                Toast.makeText(this, "Loan Type is Missing", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException nfe) {

        }


        try {
            int pickup = Integer.parseInt(((SpinnerItem) pickupby_spinner.getSelectedItem()).getValue());
            if (pickup == 0) {
                Toast.makeText(this, "Pickup By is Missing", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException nfe) {
        }

       /* try{
            int assignedTo = Integer.parseInt(((SpinnerItem)assignedto_residential_spinner.getSelectedItem()).getValue());
            if(assignedTo == 0){
                Toast.makeText(this, "AssignedTo  is Missing", Toast.LENGTH_LONG).show();
                return false;
            }
        }catch (NumberFormatException nfe){}*/


        String pickupdate = String.valueOf(pickupdate_edittext.getText());
        if (pickupdate.equalsIgnoreCase("")) {
            Toast.makeText(this, "Pickup Date is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String name = String.valueOf(name_resident_edittext.getText());
        if(name.equalsIgnoreCase("")){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String address = String.valueOf(address_residential_edittext.getText());
        if(address.equalsIgnoreCase("")){
            Toast.makeText(this, "Address is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String city = String.valueOf(city_residential_edittext.getText());
        if(city.equalsIgnoreCase("")){
            Toast.makeText(this, "City is empty", Toast.LENGTH_LONG).show();
            return false;
        }


        String state = String.valueOf(state_residential_edittxt.getText());
        if(state.equalsIgnoreCase("")){
            Toast.makeText(this, "State is empty", Toast.LENGTH_LONG).show();
            return false;
        }



        if (havecompany_radiobutton.isChecked()) {
            if (needverification_office_radiobutton.isChecked()) {
                try {
                    int assignedTooffice = Integer.parseInt(((SpinnerItem) assignedto_office_spinner.getSelectedItem()).getValue());
                    if (assignedTooffice == 0) {
                        Toast.makeText(this, "AssignedTo Office is Missing", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (NumberFormatException nfe) {
                }
            }
        }

        if (have_guarantor_radiobutton.isChecked()) {
            if (needverificaton_guarantor_radiobutton.isChecked()) {
                try {
                    int assignedToGuarantor = Integer.parseInt(((SpinnerItem) assignedto_guarantor_spinner.getSelectedItem()).getValue());
                    if (assignedToGuarantor == 0) {
                        Toast.makeText(this, "AssignedTo Guarantor  is Missing", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (NumberFormatException nfe) {
                }
            }
        }

        if (haveguarantoroffice_radiobutton.isChecked()) {
            if (needverification_guarantoroffice_radiobutton.isChecked()) {
                try {
                    int assignedToGuarantorOffice = Integer.parseInt(((SpinnerItem) assignedto_guarantoroffice_spinner.getSelectedItem()).getValue());
                    if (assignedToGuarantorOffice == 0) {
                        Toast.makeText(this, "AssignedTo GuarantorOffice is Missing", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (NumberFormatException nfe) {
                }
            }
        }


        return true;
    }

    private Map<String, String> getValues() {
        Map<String, String> map = new HashMap<>();

        map.put(CaseBasicId.client, ((SpinnerItem) client_spinner.getSelectedItem()).getValue());
        map.put(CaseBasicId.branch, ((SpinnerItem) branch_spinner.getSelectedItem()).getValue());
        map.put(CaseBasicId.contactPerson, ((SpinnerItem) contact_person_spinner.getSelectedItem()).getValue());
        map.put(CaseBasicId.loantype, ((SpinnerItem) loan_type_spinner.getSelectedItem()).getValue());
        map.put(CaseBasicId.pickupDate, String.valueOf(pickupdate_edittext.getText()));

        String is_reVerification = reverification_radiogroup.getCheckedRadioButtonId() == R.id.radio_button_yes_reverification ? "true" : "false";
        map.put(CaseBasicId.isReVerification, is_reVerification);

        map.put(CaseBasicId.loanAmount, String.valueOf(loanamount_edittext.getText()));
        map.put(CaseBasicId.loanTenure, String.valueOf(loantenure_edittext.getText()));
        map.put(CaseBasicId.applicationRefNo, String.valueOf(applicationrefno_edittext.getText()));

        map.put(CaseBasicId.pickupBy, ((SpinnerItem) pickupby_spinner.getSelectedItem()).getValue());

        map.put(ResidentialId.name, String.valueOf(name_resident_edittext.getText()));
        map.put(ResidentialId.dateOfBirth, String.valueOf(dob_edittext.getText()));
        map.put(ResidentialId.pan, String.valueOf(pan_edittext.getText()));

        String gender = gender_radiogroup.getCheckedRadioButtonId() == R.id.radio_button_female_residential_detail ? "F" : "M";
        map.put(ResidentialId.gender, gender);

        map.put(ResidentialId.address, String.valueOf(address_residential_edittext.getText()));
        map.put(ResidentialId.city, String.valueOf(city_residential_edittext.getText()));
        map.put(ResidentialId.state, String.valueOf(state_residential_edittxt.getText()));
        map.put(ResidentialId.pin, String.valueOf(pin_residential_edittext.getText()));
        map.put(ResidentialId.email, String.valueOf(email_residential_edittext.getText()));
        map.put(ResidentialId.mobile, String.valueOf(mobile_residential_edittext.getText()));
        map.put(ResidentialId.phone, String.valueOf(phone_residential_edittext.getText()));

        map.put(ResidentialId.assignedTo, ((SpinnerItem) assignedto_residential_spinner.getSelectedItem()).getValue());

        if (havecompany_radiobutton.isChecked()) {
            map.put(ResidentialId.haveCompany, "true");
            map.put(OfficeId.companyName, String.valueOf(companyname_office_edittext.getText()));
            map.put(OfficeId.address, String.valueOf(address_office_edittext.getText()));
            map.put(OfficeId.city, String.valueOf(city_office_edittext.getText()));
            map.put(OfficeId.state, String.valueOf(state_office_edittext.getText()));
            map.put(OfficeId.mobile, String.valueOf(mobile_office_edittext.getText()));
            map.put(OfficeId.phone, String.valueOf(phone_office_edittext.getText()));

            if (needverification_office_radiobutton.isChecked()) {
                map.put(OfficeId.companyNeedsVerification, "true");
                map.put(OfficeId.assignedTo, ((SpinnerItem) assignedto_office_spinner.getSelectedItem()).getValue());
            }

        }

        if (have_guarantor_radiobutton.isChecked()) {
            map.put(ResidentialId.haveGuarantor, "true");
            map.put(GuarantorId.guarName, String.valueOf(name_guarantor_edittext.getText()));
            map.put(GuarantorId.guarDOB, String.valueOf(dob_guarantor_edittext.getText()));
            map.put(GuarantorId.guarPAN, String.valueOf(pan_guarantor_edittext.getText()));

            String guarGender = gender_guarantor_radiogroup.getCheckedRadioButtonId() == R.id.radio_button_female_guarantor_residential_detail ? "F" : "M";
            map.put(GuarantorId.guarGender, guarGender);

            map.put(GuarantorId.guarAddress, String.valueOf(address_guarantor_edittext.getText()));
            map.put(GuarantorId.guarCity, String.valueOf(city_guarantor_edittext.getText()));
            map.put(GuarantorId.guarState, String.valueOf(state_guarantor_edittext.getText()));
            map.put(GuarantorId.guarPin, String.valueOf(pin_guarantor_edittext.getText()));
            map.put(GuarantorId.guarEMail, String.valueOf(email_guarantor_edittext.getText()));
            map.put(GuarantorId.guarMobile, String.valueOf(mobile_guarantor_edittext.getText()));
            map.put(GuarantorId.guarPhone, String.valueOf(phone_guarantor_edittex.getText()));
            if (needverificaton_guarantor_radiobutton.isChecked()) {
                map.put(GuarantorId.guarNeedsVerification, "true");
                map.put(GuarantorId.guarAssignedTo, ((SpinnerItem) assignedto_guarantor_spinner.getSelectedItem()).getValue());
            }


            if (haveguarantoroffice_radiobutton.isChecked()) {
                map.put(GuarantorId.guarHaveOfficeAddress, "true");
                map.put(GuarantorId.guarCompanyName, String.valueOf(companyname_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyAddress, String.valueOf(address_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyCity, String.valueOf(city_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyState, String.valueOf(state_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyMobile, String.valueOf(mobile_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyPhone, String.valueOf(phone_guarantoroffice_edittext.getText()));

                if (needverification_guarantoroffice_radiobutton.isChecked()) {
                    map.put(GuarantorId.guarCompanyNeedsVerification, "true");
                    map.put(GuarantorId.guarOfficeAssignedTo, ((SpinnerItem) assignedto_guarantoroffice_spinner.getSelectedItem()).getValue());
                }
            }
        }

        //Log.e(LOG_TAG,"getValues");
        //logId(map);
        return map;
    }

    public Map<String, String> parseCreateCase(String html) {
        Map<String, String> map = new HashMap<>();

        Document document = Jsoup.parse(html);

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for (Element input : elements) {
            String name = input.attr("name");
            String value = input.attr("value");

            /*if (name.equalsIgnoreCase(GuarantorId.haveGuarantor) || name.equalsIgnoreCase(ResidentialId.haveCompany) || name.equalsIgnoreCase(GuarantorId.guarHaveOfficeAddress)) {
                String type = input.attr("type");
                if (type.equalsIgnoreCase("checkbox")) {
                    map.put(name, value);
                }
                continue;
            }*/
            map.put(name, value);

        }

        Elements selects = form.getElementsByTag("select");
        for (Element select : selects) {
            String id = select.id();
            //Log.e(LOG_TAG,id);
            try {
                String value = select.getElementsByAttributeValue("selected", "selected").first().attr("value");
                //Log.e(LOG_TAG,value);
                map.put(id, value);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                map.put(id, "");
            }
        }

        map.remove("");
        map.remove(ACTION_CANCEL);
        map.remove(ACTION_REMARK);
        map.remove(ACTION_SUP_REMARK);

        //Log.e(LOG_TAG,"parse");
        //logId(map);
        return map;

    }

    @Override
    public void onPositiveResponse(final String html) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> createMap = parseCreateCase(html);
                Map<String, String> map = getValues();
                createMap.putAll(map);
                //logId(createMap);
                submitMultiPart(createMap);
                //progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onNegativeResponse(int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cacheData();
                progressDialog.cancel();
            }
        });
    }

    public void submitMultiPart(Map<String, String> map) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : map.keySet()) {
            requestBodyBuilder.addFormDataPart(key, map.get(key));
        }

        MultipartBody requestBody = requestBodyBuilder.build();

        String CASE_CREATE_URL = "/Users/Cases/Create";
        //String temp_url = "http://127.0.0.1/tempData" ;
        Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_CREATE_URL)
                //.url(temp_url)
                .post(requestBody)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + CASE_CREATE_URL + " submitting data");

        WebHelper.getInstance(this).requestCall(request, new WebHelper.CallBack() {
            @Override
            public void onPositiveResponse(String html) {
                Toast.makeText(CreateCaseDialog.this, "Data Submited", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onNegativeResponse(int code) {
                cacheData();
                Toast.makeText(CreateCaseDialog.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void logId(Map<String,String> map){
        for(String key:map.keySet()){
            Log.e(LOG_TAG,key+" : "+map.get(key));
        }
    }
}
