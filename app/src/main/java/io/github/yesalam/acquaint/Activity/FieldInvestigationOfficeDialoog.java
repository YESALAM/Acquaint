package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;

import static io.github.yesalam.acquaint.Util.Util.getAccomodationType;
import static io.github.yesalam.acquaint.Util.Util.getAddressConfirmedByType;
import static io.github.yesalam.acquaint.Util.Util.getBusinessLevelType;
import static io.github.yesalam.acquaint.Util.Util.getBusinessType;
import static io.github.yesalam.acquaint.Util.Util.getEaseofLocatingType;
import static io.github.yesalam.acquaint.Util.Util.getEmployementTermType;
import static io.github.yesalam.acquaint.Util.Util.getEmployerType;
import static io.github.yesalam.acquaint.Util.Util.getFamilyMemberType;
import static io.github.yesalam.acquaint.Util.Util.getGradeType;
import static io.github.yesalam.acquaint.Util.Util.getLivingStandardType;
import static io.github.yesalam.acquaint.Util.Util.getLocalityType;
import static io.github.yesalam.acquaint.Util.Util.getMonthType;
import static io.github.yesalam.acquaint.Util.Util.getOfficeAmbienceType;
import static io.github.yesalam.acquaint.Util.Util.getRecommendationType;
import static io.github.yesalam.acquaint.Util.Util.getRelationType;
import static io.github.yesalam.acquaint.Util.Util.getResidenceProofType;
import static io.github.yesalam.acquaint.Util.Util.getYearType;

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
    LinearLayout addressconfirmed_office_frame;
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
    @BindView(R.id.company_board_seen_radiogroup)
    RadioGroup company_boardseen_radiogroup;
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

    //include_address_not_confirmed
    @BindView(R.id.address_not_confirmed_frame)
    LinearLayout address_notconfirmed_frame;
    @BindView(R.id.reason_not_confirmed_radiogroup)
    RadioGroup reason_radiogruop;
    @BindView(R.id.person_know_applicant_row)
    TableRow person_know_applicant_row;
    @BindView(R.id.person_know_radiogroup)
    RadioGroup person_know;
    @BindView(R.id.address_belongs_to_row)
    TableRow address_belongto_row;
    @BindView(R.id.address_belongs_to_edittext)
    EditText address_belongto_edittext;
    @BindView(R.id.reason_row)
    TableRow reason_row;
    @BindView(R.id.reason_edittext)
    EditText reason_edittext;
    @BindView(R.id.locality_row)
    TableRow locality_row;
    @BindView(R.id.locality_spinner_not_confirmed)
    Spinner locality_spinner_not;
    @BindView(R.id.result_of_calling_row)
    TableRow resultOfCalling_row;
    @BindView(R.id.result_of_calling_edittext)
    EditText resultOfCalling_edittex;
    @BindView(R.id.mismatch_row)
    TableLayout mismatch_row;
    @BindView(R.id.untraceable_row)
    TableLayout untraceable_row;


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

        initForm();
    }

    private void initForm(){
        addressconfirmed_office_frame.setVisibility(View.GONE);
        address_notconfirmed_frame.setVisibility(View.GONE);
        addressconfirmed_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.yes_address_confirmed_radiobutton){
                    addressconfirmed_office_frame.setVisibility(View.VISIBLE);
                    address_notconfirmed_frame.setVisibility(View.GONE);
                }else {
                    addressconfirmed_office_frame.setVisibility(View.GONE);
                    address_notconfirmed_frame.setVisibility(View.VISIBLE);
                }
            }
        });

        ArrayAdapter<SpinnerItem> confirmedByAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        confirmedByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confirmedByAdapter.addAll(getAddressConfirmedByType());
        confirmedby_spinner.setAdapter(confirmedByAdapter);

        ArrayAdapter<SpinnerItem> typeOfProofAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        typeOfProofAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfProofAdapter.addAll(getResidenceProofType());
        typeofproof_spinner.setAdapter(typeOfProofAdapter);

        ArrayAdapter<SpinnerItem> recommendation_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        recommendation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recommendation_adapter.addAll(getRecommendationType());
        recommendation_spinner.setAdapter(recommendation_adapter);

        visitdate_edittext.setOnClickListener(new DateClick(this));

        //address confirmed initialization


        ArrayAdapter<SpinnerItem> employerType_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        employerType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employerType_adapter.addAll(getEmployerType());
        typeofemployer_spinner.setAdapter(employerType_adapter);
        typeofcompany_spinner.setAdapter(employerType_adapter);

        ArrayAdapter<SpinnerItem> businessNature_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        businessNature_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        businessNature_adapter.addAll(getBusinessType());
        natureof_business_spinner.setAdapter(businessNature_adapter);
        natureofcompany_office2_spinner.setAdapter(businessNature_adapter);

        ArrayAdapter<SpinnerItem> businessLevel_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        businessLevel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        businessLevel_adapter.addAll(getBusinessLevelType());
        levelof_business_spinner.setAdapter(businessLevel_adapter);

        ArrayAdapter<SpinnerItem> officeAmbiaence_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        officeAmbiaence_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        officeAmbiaence_adapter.addAll(getOfficeAmbienceType());
        office_abmience_spinner.setAdapter(officeAmbiaence_adapter);


        ArrayAdapter<SpinnerItem> locationEase_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        locationEase_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationEase_adapter.addAll(getEaseofLocatingType());
        easeof_locating_spinner.setAdapter(locationEase_adapter);

        ArrayAdapter<SpinnerItem> locality_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        locality_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locality_adapter.addAll(getLocalityType());
        locality_office_spinner.setAdapter(locality_adapter);
        locality_spinner_not.setAdapter(locality_adapter);

        ArrayAdapter<SpinnerItem> employementTerm_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        employementTerm_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employementTerm_adapter.addAll(getEmployementTermType());
        termsof_employement_spinner.setAdapter(employementTerm_adapter);

        ArrayAdapter<SpinnerItem> grade_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        grade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade_adapter.addAll(getGradeType());
        grade_spinner.setAdapter(grade_adapter);
        grade_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
                if(item.getValue().equalsIgnoreCase("O")){
                    grade_edittext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        grade_edittext.setVisibility(View.GONE);





        mismatch_row.setVisibility(View.GONE);
        reason_radiogruop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.untraceable_reason_not_confirmed_radiobutton){
                    untraceable_row.setVisibility(View.VISIBLE);
                    mismatch_row.setVisibility(View.GONE);
                }else{
                    untraceable_row.setVisibility(View.GONE);
                    mismatch_row.setVisibility(View.VISIBLE);
                }
            }
        });
        //office specific
        person_know_applicant_row.setVisibility(View.GONE);
        neighbour1_textview.setText("Collegue 1");
        neighbour2_textview.setText("Collegue 2");




    }


    public void cancel(View view){
        finish();
    }

    public void save(View view){}
}
