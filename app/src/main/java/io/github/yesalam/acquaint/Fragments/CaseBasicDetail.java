package io.github.yesalam.acquaint.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;
import io.github.yesalam.acquaint.Util.HaveClickListener;

import static io.github.yesalam.acquaint.Util.Util.getAssignedToType;
import static io.github.yesalam.acquaint.Util.Util.getBranchHash;
import static io.github.yesalam.acquaint.Util.Util.getClientHash;
import static io.github.yesalam.acquaint.Util.Util.getClientType;
import static io.github.yesalam.acquaint.Util.Util.getLoanTypes;
import static io.github.yesalam.acquaint.Util.Util.getPickupByType;

/**
 * Created by yesalam on 08-06-2017.
 */

public class CaseBasicDetail extends Fragment {

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
    EditText pickup_date_edittext;
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
    @BindView(R.id.email_senton_textview)
    TextView email_senton_textview;
    @BindView(R.id.status_textview)
    TextView status_textview;


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
    @BindView(R.id.investigation_status_residential_detail_textview)
    TextView investigationstatus_residential;

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
    @BindView(R.id.status_office_detail_textview)
    TextView status_office_textview;

    //Radio have permanent address
    @BindView(R.id.have_permanent_address)
    CheckBox havepermanentaddress_radiobutton;

    //permanent address
    @BindView(R.id.applicant_permanent_address_frame)
    FrameLayout permanent_address_frame;
    @BindView(R.id.address_permanent_resident_edittext)
    EditText address_permanent_edittext;
    @BindView(R.id.city_permanent_resident_edittext)
    EditText city_permanent_edittext;
    @BindView(R.id.state_permanent_resident_edittext)
    EditText state_permanent_edittext;
    @BindView(R.id.mobile_permanent_resident_edittext)
    EditText mobile_permanent_editetxt;
    @BindView(R.id.phon_permanent_resident_edittext)
    EditText phone_permanent_edittext;
    @BindView(R.id.need_verification_permanent_resident_radiobutton)
    CheckBox needverificaiton_permanent_radiobutton;
    @BindView(R.id.assigned_to_permanent_resident_spinner)
    Spinner assigneto_permanent_spinner;
    @BindView(R.id.investigationstatus_row_permanent_resident)
    TableRow investigaionstatusrow_permanent_tablerow;
    @BindView(R.id.investigationstatus_permanent_resident_textview)
    TextView investigatonstatus_permanent_textview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_basic_detail,container,false);
        ButterKnife.bind(this,view);
        prepareForm();

        return view;
    }



    private void prepareForm(){



        final ArrayAdapter<SpinnerItem> contactadapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        contactadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactadapter.add(new SpinnerItem("Select Contact Person","0"));
        contact_person_spinner.setAdapter(contactadapter);



        final ArrayAdapter<SpinnerItem> branchadapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
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




        ArrayAdapter<SpinnerItem> clientadapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
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





        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //loantype_spinner
        adapter.addAll(getLoanTypes());
        loan_type_spinner.setAdapter(adapter);
        //loan_type_spinner.getSelectedItem();


        pickup_date_edittext.setOnClickListener(new DateClick(getContext()));


        ArrayAdapter<SpinnerItem> pickupbyadapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        pickupbyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickupbyadapter.addAll(getPickupByType());
        pickupby_spinner.setAdapter(pickupbyadapter);

        dob_edittext.setOnClickListener(new DateClick(getContext()));


        ArrayAdapter<SpinnerItem> assignedtoadapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        assignedtoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedtoadapter.addAll(getAssignedToType());
        assignedto_residential_spinner.setAdapter(assignedtoadapter);


        havecompany_radiobutton.setChecked(false);
        applicant_office_frame.setVisibility(View.GONE);
        HaveClickListener haveClickListener = new HaveClickListener(applicant_office_frame);
        havecompany_radiobutton.setOnClickListener(haveClickListener);

        ArrayAdapter<SpinnerItem> assignedto_officeadapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        assignedto_officeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_officeadapter.addAll(getAssignedToType());
        assignedto_office_spinner.setAdapter(assignedto_officeadapter);

        havepermanentaddress_radiobutton.setChecked(false);
        permanent_address_frame.setVisibility(View.GONE);
        HaveClickListener permanentHave = new HaveClickListener(permanent_address_frame);
        havepermanentaddress_radiobutton.setOnClickListener(permanentHave);

        ArrayAdapter<SpinnerItem> assignedto_permanet = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        assignedto_permanet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_permanet.addAll(getAssignedToType());
        assigneto_permanent_spinner.setAdapter(assignedto_permanet);


    }
}
