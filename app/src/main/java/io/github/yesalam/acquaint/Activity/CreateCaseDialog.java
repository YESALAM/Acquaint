package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CreateCaseDialog extends Activity {

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
    @BindView(R.id.date_edittext)
    EditText date_edittext;
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
    RadioButton havecompany_radiobutton;

    //Applicant_office_detail
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
    RadioButton needverification_office_radiobutton;
    @BindView(R.id.assigned_to_office_detail_spinner)
    Spinner assignedto_office_spinner;
    @BindView(R.id.status_row_office_detail)
    TableRow statusrow_office_tablerow;
    @BindView(R.id.status_office_detail_textview)
    TextView status_office_textview;

    @BindView(R.id.have_guarantor_residential_radiobutton)
    RadioButton have_guarantor_radiobutton;
    @BindView(R.id.guarantor_residential_detail_frame)
    FrameLayout guarantor_residential_frame;
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
    RadioButton needverificaton_guarantor_radiobutton;
    @BindView(R.id.assigned_to_guarantor_residential_detail_spinner)
    Spinner assignedto_guarantor_spinner;

    //RAdio button have office address
    @BindView(R.id.have_guarantor_office_address)
    RadioButton haveguarantoroffice_radiobutton;

    //Applicant_office_detail
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
    RadioButton needverification_guarantoroffice_radiobutton;
    @BindView(R.id.assigned_to_guarantor_office_details_spinner)
    Spinner assignedto_guarantoroffice_spinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_case);
        ButterKnife.bind(this);
    }
}
