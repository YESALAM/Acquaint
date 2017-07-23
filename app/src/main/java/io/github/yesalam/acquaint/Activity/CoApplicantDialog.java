package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.github.yesalam.acquaint.Pojo.CoApplicantDetailPojo;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.CaseBasicId;
import io.github.yesalam.acquaint.Util.Id.GuarantorId;
import io.github.yesalam.acquaint.Util.Id.OfficeId;
import io.github.yesalam.acquaint.Util.Id.PermanentId;
import io.github.yesalam.acquaint.Util.Id.ResidentialId;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Listener.HaveClickListener;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getAssignedToType;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CoApplicantDialog extends Activity implements WebHelper.CallBack, SwipeRefreshLayout.OnRefreshListener {


    //applicant_resident_detail
    @BindView(R.id.coapplicant_residential_detail_frame)
    FrameLayout residential_detail_frame;
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
    @BindView(R.id.need_verification_coapplicant_resident_radiobutton)
    CheckBox needVerification_resident;
    @BindView(R.id.assigned_to_residential_detail_spinner)
    Spinner assignedto_residential_spinner;
    @BindView(R.id.investigationstatus_row_coapplicant_resident)
    TableRow investiagationstatusrow_residential_tablerow;
    @BindView(R.id.investigationstatus_coapplicant_resident_textview)
    TextView investigationstatus_residential;

    @BindView(R.id.have_company_address_dailog_radiobutton)
    CheckBox havecompany_address_radiobutton;

    //Applicant_office_detail
    @BindView(R.id.coapplicant_office_details_frame)
    FrameLayout coapplicant_office_frame;
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
    @BindView(R.id.status_office_detail_textview)
    TextView status_office_textview;

    @BindView(R.id.cancel_dailog)
    Button cancel_dailog_button;
    @BindView(R.id.save_dailog)
    Button save_dailog_button;

    boolean editMode = false;
    String LOG_TAG = "CoApplicantDialog";
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;

    String caseid;
    String addressid;
    CoApplicantDetailPojo pojo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_show_coapplicant);
        ButterKnife.bind(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Submitting Data");

        Intent intent = getIntent();
        caseid = intent.getStringExtra("caseid");
        addressid = intent.getStringExtra("addressid");

        Log.e(LOG_TAG, caseid + "  " + addressid);
        if (addressid != null) {
            editMode = true;
            loadData(caseid, addressid);
        }
        initForm();
    }


    private void initForm() {

        dob_edittext.setOnClickListener(new DateClick(this));

        ArrayAdapter<SpinnerItem> assignedtoadapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        assignedtoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedtoadapter.addAll(getAssignedToType(this));
        assignedto_residential_spinner.setAdapter(assignedtoadapter);


        havecompany_address_radiobutton.setChecked(false);
        coapplicant_office_frame.setVisibility(View.GONE);
        HaveClickListener haveClickListener = new HaveClickListener(coapplicant_office_frame);
        havecompany_address_radiobutton.setOnClickListener(haveClickListener);

        if (!editMode) {
            investiagationstatusrow_residential_tablerow.setVisibility(View.GONE);
            statusrow_office_tablerow.setVisibility(View.GONE);
        }

        ArrayAdapter<SpinnerItem> office_adapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item);
        office_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office_adapter.addAll(getAssignedToType(this));
        assignedto_office_spinner.setAdapter(office_adapter);

    }

    private void loadData(String caseid, String addressid) {
        refreshLayout.setRefreshing(true);
        String GET_COAPPLICANT_DETAIL_URL = "/Users/Cases/GetCoApplicantDetails?case_id=" + caseid + "&address_id=" + addressid;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + GET_COAPPLICANT_DETAIL_URL)
                .build();
        Log.e(LOG_TAG, GET_COAPPLICANT_DETAIL_URL);
        WebHelper.getInstance(this).requestCall(request, this);
    }

    private CoApplicantDetailPojo clearNull(CoApplicantDetailPojo pojo) {
            /*Field[] fields = CoApplicantDetailPojo.class.getDeclaredFields();

            for(Field field:fields){
                try {
                    Object object = field.get(pojo);
                    if(object == null){
                        object = new String();
                    }else if(object.toString().equalsIgnoreCase("null")) {
                        object = new String() ;
                    }
                    field.set(pojo,object);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }*/
        // TODO: 17-06-2017 clear null here for CoApplicantPojo fiels


        return pojo;
    }

    public CoApplicantDetailPojo parseData(String json) {
        CoApplicantDetailPojo pojo = new CoApplicantDetailPojo();
        try {
            JSONObject object = new JSONObject(json);
            pojo.name = object.getString("Name");
            pojo.dOB = object.getString("DOB");
            pojo.pAN = object.getString("PAN");
            pojo.gender = object.getString("Gender");
            pojo.address = object.getString("Address");
            pojo.city = object.getString("City");
            pojo.state = object.getString("State");
            pojo.pin = object.getString("Pin");
            pojo.eMail = object.get("EMail");
            pojo.mobile = object.get("Mobile");
            pojo.phone = object.getString("Phone");
            pojo.needsVerification = object.getBoolean("NeedsVerification");
            pojo.assignedTo = object.get("AssignedTo");
            pojo.residenceStatus = object.getString("ResidenceStatus");

            pojo.addressId = object.getInt("AddressId");

            pojo.companyName = object.get("CompanyName");
            pojo.companyAddress = object.get("CompanyAddress");
            pojo.companyCity = object.get("CompanyCity");
            pojo.companyMobile = object.get("CompanyMobile");
            pojo.companyState = object.get("CompanyState");
            pojo.companyPhone = object.get("CompanyPhone");
            pojo.companyNeedsVerification = object.get("CompanyNeedsVerification");
            pojo.companyAssignedTo = object.get("CompanyAssignedTo");
            pojo.officeStatus = object.getString("OfficeStatus");


            pojo.companyAddressId = object.getInt("CompanyAddressId");
            pojo.personId = object.getInt("PersonId");
            pojo.teleOfficeStatus = object.getString("TeleOfficeStatus");
            pojo.teleResiStatus = object.getString("TeleResiStatus");
            pojo.updatedByName = object.getString("UpdatedByName");
            pojo.updatedLast = object.getString("UpdatedLast");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pojo;
    }

    private void update(CoApplicantDetailPojo pojo) {
        refreshLayout.setRefreshing(false);

        String name = pojo.name ;
        if(name.equalsIgnoreCase("null")) name = "";
        name_resident_edittext.setText(name);

        String dOB = pojo.dOB ;
        if(dOB.equalsIgnoreCase("null")) dOB = "";
        dob_edittext.setText(dOB);

        String pAN = pojo.pAN.toString() ;
        if(pAN.equalsIgnoreCase("null")) pAN = "";
        pan_edittext.setText(pAN);

        String genders = pojo.gender ;
        if(genders.equalsIgnoreCase("null")) genders = "";
        else{
            int gender = genders.equalsIgnoreCase("M") ? R.id.radio_button_male_residential_detail : R.id.radio_button_female_residential_detail;
            gender_radiogroup.check(gender);
        }

        String address = pojo.address   ;
        if(address.equalsIgnoreCase("null")) address = "";
        address_residential_edittext.setText(address);

        String city = pojo.city ;
        if(city.equalsIgnoreCase("null")) city = "";
        city_residential_edittext.setText(city);

        String state = pojo.state ;
        if(state.equalsIgnoreCase("null")) state = "";
        state_residential_edittxt.setText(state);

        String pin = pojo.pin.toString() ;
        if(pin.equalsIgnoreCase("null")) pin = "";
        pin_residential_edittext.setText(pin);

        String eMail = pojo.eMail.toString() ;
        if(eMail.equalsIgnoreCase("null")) eMail = "";
        email_residential_edittext.setText(eMail);

        String mobile = pojo.mobile.toString() ;
        if(mobile.equalsIgnoreCase("null")) mobile = "";
        mobile_residential_edittext.setText(mobile);

        String phone = pojo.phone.toString() ;
        if(phone.equalsIgnoreCase("null")) phone = "";
        phone_residential_edittext.setText(phone);

        String needsVerification = pojo.needsVerification.toString() ;
        if(needsVerification.equalsIgnoreCase("null")) needsVerification = "";
        else needVerification_resident.setChecked(needsVerification.equalsIgnoreCase("true"));

        /*String assignedto =  map.get(ResidentialId.assignedTo);
        int positionassignedto = ((ArrayAdapter)assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_residential_spinner.setSelection(positionassignedto);
        assignedto_residential_spinner.setText(pojo.name);*/

        String assignedTo = pojo.assignedTo.toString() ;
        if(assignedTo.equalsIgnoreCase("null")) assignedTo = "";
        int positionassignedto = ((ArrayAdapter) assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedTo));
        assignedto_residential_spinner.setSelection(positionassignedto);

        //sta.setText(pojo.name);
        String residenceStatus = pojo.residenceStatus ;
        if(residenceStatus.equalsIgnoreCase("null")) residenceStatus = "";
        investigationstatus_residential.setText(residenceStatus);



        boolean haveCompany = !pojo.companyAddressId.toString().equalsIgnoreCase("0");


        havecompany_address_radiobutton.setChecked(haveCompany);
        if (haveCompany) {
            coapplicant_office_frame.setVisibility(View.VISIBLE);
        }

        String companyName = pojo.companyName.toString() ;
        if(companyName.equalsIgnoreCase("null")) companyName = "";
        companyname_office_edittext.setText(companyName);

        String companyAddress = pojo.companyAddress.toString() ;
        if(companyAddress.equalsIgnoreCase("null")) companyAddress = "";
        address_office_edittext.setText(pojo.companyAddress.toString());

        String companyCity = pojo.companyCity.toString() ;
        if(companyCity.equalsIgnoreCase("null")) companyCity = "";
        city_office_edittext.setText(pojo.companyCity.toString());

        String companyState = pojo.companyState.toString() ;
        if(companyState.equalsIgnoreCase("null")) companyState = "";
        state_office_edittext.setText(pojo.companyState.toString());

        String companyMobile = pojo.companyMobile.toString() ;
        if(companyMobile.equalsIgnoreCase("null")) companyMobile = "";
        mobile_office_edittext.setText(pojo.companyMobile.toString());

        String companyPhone = pojo.companyPhone.toString() ;
        if(companyPhone.equalsIgnoreCase("null")) companyPhone = "";
        phone_office_edittext.setText(pojo.companyPhone.toString());

        String companyNeedsVerification = pojo.companyNeedsVerification.toString() ;
        if(companyNeedsVerification.equalsIgnoreCase("null")) companyNeedsVerification = "";
        else needverification_office_radiobutton.setChecked(companyNeedsVerification.equalsIgnoreCase("true"));

        //assignedto_office_spinner.setText(pojo.name);
        String companyAssignedTo = pojo.companyAssignedTo.toString() ;
        if(companyAssignedTo.equalsIgnoreCase("null")) companyAssignedTo = "";
        int positionassignedtoOffice = ((ArrayAdapter) assignedto_office_spinner.getAdapter()).getPosition(new SpinnerItem(companyAssignedTo));
        assignedto_office_spinner.setSelection(positionassignedtoOffice);
        Log.e(LOG_TAG, companyAssignedTo + "->" + positionassignedtoOffice);

        String officeStatus = pojo.officeStatus ;
        if(officeStatus.equalsIgnoreCase("null")) officeStatus = "";
        status_office_textview.setText(officeStatus);


    }

    public void save(View view) {
        if (validate()) areYouSure();
    }

    private boolean validate() {


        String name = String.valueOf(name_resident_edittext.getText());
        if (name.equalsIgnoreCase("")) {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String address = String.valueOf(address_residential_edittext.getText());
        if (address.equalsIgnoreCase("")) {
            Toast.makeText(this, "Address is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String city = String.valueOf(city_residential_edittext.getText());
        if (city.equalsIgnoreCase("")) {
            Toast.makeText(this, "City is empty", Toast.LENGTH_LONG).show();
            return false;
        }


        String state = String.valueOf(state_residential_edittxt.getText());
        if (state.equalsIgnoreCase("")) {
            Toast.makeText(this, "State is empty", Toast.LENGTH_LONG).show();
            return false;
        }


        if (needVerification_resident.isChecked()) {
            try {
                int assignedTooffice = Integer.parseInt(((SpinnerItem) assignedto_residential_spinner.getSelectedItem()).getValue());
                if (assignedTooffice == 0) {
                    Toast.makeText(this, "AssignedTo is Missing", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (NumberFormatException nfe) {
            }
        }


        if (havecompany_address_radiobutton.isChecked()) {
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


        return true;
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
                            oktoSubmit();
                        } else {
                            //cacheData();
                            //finish();
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

    private void oktoSubmit() {
        //map.remove("img_src");
        Map<String,String> map = Util.coMap ;
        if (map == null) {
            Toast.makeText(this, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pojo!=null && editMode){
            map.put("AddressId", String.valueOf(pojo.addressId));
            map.put("CompanyAddressId", String.valueOf(pojo.companyAddressId));
            map.put("PersonId", String.valueOf(pojo.personId));
        }
        map.remove("action:Cancel");
        map.remove("");
        Map<String, String> valuesMap = getValues();
        map.putAll(valuesMap);
        //logId(map);
        submitMultiPart(map);

    }


    public void submitMultiPart(Map<String, String> map) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : map.keySet()) {
            requestBodyBuilder.addFormDataPart(key, map.get(key));
        }

        MultipartBody requestBody = requestBodyBuilder.build();

        String CASE_EDIT = "/Users/Cases/Edit/" + caseid;

        Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_EDIT)
                .post(requestBody)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + CASE_EDIT + " submitting data");

        WebHelper.getInstance(this).requestCall(request, new WebHelper.CallBack() {
            @Override
            public void onPositiveResponse(String html) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CoApplicantDialog.this, "Data Submited", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onNegativeResponse(int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //cacheData();
                        Toast.makeText(CoApplicantDialog.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //finish();
                    }
                });
            }
        });

    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onPositiveResponse(String htmldoc) {
        Log.e(LOG_TAG, "Got response");
        pojo = parseData(htmldoc);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                update(clearNull(pojo));
            }
        });
    }

    @Override
    public void onNegativeResponse(int code) {
        switch (code) {
            case NO_CONNECTION:
                Snackbar.make(residential_detail_frame, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                        .show(); // Don’t forget to show!
                break;
            case 500:
                Snackbar.make(residential_detail_frame, "Internal Server Error", Snackbar.LENGTH_LONG)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                        .show(); // Don’t forget to show!

                break;

        }
    }

    private Map<String, String> getValues() {
        Map<String, String> map = new HashMap<>();

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

        if (needVerification_resident.isChecked()) {
            map.put(ResidentialId.needsVerification, "true");
            map.put(ResidentialId.assignedTo, ((SpinnerItem) assignedto_residential_spinner.getSelectedItem()).getValue());
        }

      /*  if (havecompany_address_radiobutton.isChecked()) {*/
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

       /* }*/

        return map;
    }


    @Override
    public void onRefresh() {
        Log.e(LOG_TAG, "called onRefresh");
        loadData(caseid, addressid);
        refreshLayout.setRefreshing(false);
    }
}
