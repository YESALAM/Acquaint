package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;
import io.github.yesalam.acquaint.Util.HaveClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.getAssignedToType;
import static io.github.yesalam.acquaint.Util.Util.getBinaryType;
import static io.github.yesalam.acquaint.Util.Util.getBranchHash;
import static io.github.yesalam.acquaint.Util.Util.getClientHash;
import static io.github.yesalam.acquaint.Util.Util.getClientType;
import static io.github.yesalam.acquaint.Util.Util.getLoanTypes;
import static io.github.yesalam.acquaint.Util.Util.getPickupByType;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CreateCaseDialog extends BaseWebActivity implements Callback {

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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_case);
        ButterKnife.bind(this);

        prepareForm();
    }


    private void prepareForm(){



        email_row.setVisibility(View.GONE);
        staus_row.setVisibility(View.GONE);
        investiagatonstatusrow_residential_tablerow.setVisibility(View.GONE);

        final ArrayAdapter<SpinnerItem> contactadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        contactadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactadapter.add(new SpinnerItem("Select Contact Person","0"));
        contact_person_spinner.setAdapter(contactadapter);



        final ArrayAdapter<SpinnerItem> branchadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        branchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchadapter.add(new SpinnerItem("Select Branch","0"));
        branch_spinner.setAdapter(branchadapter);
        branch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
                int val = Integer.parseInt(item.getValue());
                if(val==0)return;
                String clientstring = getBranchHash().get(val);
                try {
                    JSONArray array = new JSONArray(clientstring);
                    ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String value = object.getString("id");
                        String name = object.getString("name");
                        list.add(new SpinnerItem(name,value));
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




        ArrayAdapter<SpinnerItem> clientadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        clientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientadapter.addAll(getClientType());
        client_spinner.setAdapter(clientadapter);
        client_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
                int val = Integer.parseInt(item.getValue());
                if(val==0)return;
                String branchstring = getClientHash().get(val);
                try {
                    JSONArray array = new JSONArray(branchstring);
                    ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String value = object.getString("id");
                        String name = object.getString("name");
                        list.add(new SpinnerItem(name,value));
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





        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //loantype_spinner
        adapter.addAll(getLoanTypes());
        loan_type_spinner.setAdapter(adapter);
        //loan_type_spinner.getSelectedItem();


        pickupdate_edittext.setOnClickListener(new DateClick(this));


        ArrayAdapter<SpinnerItem> pickupbyadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        pickupbyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickupbyadapter.addAll(getPickupByType());
        pickupby_spinner.setAdapter(pickupbyadapter);

        dob_edittext.setOnClickListener(new DateClick(this));


        ArrayAdapter<SpinnerItem> assignedtoadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        assignedtoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedtoadapter.addAll(getAssignedToType());
        assignedto_residential_spinner.setAdapter(assignedtoadapter);


        havecompany_radiobutton.setChecked(false);
        applicant_office_frame.setVisibility(View.GONE);
        HaveClickListener haveClickListener = new HaveClickListener(applicant_office_frame);
        havecompany_radiobutton.setOnClickListener(haveClickListener);

        ArrayAdapter<SpinnerItem> assignedto_officeadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        assignedto_officeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_officeadapter.addAll(getAssignedToType());
        assignedto_office_spinner.setAdapter(assignedto_officeadapter);

        guarantor_residential_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantorListener = new HaveClickListener(guarantor_residential_frame);
        have_guarantor_radiobutton.setOnClickListener(haveguarantorListener);

        dob_guarantor_edittext.setOnClickListener(new DateClick(this));

        ArrayAdapter<SpinnerItem> assignedto_gurantor = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        assignedto_gurantor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantor.addAll(getAssignedToType());
        assignedto_guarantor_spinner.setAdapter(assignedto_gurantor);

        guarantor_office_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantoroffice = new HaveClickListener(guarantor_office_frame);
        haveguarantoroffice_radiobutton.setOnClickListener(haveguarantoroffice);


        ArrayAdapter<SpinnerItem> assignedto_gurantoroffice = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        assignedto_gurantoroffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantoroffice.addAll(getAssignedToType());
        assignedto_guarantoroffice_spinner.setAdapter(assignedto_gurantoroffice);


    }


    public void save(View view) {
    }

    public void cancel(View view) {
        finish();


    }

    public void updateClientDetail(String clientid){
        Request request = new Request.Builder()
                .url(ACQUAINT_URL+"/GetBranches?client_id="+clientid)
                .build();

        okHttpClient.newCall(request).enqueue(this);

    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call,final Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        try {
            //call.request().url().
            JSONArray array = new JSONArray(response.body().string());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
