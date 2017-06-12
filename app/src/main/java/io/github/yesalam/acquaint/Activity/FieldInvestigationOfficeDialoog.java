package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 12-06-2017.
 */

public class FieldInvestigationOfficeDialoog extends Activity {

    //Address verification details
    @BindView(R.id.investigation_title)
    TextView investigaion_title_textview;
    @BindView(R.id.caseid_address_verification)
    TextView caseid_textview;
    @BindView(R.id.name_address_verification)
    TextView name_textview;
    @BindView(R.id.applicant_name_address_verification)
    TextView applicantname_textview;
    @BindView(R.id.coapplicant_address_verification)
    TextView coapplicant_textview;
    @BindView(R.id.company_name_row_address_verification)
    TableRow companyname_row;
    @BindView(R.id.company_name_address_verificaion)
    TextView companyname_textview;
    @BindView(R.id.address_address_verification)
    TextView address_textview;
    @BindView(R.id.application_refno_address_verification)
    TextView applicantiorefno_textview;
    @BindView(R.id.contact_no_address_verification)
    TextView contactno_textview;
    @BindView(R.id.field_executive_name)
    TextView fielexecutive_name;

    //Client title textview
    @BindView(R.id.client_address_type_verification_textview)
    TextView clienttitle_textview;

    //include_client_address_verification_1
    @BindView(R.id.address_confirmed_radiogroup)
    RadioGroup addressconfirmed_radiogroup;
    @BindView(R.id.address_confirmed_by_spinner)
    Spinner confirmedby_spinner;
    @BindView(R.id.office_address_row)
    TableRow officeaddress_row;
    @BindView(R.id.office_address_edittex)
    EditText officeaddress_edittext;

    //include_address_confirmed_office
    @BindView(R.id.address_confirmed_office_frame)
    FrameLayout addressconfirmed_office_frame;
    @BindView(R.id.name_of_employer_edittext)
    EditText nameofemployer_edittext;
    @BindView(R.id.person_met_office_edittext)
    EditText personmet_office_edittext;
    @BindView(R.id.person_met_designation_edittext)
    EditText personment_designation_edittext;
    @BindView(R.id.address_of_employer_edittext)
    EditText addressof_employer_edittext;
    @BindView(R.id.phone_offic_edittext)
    EditText phone_office_edittext;
    @BindView(R.id.extension_edittext)
    EditText extension_edittext;
    @BindView(R.id.residence_edittext)
    EditText residence_edittext;
    @BindView(R.id.mobile_edittext)
    EditText mobile_edittext;
    @BindView(R.id.company_board_seen_spinner)
    Spinner companyboardseen_spinner;
    @BindView(R.id.type_of_employer_spinner)
    Spinner typeofemployer_spinner;
    @BindView(R.id.nature_of_business_spinner)
    Spinner natureof_business_spinner;
    @BindView(R.id.line_of_business_edittext)
    EditText lineof_business_edittext;
    @BindView(R.id.year_of_establishment_edittext)
    EditText yearof_establishment_edittext;
    @BindView(R.id.level_of_business_spinner)
    Spinner levelof_business_spinner;
    @BindView(R.id.no_of_employee_seen_edittext)
    EditText noofemployee_see_edittext;
    @BindView(R.id.no_of_branches_edittext)
    EditText noofbranch_edittext;
    @BindView(R.id.office_abmience_spinner)
    Spinner office_abmience_spinner;
    @BindView(R.id.locality_office_spinner)
    Spinner locality_office_spinner;
    @BindView(R.id.area_edittext)
    EditText area_edittext;
    @BindView(R.id.nearest_landmark_edittext)
    EditText nearest_landmark_edittext;
    @BindView(R.id.ease_of_locating_spinner)
    Spinner easeof_locating_spinner;
    @BindView(R.id.years_of_current_employement_edittext)
    EditText yearsofcurrent_employement_edittext;
    @BindView(R.id.terms_of_employement_spinner)
    Spinner termsof_employement_spinner;
    @BindView(R.id.grade_spinner)
    Spinner grade_spinner;
    @BindView(R.id.grade_edittext)
    EditText grade_edittext;
    @BindView(R.id.current_salary_edittext)
    EditText current_salary_edittext;
    //include_address_confirmed_office_2
    @BindView(R.id.address_confirmed_office2_frame)
    FrameLayout addressconfirmed_office2_frame;
    @BindView(R.id.applicant_age_office2_edittext)
    EditText applicantage_office2_edittext;
    @BindView(R.id.nameofcompany_office2_edittext)
    EditText nameofcompany_office2_edittext;
    @BindView(R.id.establishmentyear_office2_edittext)
    EditText establishmentyear_office2_edittext;
    @BindView(R.id.designation_office2_edittext)
    EditText designation_office2_edittext;
    @BindView(R.id.phone_office2_edittext)
    EditText phone_office2_edittext;
    @BindView(R.id.extension_office2_edittext)
    EditText extension_office2_edittext;
    @BindView(R.id.typeofcompany_office2_spinner)
    Spinner typeofcompany_spinner;
    @BindView(R.id.natureofcompany_office2_spinner)
    Spinner natureofcompany_office2_spinner;
    @BindView(R.id.noofemployees_office2_edittext)
    EditText noofemployees_office2_edittext;
    @BindView(R.id.noofbranches_office2_edittext)
    EditText noofbranches_office2_edittext;
    @BindView(R.id.area_office2_edittext)
    EditText area_office2_edittext;
    @BindView(R.id.nearestlandmark_office2_edittext)
    EditText nearestlandmark_office2_edittext;

    //include_client_address_verification2
    @BindView(R.id.proof_attached_radiogroup)
    RadioGroup proofattached_radiogroup;
    @BindView(R.id.type_of_proof_spinner)
    Spinner typeofproof_spinner;
    @BindView(R.id.neighbour_1_textview)
    TextView neighbour1_textview;
    @BindView(R.id.neighbour_1_edittext)
    EditText neighbour1_edittext;
    @BindView(R.id.neighbour_2_textview)
    TextView neighbour2_textview;
    @BindView(R.id.neighbour_2_edittext)
    EditText neighbour2_edittext;
    @BindView(R.id.visit_date_edittext)
    EditText visitdate_edittext;
    @BindView(R.id.visit_time_edittext)
    EditText visittime_edittext;
    @BindView(R.id.field_executive_name_textview)
    TextView fieldexecutive_name_textview;
    @BindView(R.id.verifier_remark_edittext)
    EditText verifierremark_edittext;
    @BindView(R.id.verifier_autoremark_button)
    Button autoremark_verifier_button;
    @BindView(R.id.is_rcb_case_row)
    TableRow isrcb_row;
    @BindView(R.id.is_rcb_case_radiogroup)
    RadioGroup isrcb_radiogroup;
    @BindView(R.id.address_updations_edittext)
    EditText addressupdateion_edittext;
    @BindView(R.id.mobile_no_updations_edittext)
    EditText mobilenoupdation_edittext;
    @BindView(R.id.phone_no_updations_edittext)
    EditText phonenoupdation_edittext;
    @BindView(R.id.employement_detail_row)
    TableRow emloyementdetail_row;
    @BindView(R.id.employement_detail_updations_edittext)
    EditText employementupdation_edittext;
    @BindView(R.id.recommendation_spinner)
    Spinner recommendation_spinner;
    @BindView(R.id.uploaded_images_imageview)
    ImageView uploadedimages_imageview;
    @BindView(R.id.upload_file_button)
    Button uploadfile_button;
    @BindView(R.id.supervisor_remark_edittext)
    EditText supervisorremark_edittext;
    @BindView(R.id.autoremark_supervisor_button)
    Button autoremark_supervisor_button;

    @BindView(R.id.cancel_dailog_indie_investigation_button)
    Button cancel_button;
    @BindView(R.id.save_dailog_indie_investigation_button)
    Button save_button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_indie_field_office_investigation);

        ButterKnife.bind(this);


    }
}
