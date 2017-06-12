package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 10-06-2017.
 */

public class TeleVerificationDialog extends Activity {

    @BindView(R.id.televerification_title_textview)
    TextView title_textview;
    @BindView(R.id.caseid_tele_verification)
    TextView caseid_textview;
    @BindView(R.id.application_refno_tele_verification)
    TextView applicatiorefno_textview;
    @BindView(R.id.name_tele_verification)
    TextView name_textview;
    @BindView(R.id.applicant_name_tele_verification)
    TextView applicantname_textview;
    @BindView(R.id.coapplicant_tele_verification)
    TextView coapplicant_textview;

    //include_residence_televerification
    @BindView(R.id.residence_address_residence_tele)
    TextView residenceaddress_textview;
    @BindView(R.id.mobile_residence_tele)
    TextView mobileresidence_textview;
    @BindView(R.id.person_spoken_to_residence)
    EditText personspoken_residence_edittext;
    @BindView(R.id.relation_residence_spinner)
    Spinner relation_residence_spinner;
    @BindView(R.id.first_calling_date_residence)
    EditText firstcallingdate_residence_edittext;
    @BindView(R.id.first_time_of_calling_residence)
    EditText firstcallingtime_residence_edittext;
    @BindView(R.id.second_calling_date_residence)
    EditText secondcallingdate_residence_edittext;
    @BindView(R.id.second_time_of_calling_residence)
    EditText secondcallingtime_residence_edittext;
    @BindView(R.id.third_calling_date_residence)
    EditText thirdcallingdate_residence_edittext;
    @BindView(R.id.third_time_of_calling_residence)
    EditText thiredcallingtime_residence_edittext;
    @BindView(R.id.remark_residence)
    EditText remark_residence_edittext;
    @BindView(R.id.status_residence_spinner)
    Spinner status_residence_spinner;

    //include_office_tele_verification
    @BindView(R.id.companyname_office_tele)
    TextView companyname_textview;
    @BindView(R.id.address_office_tele)
    TextView companyaddress_textview;
    @BindView(R.id.mobile_office_tele)
    TextView mobile_office_textview;
    @BindView(R.id.person_spoken_to_office)
    EditText personspoken_office_edittext;
    @BindView(R.id.designation_office)
    EditText designation_office_edittext;
    @BindView(R.id.relation_office_spinner)
    Spinner relation_office_spinner;
    @BindView(R.id.first_calling_date_office)
    EditText firstcallingdate_office_edittext;
    @BindView(R.id.first_time_of_calling_office)
    EditText firstcallingtime_office_edittext;
    @BindView(R.id.second_calling_date_office)
    EditText secondcallingdate_office_edittext;
    @BindView(R.id.second_time_of_calling_office)
    EditText secondcallingtime_office_edittext;
    @BindView(R.id.third_calling_date_office)
    EditText thirdcallingdate_office_edittext;
    @BindView(R.id.third_time_of_calling_office)
    EditText thiredcallingtime_office_edittext;
    @BindView(R.id.remark_office)
    EditText remark_office_edittext;
    @BindView(R.id.status_office_spinner)
    Spinner status_office_spinner;


    @BindView(R.id.cancel_dailog_tele_button)
    Button calcel_button;
    @BindView(R.id.save_dailog_tele_button)
    Button save_button;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_televerification);

        ButterKnife.bind(this);
    }
}
