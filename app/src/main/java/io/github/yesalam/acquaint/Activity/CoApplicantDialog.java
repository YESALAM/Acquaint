package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.Pojo.CoApplicantDetailPojo;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;
import io.github.yesalam.acquaint.Util.HaveClickListener;
import io.github.yesalam.acquaint.Util.ResidentialId;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Request;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.getAssignedToType;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CoApplicantDialog extends Activity implements WebHelper.CallBack {



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
    String LOG_TAG = "CoApplicantDialog" ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_show_coapplicant);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String caseid = intent.getStringExtra("caseid");
        String addressid = intent.getStringExtra("addressid");
        Log.e(LOG_TAG,caseid+"  "+addressid);
        if(addressid!=null) editMode = true;
        initForm();

        loadData(caseid,addressid);

    }



    private void initForm(){

        dob_edittext.setOnClickListener(new DateClick(this));

        ArrayAdapter<SpinnerItem> assignedtoadapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        assignedtoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedtoadapter.addAll(getAssignedToType());
        assignedto_residential_spinner.setAdapter(assignedtoadapter);


        havecompany_address_radiobutton.setChecked(false);
        coapplicant_office_frame.setVisibility(View.GONE);
        HaveClickListener haveClickListener = new HaveClickListener(coapplicant_office_frame);
        havecompany_address_radiobutton.setOnClickListener(haveClickListener);

        if(!editMode){
            investiagationstatusrow_residential_tablerow.setVisibility(View.GONE);
            statusrow_office_tablerow.setVisibility(View.GONE);
        }

        ArrayAdapter<SpinnerItem> office_adapter= new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        office_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office_adapter.addAll(getAssignedToType());
        assignedto_office_spinner.setAdapter(office_adapter);

    }

    private void loadData(String caseid,String addressid){
        String GET_COAPPLICANT_DETAIL_URL = "/Users/Cases/GetCoApplicantDetails?case_id="+caseid+"&address_id="+addressid;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL+GET_COAPPLICANT_DETAIL_URL)
                .build();
        WebHelper.getInstance(this).requestCall(request,this);
    }

    private CoApplicantDetailPojo clearNull(CoApplicantDetailPojo pojo){
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






        return pojo ;
    }

    public CoApplicantDetailPojo parseData(String json){
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

    private void update(CoApplicantDetailPojo pojo){
        name_resident_edittext.setText(pojo.name);
        dob_edittext.setText(pojo.dOB);
        pan_edittext.setText(pojo.pAN.toString());

        int gender = pojo.gender.equalsIgnoreCase("M")?R.id.radio_button_male_residential_detail:R.id.radio_button_female_residential_detail;
        gender_radiogroup.check(gender);

        address_residential_edittext.setText(pojo.address);
        city_residential_edittext.setText(pojo.city);
        state_residential_edittxt.setText(pojo.state);
        pin_residential_edittext.setText(pojo.pin.toString());
        email_residential_edittext.setText(pojo.eMail.toString());
        mobile_residential_edittext.setText(pojo.mobile.toString());
        phone_residential_edittext.setText(pojo.phone.toString());

        needverification_office_radiobutton.setChecked(pojo.needsVerification.toString().equalsIgnoreCase("true"));

        /*String assignedto =  map.get(ResidentialId.assignedTo);
        int positionassignedto = ((ArrayAdapter)assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_residential_spinner.setSelection(positionassignedto);
        assignedto_residential_spinner.setText(pojo.name);*/

        //sta.setText(pojo.name);
        investigationstatus_residential.setText(pojo.residenceStatus);

        havecompany_address_radiobutton.setChecked(!pojo.companyAddressId.toString().equalsIgnoreCase("0"));

        companyname_office_edittext.setText(pojo.companyName.toString());

        address_office_edittext.setText(pojo.companyAddress.toString());
        city_office_edittext.setText(pojo.companyCity.toString());
        state_office_edittext.setText(pojo.companyState.toString());
        mobile_office_edittext.setText(pojo.companyMobile.toString());
        phone_office_edittext.setText(pojo.companyPhone.toString());

        needverification_office_radiobutton.setChecked(pojo.companyNeedsVerification.toString().equalsIgnoreCase("true"));

        //assignedto_office_spinner.setText(pojo.name);

        status_office_textview.setText(pojo.officeStatus);
    }

    public void save(View view){}

    public void cancel(View view){
        finish();
    }

    @Override
    public void onPositiveResponse(String htmldoc) {
        final CoApplicantDetailPojo pojo = parseData(htmldoc);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                update(clearNull(pojo));
            }
        });
    }
}
