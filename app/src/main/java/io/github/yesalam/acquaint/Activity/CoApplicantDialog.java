package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.BaseWebActivity;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;
import io.github.yesalam.acquaint.Util.HaveClickListener;

import static io.github.yesalam.acquaint.Util.Util.getAssignedToType;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CoApplicantDialog extends Activity {



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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_show_coapplicant);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String addressid = intent.getStringExtra("addressid");


        if(addressid!=null) editMode = true;
        initForm();


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


    public void save(View view){}

    public void cancel(View view){
        finish();
    }
}
