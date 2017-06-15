package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.EGLDisplay;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;
import io.github.yesalam.acquaint.Util.HaveClickListener;

import static io.github.yesalam.acquaint.Util.Util.getAccomodationType;
import static io.github.yesalam.acquaint.Util.Util.getAddressConfirmedByType;
import static io.github.yesalam.acquaint.Util.Util.getAssignedToType;
import static io.github.yesalam.acquaint.Util.Util.getEaseofLocatingType;
import static io.github.yesalam.acquaint.Util.Util.getFamilyMemberType;
import static io.github.yesalam.acquaint.Util.Util.getLivingStandardType;
import static io.github.yesalam.acquaint.Util.Util.getLocalityType;
import static io.github.yesalam.acquaint.Util.Util.getMonthType;
import static io.github.yesalam.acquaint.Util.Util.getRecommendationType;
import static io.github.yesalam.acquaint.Util.Util.getRelationType;
import static io.github.yesalam.acquaint.Util.Util.getResidenceProofType;
import static io.github.yesalam.acquaint.Util.Util.getYearType;

/**
 * Created by yesalam on 10-06-2017.
 */

public class FieldInvestigationDialog extends Activity {

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


    ///include_address_confirmed_resident
    @BindView(R.id.address_confirmed_resident_frame)
    LinearLayout addressconfirmed_resident_frame;
    @BindView(R.id.name_plate_seen_radiogroup)
    RadioGroup nameplateseen_radiogroup;
    @BindView(R.id.applicant_name_edittext)
    EditText applicantname_edittext;
    @BindView(R.id.date_of_birth_edittext)
    EditText dateofbirth_edittext;
    @BindView(R.id.person_met_edittext)
    EditText personmet_edittext;
    @BindView(R.id.relation_spinner)
    Spinner relation_spinner;
    @BindView(R.id.total_family_member_spinner)
    Spinner totalfamilymember_spinner;
    @BindView(R.id.earning_family_member_spinner)
    Spinner earningmember_spinner;
    @BindView(R.id.residence_status_month_spinner)
    Spinner residencestatus_month_spinner;
    @BindView(R.id.residence_status_year_spinner)
    Spinner residencestatus_year_spinner;
    @BindView(R.id.approx_area_edittext)
    EditText approxarea_edittext;
    @BindView(R.id.approx_value_edittext)
    EditText approxvalue_edittext;
    @BindView(R.id.rent_monthly_edittext)
    EditText rentmonthly_edittext;
    @BindView(R.id.employer_edittext)
    EditText employer_edittext;
    @BindView(R.id.designation_edittext)
    EditText designation_edittext;
    @BindView(R.id.employer_address_edittext)
    EditText employeraddress_edittext;
    @BindView(R.id.ease_of_location_spinner)
    Spinner easeoflocation_spinner;
    @BindView(R.id.locality_spinner)
    Spinner locality_spinner;
    @BindView(R.id.accomodation_type)
    Spinner accomadationtype_spinner;
    //interior condition checkbox
    @BindView(R.id.painted_interior_condition_checkbox)
    CheckBox painted_interior_checkbox;
    @BindView(R.id.furnished_interior_condition_checkbox)
    CheckBox furnished_interior_checkbox;
    @BindView(R.id.carpeted_interior_condition_checkbox)
    CheckBox carpeted_interior_checkbox;
    @BindView(R.id.curtains_interior_condition_checkbox)
    CheckBox curtains_interior_checkbox;
    //exterior condition checkbox
    @BindView(R.id.plastered_exterior_condition_checkbox)
    CheckBox palstered_exterior_checkbox;
    @BindView(R.id.painted_exterior_condition_checkbox)
    CheckBox painted_exterior_checkbox;
    @BindView(R.id.security_guard_condition_checkbox)
    CheckBox securityguard_exterior_checkbox;
    @BindView(R.id.parking_exterior_condition_checkbox)
    CheckBox parking_exterior_checkbox;
    @BindView(R.id.garden_exterior_condition_checkbox)
    CheckBox garden_exterior_checkbox;
    //assets seen
    @BindView(R.id.television_assets_seen_checkbox)
    CheckBox television_asset_checkbox;
    @BindView(R.id.refrigerator_assets_seen_checkbox)
    CheckBox refrigerator_asset_checkbox;
    @BindView(R.id.air_conditioned_assets_seen_checkbox)
    CheckBox ac_asset_checkbox;
    @BindView(R.id.music_system_assets_seen_checkbox)
    CheckBox musicsystem_asset_checkbox;
    //Vehicle Type
    @BindView(R.id.two_wheeler_vehicle_type_checkbox)
    CheckBox two_wheeler_checkbox;
    @BindView(R.id.four_wheeler_vehicle_type_checkbox)
    CheckBox four_wheeler_checkbox;
    @BindView(R.id.other_vehicle_type_checkbox)
    CheckBox other_vehicle_checkbox;
    ////
    @BindView(R.id.standard_of_living_spinner)
    Spinner statndard_living_spinner;
    @BindView(R.id.vehicle_detail_edittext)
    EditText vehicledetail_edittext;
    @BindView(R.id.nearest_landmark_edittext)
    EditText nearestlandmark_edittext;
    //include_address_confirmed_resident2
    @BindView(R.id.applicant_name_textview)
    TextView applicantname2_textview;
    @BindView(R.id.person_contacted_edittext)
    EditText personcontacted_edittext;
    @BindView(R.id.relation_with_applicant_spinner)
    Spinner relationwith_applicant_spinner;
    @BindView(R.id.applicant_approx_age_edittext)
    EditText applicantapproxage_edittext;
    @BindView(R.id.no_of_residents_edittext)
    EditText noofresident_edittext;
    @BindView(R.id.living_since_month_spinner)
    Spinner livingsince_month_spinner;
    @BindView(R.id.living_since_year_spinner)
    Spinner livingsince_year_spinner;
    @BindView(R.id.occupation_edittext)
    EditText occupation_edittext;

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
        setContentView(R.layout.dialog_indie_field_investigation);

        ButterKnife.bind(this);


        initForm();


    }


    private void initForm() {
        addressconfirmed_resident_frame.setVisibility(View.GONE);
        address_notconfirmed_frame.setVisibility(View.GONE);
        addressconfirmed_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.yes_address_confirmed_radiobutton) {
                    addressconfirmed_resident_frame.setVisibility(View.VISIBLE);
                    address_notconfirmed_frame.setVisibility(View.GONE);
                } else {
                    addressconfirmed_resident_frame.setVisibility(View.GONE);
                    address_notconfirmed_frame.setVisibility(View.VISIBLE);
                }
            }
        });

        ArrayAdapter<SpinnerItem> confirmedByAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        confirmedByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confirmedByAdapter.addAll(getAddressConfirmedByType());
        confirmedby_spinner.setAdapter(confirmedByAdapter);

        ArrayAdapter<SpinnerItem> typeOfProofAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        typeOfProofAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfProofAdapter.addAll(getResidenceProofType());
        typeofproof_spinner.setAdapter(typeOfProofAdapter);

        ArrayAdapter<SpinnerItem> recommendation_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        recommendation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recommendation_adapter.addAll(getRecommendationType());
        recommendation_spinner.setAdapter(recommendation_adapter);

        visitdate_edittext.setOnClickListener(new DateClick(this));

        //address confirmed initialization
        dateofbirth_edittext.setOnClickListener(new DateClick(this));

        ArrayAdapter<SpinnerItem> relation_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        relation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation_adapter.addAll(getRelationType());
        relation_spinner.setAdapter(relation_adapter);
        relationwith_applicant_spinner.setAdapter(relation_adapter);

        ArrayAdapter<SpinnerItem> totalMember_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        totalMember_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalMember_adapter.addAll(getFamilyMemberType());
        totalfamilymember_spinner.setAdapter(totalMember_adapter);
        earningmember_spinner.setAdapter(totalMember_adapter);

        ArrayAdapter<SpinnerItem> residing_Month = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        residing_Month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residing_Month.addAll(getMonthType());
        residencestatus_month_spinner.setAdapter(residing_Month);
        livingsince_month_spinner.setAdapter(residing_Month);

        ArrayAdapter<SpinnerItem> residing_Year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        residing_Year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residing_Year.addAll(getYearType());
        residencestatus_year_spinner.setAdapter(residing_Year);
        livingsince_year_spinner.setAdapter(residing_Year);


        ArrayAdapter<SpinnerItem> locationEase_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        locationEase_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationEase_adapter.addAll(getEaseofLocatingType());
        easeoflocation_spinner.setAdapter(locationEase_adapter);

        ArrayAdapter<SpinnerItem> locality_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        locality_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locality_adapter.addAll(getLocalityType());
        locality_spinner.setAdapter(locality_adapter);
        locality_spinner_not.setAdapter(locality_adapter);

        ArrayAdapter<SpinnerItem> accomodation_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        accomodation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accomodation_adapter.addAll(getAccomodationType());
        accomadationtype_spinner.setAdapter(accomodation_adapter);

        ArrayAdapter<SpinnerItem> livingStandard_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        livingStandard_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        livingStandard_adapter.addAll(getLivingStandardType());
        statndard_living_spinner.setAdapter(livingStandard_adapter);

        mismatch_row.setVisibility(View.GONE);
        reason_radiogruop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.untraceable_reason_not_confirmed_radiobutton) {
                    untraceable_row.setVisibility(View.VISIBLE);
                    mismatch_row.setVisibility(View.GONE);
                } else {
                    untraceable_row.setVisibility(View.GONE);
                    mismatch_row.setVisibility(View.VISIBLE);
                }
            }
        });

        //residence specific
        isrcb_row.setVisibility(View.GONE);
        companyname_row.setVisibility(View.GONE);


    }

}
