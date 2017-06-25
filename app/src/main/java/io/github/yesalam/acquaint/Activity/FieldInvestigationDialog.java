package io.github.yesalam.acquaint.Activity;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.CaseBasicId;
import io.github.yesalam.acquaint.Util.Id.OVerificationId;
import io.github.yesalam.acquaint.Util.Id.ResidentialId;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Id.RVerificationId;
import io.github.yesalam.acquaint.Util.ScalingUtilities;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.SpinnerLists.*;
import static io.github.yesalam.acquaint.Util.Util.PENDING_INVESTIGATION;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 10-06-2017.
 */

public class FieldInvestigationDialog extends Activity implements WebHelper.CallBack, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "FieldInvestigation" ;
    private static final int PERMISSION_REQUEST = 10101;
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
    @BindView(R.id.residence_status_spinner)
    Spinner residence_status_spinner;
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
    CheckBox plastered_exterior_checkbox;
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

   /* //include_address_confirmed_resident2
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
    EditText occupation_edittext;*/

    //include_address_not_confirmed
    @BindView(R.id.address_not_confirmed_frame)
    LinearLayout address_notconfirmed_frame;
    @BindView(R.id.reason_not_confirmed_radiogroup)
    RadioGroup reason_radiogruop;
    @BindView(R.id.person_know_applicant_row)
    TableRow person_know_applicant_row;
    @BindView(R.id.person_know_radiogroup)
    RadioGroup person_know_chekbox;
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
    @BindView(R.id.uploaded_images_scrollview)
    HorizontalScrollView uploadedimages_scrollview;
    @BindView(R.id.image_holder)
    LinearLayout image_holder;
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

    String investigationId;
    String client ;
    String image_file;
    SwipeRefreshLayout refreshLayout;
    Map<String,String> map;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_indie_field_investigation);
        Intent intent = getIntent();
        investigationId = intent.getStringExtra("investigationid");
        client = intent.getStringExtra("client");
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting Data");


        initForm();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        loadData();

    }

    public void save(View view){
        if(validate()) areYouSure();
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
                            cacheData();
                            finish();
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

    private void oktoSubmit(){
        map.remove("img_src");
        Map<String,String> valuesMap = getValues();
        map.putAll(valuesMap);
        submitMultiPart(map);

    }



    public void submitMultiPart(Map<String, String> map) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : map.keySet()) {
            requestBodyBuilder.addFormDataPart(key, map.get(key));
        }

        if(image_file!=null){
            File sourceFile = new File(image_file);
            Log.d(LOG_TAG, "File...::::" + sourceFile + " : " + sourceFile.exists());
            final MediaType MEDIA_TYPE = image_file.endsWith("png") ?
                    MediaType.parse("image/png") : MediaType.parse("image/jpeg");
            String filename = image_file.substring(image_file.lastIndexOf("/")+1);

            requestBodyBuilder
                    .addFormDataPart(OVerificationId.file_name, filename, RequestBody.create(MEDIA_TYPE, sourceFile));

        }



        MultipartBody requestBody = requestBodyBuilder.build();

        String TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/ResidenceVerification/"+investigationId;

        Request request = new Request.Builder()
                .url(ACQUAINT_URL + TELE_VERIFICATION_DETAIL)
                .post(requestBody)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + TELE_VERIFICATION_DETAIL + " submitting data");

        WebHelper.getInstance(this).requestCall(request, new WebHelper.CallBack() {
            @Override
            public void onPositiveResponse(String html) {
                Toast.makeText(FieldInvestigationDialog.this, "Data Submited", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onNegativeResponse(int code) {
                cacheData();
                Toast.makeText(FieldInvestigationDialog.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }
        });

    }

    private void cacheData() {
        Map<String,String> map = getValues();
        InvestigationPojo pojo = new InvestigationPojo();
        pojo.id = investigationId;
        pojo.name = map.get(ResidentialId.name);
        pojo.type= "Residence";
        pojo.address=map.get(ResidentialId.address);
        pojo.casedetail = map.get(OVerificationId.caseid);
        pojo.client = client;
        pojo.file_name = image_file;

        try {
            Util.writeObject(getApplicationContext(), investigationId, map);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "map writing error");
        }


        try {
            List<InvestigationPojo> pendingInvestigation = (List<InvestigationPojo>) Util.readObject(getApplicationContext(), Util.PENDING_INVESTIGATION);
            if (pendingInvestigation.size() > 0) {
                //progressBar.setVisibility(View.GONE);
                //passData(cachedEntries_newcase);
                pendingInvestigation.add(pojo);
            } else {
                //refreshLayout.setRefreshing(true);
                pendingInvestigation.add(pojo);
            }
            Util.writeObject(getApplicationContext(), PENDING_INVESTIGATION, pendingInvestigation);
        } catch (FileNotFoundException e) {
            List<InvestigationPojo> pendingInvestigation = new ArrayList<>();
            pendingInvestigation.add(pojo);
            try {
                Util.writeObject(getApplicationContext(), PENDING_INVESTIGATION, pendingInvestigation);
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.e(LOG_TAG, "new pendingInvestiagaion writing error");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "pendingInvestigation writing error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Connection Unavailable! Data will be Saved", Toast.LENGTH_SHORT).show();
    }

    public void cancel(View view){
        finish();
    }


    private void loadData(){
        refreshLayout.setRefreshing(true);
        String TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/ResidenceVerification/"+investigationId;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL+TELE_VERIFICATION_DETAIL)
                .build();

        WebHelper.getInstance(this).requestCall(request,this);
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
        //relationwith_applicant_spinner.setAdapter(relation_adapter);

        ArrayAdapter<SpinnerItem> totalMember_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        totalMember_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalMember_adapter.addAll(getFamilyMemberType());
        totalfamilymember_spinner.setAdapter(totalMember_adapter);
        earningmember_spinner.setAdapter(totalMember_adapter);

        ArrayAdapter<SpinnerItem> residing_Month = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        residing_Month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residing_Month.addAll(getMonthType());
        residencestatus_month_spinner.setAdapter(residing_Month);
        //livingsince_month_spinner.setAdapter(residing_Month);

        ArrayAdapter<SpinnerItem> residing_Year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        residing_Year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residing_Year.addAll(getYearType());
        residencestatus_year_spinner.setAdapter(residing_Year);
        //livingsince_year_spinner.setAdapter(residing_Year);

        ArrayAdapter<SpinnerItem> residence_status = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        residence_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residence_status.addAll(getResidenceStatus());
        residence_status_spinner.setAdapter(residence_status);


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
        untraceable_row.setVisibility(View.GONE);
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
        officeaddress_row.setVisibility(View.GONE);


        autoremark_verifier_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierremark_edittext.setText(autoRemark());
            }
        });
        autoremark_supervisor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(LOG_TAG,"supervisor remark called");
                supervisorremark_edittext.setText(autoRemark());
            }
        });

        uploadfile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissinCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permissinCheck== PackageManager.PERMISSION_DENIED){
                    requestPermission();
                }else {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 786);
                }
            }
        });
    }

    private void requestPermission(){
        Log.e(LOG_TAG,"requesting permission");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 786);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setImage(Uri uri){
        String path = ScalingUtilities.getPath(uri,this);
        String tpath = ScalingUtilities.decodeFile(this,path,356,634,investigationId);
        Log.e(LOG_TAG,tpath);
        image_file = tpath;
        File file = new File(tpath);

        ImageView imageView = new ImageView(this);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight(380);
        imageView.setMaxWidth(250);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(this).load(file).error(R.mipmap.logo).into(imageView);

        int count = image_holder.getChildCount();
        if(count>0){
            image_holder.removeViewAt(count-1);
        }
        image_holder.addView(imageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 786:
                if(resultCode == RESULT_OK){
                    final Uri imageUri = data.getData();
                    //final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    // final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //imageView.setImageBitmap(selectedImage);
                    setImage(imageUri);

                }
        }
    }

    @Override
    public void onPositiveResponse(String htmldoc) {
            map = parse(htmldoc);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    update(map);
                }
            });
    }

    @Override
    public void onNegativeResponse(int code) {
        switch (code){
            case NO_CONNECTION:
                refreshLayout.setRefreshing(false);
                Snackbar.make(investigaion_title_textview, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                        .show(); // Don’t forget to show!
                break;


        }
    }

    private void logId(Map<String,String> map){
        for(String key:map.keySet()){
            Log.e(LOG_TAG,key+" : "+map.get(key));
        }
    }

    private void update(Map<String,String> map){
        //logId(map);
        refreshLayout.setRefreshing(false);

        investigaion_title_textview.setText("Field Investigations "+investigationId);
        if(client.contains("Indiabulls")){
            clienttitle_textview.setText("Indiabulls Residence Verification");
        }else{
            clienttitle_textview.setText("SBI Residence Verification");
        }

        caseid_textview.setText(map.get(RVerificationId.caseid));
        name_textview.setText(map.get(RVerificationId.name));
        applicantname_textview.setText(map.get(RVerificationId.applicantName));
        coapplicant_textview.setText(map.get(RVerificationId.coApplicantName));
        address_textview.setText(map.get(RVerificationId.address));
        applicantiorefno_textview.setText(map.get(RVerificationId.applicationRefNo));
        contactno_textview.setText(map.get(RVerificationId.mobile));
        fielexecutive_name.setText(map.get(RVerificationId.feName));

        String proof_Attached = map.get(RVerificationId.proofAttached);
        if(proof_Attached!=null){
            boolean proofAttached = proof_Attached.equalsIgnoreCase("True");
            proofattached_radiogroup.check(proofAttached?R.id.yes_proof_attached_radiobutton:R.id.no_proof_attached_radiobutton);

            if(proofAttached){
                String typeOfProof = map.get(RVerificationId.typeofProof);
                if(typeOfProof!=null){
                    int positionTOP = ((ArrayAdapter)typeofproof_spinner.getAdapter()).getPosition(new SpinnerItem(typeOfProof));
                    typeofproof_spinner.setSelection(positionTOP);
                }
            }
        }


        neighbour1_edittext.setText(map.get(RVerificationId.neighbour1));
        neighbour2_edittext.setText(map.get(RVerificationId.neighbour2));
        visitdate_edittext.setText(map.get(RVerificationId.firstVisitDate));
        visittime_edittext.setText(map.get(RVerificationId.visitTime));
        fieldexecutive_name_textview.setText(map.get(RVerificationId.feName));
        addressupdateion_edittext.setText(map.get(RVerificationId.updateAddress));
        mobilenoupdation_edittext.setText(map.get(RVerificationId.updateMobileNo));
        phonenoupdation_edittext.setText(map.get(RVerificationId.updatePhoneNo));


        for(int i=0;i<5;i++){
            String img = map.get("img_src"+i);
            if(img == null) break;
           /* LayoutInflater inflater = getLayoutInflater();
            RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.image_closeable,null);
            View close = rl.findViewById(R.id.image_view_close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = v.getRootView();
                    view.setVisibility(View.GONE);
                }
            });*/

            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(5, 5, 5, 5);
            /*imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.logo));*/
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(250);
            imageView.setMaxWidth(380);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(this).load(ACQUAINT_URL+img).error(R.mipmap.logo).into(imageView);

            image_holder.addView(imageView);

        }

        verifierremark_edittext.setText(map.get(RVerificationId.verifierRemark));
        supervisorremark_edittext.setText(map.get(RVerificationId.superVisorRemark));





        String recommendation = map.get(RVerificationId.status);
        if(recommendation!=null){
            int positionrecommendation = ((ArrayAdapter)recommendation_spinner.getAdapter()).getPosition(new SpinnerItem(recommendation));
            recommendation_spinner.setSelection(positionrecommendation);
        }

        String address_confirmed = map.get(RVerificationId.addressConfirmed);
        if(address_confirmed == null){
            return;
        }else {
            boolean addressConfirmed = map.get(RVerificationId.addressConfirmed).equalsIgnoreCase("True");
            addressconfirmed_radiogroup.check(addressConfirmed ? R.id.yes_address_confirmed_radiobutton : R.id.no_address_confirmed_radiobutton);


            String confirmedBy = map.get(RVerificationId.addressConfirmedBy);
            if (confirmedBy != null) {
                int positonConfirmedBy = ((ArrayAdapter) confirmedby_spinner.getAdapter()).getPosition(new SpinnerItem(confirmedBy));
                confirmedby_spinner.setSelection(positonConfirmedBy);
            }


            if (addressConfirmed) {
                boolean namePlateSeen = map.get(RVerificationId.namePlateSeen).equalsIgnoreCase("True");
                nameplateseen_radiogroup.check(namePlateSeen ? R.id.yes_name_plate_seen_radiobutton : R.id.no_name_plate_seen_radiobutton);

                applicantname_edittext.setText(map.get(RVerificationId.applicant_name));
                dateofbirth_edittext.setText(map.get(RVerificationId.dateOfBirth));
                personmet_edittext.setText(map.get(RVerificationId.personMet));

                String relation = map.get(RVerificationId.relation);
                if (relation != null) {
                    int positionrelation = ((ArrayAdapter) relation_spinner.getAdapter()).getPosition(new SpinnerItem(relation));
                    relation_spinner.setSelection(positionrelation);
                }

                String totalMember = map.get(RVerificationId.totalFamilyMembers);
                if (totalMember != null) {
                    int positiontotal = ((ArrayAdapter) totalfamilymember_spinner.getAdapter()).getPosition(new SpinnerItem(totalMember));
                    totalfamilymember_spinner.setSelection(positiontotal);
                }

                String earningMemeber = map.get(RVerificationId.earningFamilyMembers);
                if (earningMemeber != null) {
                    int positionEarning = ((ArrayAdapter) earningmember_spinner.getAdapter()).getPosition(new SpinnerItem(earningMemeber));
                    earningmember_spinner.setSelection(positionEarning);
                }

                String residenceStatus = map.get(RVerificationId.residenceStatus);
                if (residenceStatus != null) {
                    int positionResidenceStatus = ((ArrayAdapter) residence_status_spinner.getAdapter()).getPosition(new SpinnerItem(residenceStatus));
                    residence_status_spinner.setSelection(positionResidenceStatus);
                }

                String residingMonth = map.get(RVerificationId.residingSinceMonth);
                if (residingMonth != null) {
                    int positionResidingMonth = ((ArrayAdapter) residencestatus_month_spinner.getAdapter()).getPosition(new SpinnerItem(residingMonth));
                    residencestatus_month_spinner.setSelection(positionResidingMonth);
                }

                String residingYear = map.get(RVerificationId.residingSinceYear);
                if (residingYear != null) {
                    int positionResidingYear = ((ArrayAdapter) residencestatus_year_spinner.getAdapter()).getPosition(new SpinnerItem(residingYear));
                    residencestatus_year_spinner.setSelection(positionResidingYear);
                }

                approxarea_edittext.setText(map.get(RVerificationId.approxArea));
                approxvalue_edittext.setText(map.get(RVerificationId.approxValue));
                rentmonthly_edittext.setText(map.get(RVerificationId.rent));
                employer_edittext.setText(map.get(RVerificationId.nameofEmployer));
                employeraddress_edittext.setText(map.get(RVerificationId.employerAddress));
                designation_edittext.setText(map.get(RVerificationId.designation));


                String easeOfLocation = map.get(RVerificationId.easeofLocation);
                if (easeOfLocation != null) {
                    int positionEaseOfLocation = ((ArrayAdapter) easeoflocation_spinner.getAdapter()).getPosition(new SpinnerItem(easeOfLocation));
                    easeoflocation_spinner.setSelection(positionEaseOfLocation);
                }


                String locality = map.get(RVerificationId.locality);
                if (locality != null) {
                    int positonLocality = ((ArrayAdapter) locality_spinner.getAdapter()).getPosition(new SpinnerItem(locality));
                    locality_spinner.setSelection(positonLocality);
                }

                String accomodationType = map.get(RVerificationId.accomodationType);
                if (accomodationType != null) {
                    int positionAccomodationType = ((ArrayAdapter) accomadationtype_spinner.getAdapter()).getPosition(new SpinnerItem(accomodationType));
                    accomadationtype_spinner.setSelection(positionAccomodationType);
                }

                String standardOfLiving = map.get(RVerificationId.standardofLiving);
                if (standardOfLiving != null) {
                    int positionStandardOfLiving = ((ArrayAdapter) statndard_living_spinner.getAdapter()).getPosition(new SpinnerItem(standardOfLiving));
                    statndard_living_spinner.setSelection(positionStandardOfLiving);
                }

                String interior = map.get(RVerificationId.interiorCondition);
                List<String> interiorConditions = Arrays.asList(interior.split(","));
                painted_interior_checkbox.setChecked(interiorConditions.contains("P"));
                furnished_interior_checkbox.setChecked(interiorConditions.contains("F"));
                carpeted_interior_checkbox.setChecked(interiorConditions.contains("C"));
                curtains_interior_checkbox.setChecked(interiorConditions.contains("U"));

                String exterior = map.get(RVerificationId.exteriorCondition);
                List<String> exteriorConditions = Arrays.asList(exterior.split(","));
                plastered_exterior_checkbox.setChecked(exteriorConditions.contains("P"));
                painted_exterior_checkbox.setChecked(exteriorConditions.contains("A"));
                securityguard_exterior_checkbox.setChecked(exteriorConditions.contains("S"));
                parking_exterior_checkbox.setChecked(exteriorConditions.contains("R"));
                garden_exterior_checkbox.setChecked(exteriorConditions.contains("G"));

                String assetSeen = map.get(RVerificationId.assetsSeen);
                List<String> assets = Arrays.asList(assetSeen.split(","));
                television_asset_checkbox.setChecked(assets.contains("T"));
                refrigerator_asset_checkbox.setChecked(assets.contains("R"));
                ac_asset_checkbox.setChecked(assets.contains("A"));
                musicsystem_asset_checkbox.setChecked(assets.contains("M"));

                String vehicleType = map.get(RVerificationId.vehicleType);
                List<String> vehicleTypes = Arrays.asList(vehicleType.split(","));
                two_wheeler_checkbox.setChecked(vehicleTypes.contains("T"));
                four_wheeler_checkbox.setChecked(vehicleTypes.contains("F"));
                other_vehicle_checkbox.setChecked(vehicleTypes.contains("O"));

                vehicledetail_edittext.setText(map.get(RVerificationId.vehicleDetail));
                nearestlandmark_edittext.setText(map.get(RVerificationId.nearestLandMark));

               /* applicantname2_textview.setText(map.get(RVerificationId.applicant_name));
                personcontacted_edittext.setText(map.get(RVerificationId.personContacted));
                applicantapproxage_edittext.setText(map.get(RVerificationId.applicantApproxAge));
                noofresident_edittext.setText(map.get(RVerificationId.noofResidents));
                occupation_edittext.setText(map.get(RVerificationId.occupation));


                String relationWith = map.get(RVerificationId.relationWithApplicant);
                if (relationWith != null) {
                    int positionRelationWith = ((ArrayAdapter) relationwith_applicant_spinner.getAdapter()).getPosition(new SpinnerItem(relationWith));
                    relationwith_applicant_spinner.setSelection(positionRelationWith);
                }

                String livingSinceMonth = map.get(RVerificationId.livingSinceMonth);
                if (livingSinceMonth != null) {
                    int positionLivingSince = ((ArrayAdapter) livingsince_month_spinner.getAdapter()).getPosition(new SpinnerItem(livingSinceMonth));
                    livingsince_month_spinner.setSelection(positionLivingSince);
                }

                String livingSinceYear = map.get(RVerificationId.livingSinceYear);
                if (livingSinceYear != null) {
                    int positionLivingYear = ((ArrayAdapter) livingsince_year_spinner.getAdapter()).getPosition(new SpinnerItem(livingSinceYear));
                    livingsince_year_spinner.setSelection(positionLivingYear);
                }*/


            } else {
                String reason = map.get(RVerificationId.notConfirmedType);
                if (reason != null) {
                    boolean untraceable = reason.equalsIgnoreCase("U");
                    reason_radiogruop.check(untraceable ? R.id.untraceable_reason_not_confirmed_radiobutton : R.id.mismatch_reason_not_confirmed_radiobutton);
                }

                String person_know = map.get(RVerificationId.personKnowApplicant);
                if (person_know != null) {
                    boolean personKnow = person_know.equalsIgnoreCase("True");
                    person_know_chekbox.check(personKnow ? R.id.yes_person_know_applicant_radiobutton : R.id.no_person_know_applicant_radiobutton);
                    address_belongto_edittext.setText(map.get(RVerificationId.addressBelongsTo));
                }

                reason_edittext.setText(map.get(RVerificationId.notConfirmedReason));
                resultOfCalling_edittex.setText(map.get(RVerificationId.resultofCalling));

                String notConfirmedLocality = map.get(RVerificationId.notConfirmedLocality);
                if (notConfirmedLocality != null) {
                    int positionNotConfirmedLocality = ((ArrayAdapter) locality_spinner_not.getAdapter()).getPosition(new SpinnerItem(notConfirmedLocality));
                    locality_spinner_not.setSelection(positionNotConfirmedLocality);
                }

            }


        }



    }

    private boolean validate(){
        int confirmed = addressconfirmed_radiogroup.getCheckedRadioButtonId();
        if(confirmed<0) {
            Toast.makeText(this, "Please select Address Confirmed button", Toast.LENGTH_SHORT).show();
            return false;
        }

        String recommendation = ((SpinnerItem)recommendation_spinner.getSelectedItem()).getValue();
        try{
            int recommend = Integer.parseInt(recommendation);

            Toast.makeText(this, "Please select recommendation", Toast.LENGTH_SHORT).show();
            return false;

        }catch (NumberFormatException  nfe){
            //map.put(OVerificationId.status,recommendation);
        }


        return  true;
    }

    private Map<String,String> getValues(){
        Map<String,String> map = new HashMap<>();

        map.put(RVerificationId.caseid,String.valueOf(caseid_textview.getText()));



        int confirmed = addressconfirmed_radiogroup.getCheckedRadioButtonId();
        if(confirmed>0) {
            String aConfirmed = confirmed == R.id.yes_address_confirmed_radiobutton ? "True" : "False";
            map.put(OVerificationId.addressConfirmed,aConfirmed);

            int board = nameplateseen_radiogroup.getCheckedRadioButtonId();
            if(board>0){
                String seen = board == R.id.yes_name_plate_seen_radiobutton?"True":"False" ;
                map.put(RVerificationId.namePlateSeen,seen);
            }

            map.put(RVerificationId.applicant_name, String.valueOf(applicantname_edittext.getText()));
            map.put(RVerificationId.dateOfBirth, String.valueOf(dateofbirth_edittext.getText()));
            map.put(RVerificationId.personMet, String.valueOf(personmet_edittext.getText()));

            String relation =  ((SpinnerItem) relation_spinner.getSelectedItem()).getValue();
            try{
                int relationType = Integer.parseInt(relation);
                if(relationType==0) map.put(RVerificationId.relation,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.relation,relation);
            }

            String familyMember =  ((SpinnerItem) totalfamilymember_spinner.getSelectedItem()).getValue();
            try{
                int totalMember = Integer.parseInt(familyMember);
                if(totalMember==0) map.put(RVerificationId.totalFamilyMembers,"");
                else map.put(RVerificationId.totalFamilyMembers,familyMember);
            }catch (NumberFormatException nfe){

            }

            String earningMember =  ((SpinnerItem) earningmember_spinner.getSelectedItem()).getValue();
            try{
                int totalEarning = Integer.parseInt(earningMember);
                if(totalEarning==0) map.put(RVerificationId.earningFamilyMembers,"");
                else map.put(RVerificationId.earningFamilyMembers,earningMember);
            }catch (NumberFormatException nfe){
                nfe.printStackTrace();
            }

            String residenceStatus =  ((SpinnerItem) residence_status_spinner.getSelectedItem()).getValue();
            try{
                int resiSatatus = Integer.parseInt(residenceStatus);
                if(resiSatatus==0) map.put(RVerificationId.residenceStatus,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.residenceStatus,residenceStatus);
            }

            String sinceMonth =  ((SpinnerItem) residencestatus_month_spinner.getSelectedItem()).getValue();
            try{
                int month = Integer.parseInt(sinceMonth);
                if(month==0) map.put(RVerificationId.residingSinceMonth,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.residingSinceMonth,sinceMonth);
            }

            String sinceYear =  ((SpinnerItem) residencestatus_year_spinner.getSelectedItem()).getValue();
            try{
                int year = Integer.parseInt(sinceYear);
                if(year==0) map.put(RVerificationId.residingSinceYear,"");
                else map.put(RVerificationId.residingSinceYear,sinceYear);
            }catch (NumberFormatException nfe){
                nfe.printStackTrace();
            }

            map.put(RVerificationId.approxArea, String.valueOf(approxarea_edittext.getText()));
            map.put(RVerificationId.approxValue, String.valueOf(approxvalue_edittext.getText()));
            map.put(RVerificationId.rent, String.valueOf(rentmonthly_edittext.getText()));
            map.put(RVerificationId.nameofEmployer, String.valueOf(employer_edittext.getText()));
            map.put(RVerificationId.designation, String.valueOf(designation_edittext.getText()));
            map.put(RVerificationId.employerAddress, String.valueOf(employeraddress_edittext.getText()));

            String easeLocation =  ((SpinnerItem) easeoflocation_spinner.getSelectedItem()).getValue();
            try{
                int locationEase = Integer.parseInt(easeLocation);
                if(locationEase==0) map.put(RVerificationId.easeofLocation,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.easeofLocation,easeLocation);
            }

            String locality =  ((SpinnerItem) locality_spinner.getSelectedItem()).getValue();
            try{
                int localty = Integer.parseInt(locality);
                if(localty==0) map.put(RVerificationId.locality,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.locality,locality);
            }

            String accomoTypoe =  ((SpinnerItem) accomadationtype_spinner.getSelectedItem()).getValue();
            try{
                int accType = Integer.parseInt(accomoTypoe);
                if(accType==0) map.put(RVerificationId.accomodationType,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.accomodationType,accomoTypoe);
            }

            String interior = "" ;
            if(painted_interior_checkbox.isChecked()) interior+="P,";
            if(furnished_interior_checkbox.isChecked()) interior+="F,";
            if(carpeted_interior_checkbox.isChecked()) interior+="C,";
            if(curtains_interior_checkbox.isChecked()) interior+="U,";
            map.put(RVerificationId.interiorCondition,interior);

            String exterior = "" ;
            if(plastered_exterior_checkbox.isChecked()) exterior+="P,";
            if(painted_exterior_checkbox.isChecked()) exterior+="A,";
            if(securityguard_exterior_checkbox.isChecked()) exterior+="S,";
            if(parking_exterior_checkbox.isChecked()) exterior+="R,";
            if(garden_exterior_checkbox.isChecked()) exterior+="G,";
            map.put(RVerificationId.exteriorCondition,exterior) ;

            String assets = "" ;
            if(television_asset_checkbox.isChecked()) assets+="T,";
            if(refrigerator_asset_checkbox.isChecked()) assets+="R,";
            if(ac_asset_checkbox.isChecked()) assets+="A,";
            if(musicsystem_asset_checkbox.isChecked()) assets+="M,";
            map.put(RVerificationId.assetsSeen,assets) ;

            String standardLiving =  ((SpinnerItem) statndard_living_spinner.getSelectedItem()).getValue();
            try{
                int livingStandard = Integer.parseInt(standardLiving);
                if(livingStandard==0) map.put(RVerificationId.standardofLiving,"");
            }catch (NumberFormatException nfe){
                map.put(RVerificationId.standardofLiving,standardLiving);
            }

            String vehicleType = "" ;
            if(two_wheeler_checkbox.isChecked()) vehicleType+="T,";
            if(four_wheeler_checkbox.isChecked()) vehicleType+="F,";
            if(other_vehicle_checkbox.isChecked()) vehicleType+="O,";
            map.put(RVerificationId.vehicleType,vehicleType) ;

            map.put(RVerificationId.vehicleDetail, String.valueOf(vehicledetail_edittext.getText()));
            map.put(RVerificationId.nearestLandMark, String.valueOf(nearestlandmark_edittext.getText()));

        }


        String confirmedBy =  ((SpinnerItem) confirmedby_spinner.getSelectedItem()).getValue();
        try{
            int confirmedby = Integer.parseInt(confirmedBy);
            if(confirmedby==0) map.put(RVerificationId.addressConfirmedBy,"");
        }catch (NumberFormatException nfe){
            map.put(RVerificationId.addressConfirmedBy,confirmedBy);
        }

       // map.put(RVerificationId.officeAddress, String.valueOf(officeaddress_edittext.getText()));
        int proffAta = proofattached_radiogroup.getCheckedRadioButtonId();
        if(proffAta>0) {
            String aConfirmed = proffAta == R.id.yes_address_confirmed_radiobutton ? "True" : "False";
            map.put(RVerificationId.addressConfirmed, aConfirmed);
        }

        String typeProof = ((SpinnerItem)typeofproof_spinner.getSelectedItem()).getValue();
        try{
            int typeOProof = Integer.parseInt(typeProof);
            if(typeOProof==0) map.put(RVerificationId.typeofProof,"");
        }catch (NumberFormatException  nfe){
            map.put(RVerificationId.typeofProof,typeProof);
        }

        map.put(RVerificationId.neighbour1, String.valueOf(neighbour1_edittext.getText()));
        map.put(RVerificationId.neighbour2, String.valueOf(neighbour2_edittext.getText()));
        map.put(RVerificationId.firstVisitDate, String.valueOf(visitdate_edittext.getText()));
        map.put(RVerificationId.visitTime, String.valueOf(visittime_edittext.getText()));
        map.put(RVerificationId.verifierRemark, String.valueOf(verifierremark_edittext.getText()));


        map.put(RVerificationId.updateAddress, String.valueOf(addressupdateion_edittext.getText()));
        map.put(RVerificationId.updateMobileNo, String.valueOf(mobilenoupdation_edittext.getText()));
        map.put(RVerificationId.updatePhoneNo, String.valueOf(phonenoupdation_edittext.getText()));

        String recommendation = ((SpinnerItem)recommendation_spinner.getSelectedItem()).getValue();
        try{
            int recommend = Integer.parseInt(recommendation);
            if(recommend==0) map.put(RVerificationId.status,"");
        }catch (NumberFormatException  nfe){
            map.put(RVerificationId.status,recommendation);
        }

        map.put(RVerificationId.superVisorRemark, String.valueOf(supervisorremark_edittext.getText()));

        return  map;
    }

    private Map<String,String> parse(String html){
        Map<String,String> map = new HashMap<>();

        Document document = Jsoup.parse(html);

        String applicantName = document.select("#formzipcode > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(2) > td.table-number.Impair").text();
        String coApplicantName = document.select("#formzipcode > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(4) > td.table-number").text();
        map.put("applicantName",applicantName);
        map.put("coApplicantName",coApplicantName);

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for(Element input:elements){
            String name = input.attr("name");
            String value =input.attr("value");

            String type = input.attr("type");
            if(type.equalsIgnoreCase("radio")){
                String checked = input.attr("checked");
                if(checked.equalsIgnoreCase("checked")){
                    map.put(name,value);
                }
            }else{
                map.put(name,value);
            }

        }

        Elements selects = form.getElementsByTag("select");
        for(Element select:selects){
            String id = select.id();
            //Log.e(LOG_TAG,id);
            try{
                String value = select.getElementsByAttributeValue("selected","selected").first().attr("value");
                //Log.e(LOG_TAG,value);
                map.put(id,value);
            }catch (NullPointerException npe){
                npe.printStackTrace();
                map.put(id,"0");
            }
        }

        Elements imgs = form.getElementsByTag("img");
        if(imgs!=null){
            for(int i=0;i<imgs.size();i++){
                String src = imgs.get(i).attr("src");
                map.put("img_src"+i,src);
                Log.e(LOG_TAG,"img : "+src);
            }
        }

        Elements textareas = form.getElementsByTag("textarea");
        for(Element textarea:textareas){
            map.put(textarea.id(),textarea.text());
        }





        return map;
    }

    private String autoRemark(){
        String format = "VISIT DONE ADDRESS CONFIRMED MET WITH %s (%s) AS PER RESPONDED APPLICANT IS RESIDING IN %s PREMISES SINCE LAST %s;TOTAL FAMILY MEMBERS ARE %s OUT OF %s ARE EARNING;%s LOCALITY;%s DOMINATED AREA;APPROXIMATE BUILT UP SIZE IS %s SQFT;BUILT UP TYPE %s;LIVING STANDARD IS %s;NEIGHBOUR CHECK DONE WITH MR. %s AND NO ADVERSE FEEDBACK FOUND;";
        String personMet = String.valueOf(personmet_edittext.getText());
        String relation = ((SpinnerItem)relation_spinner.getSelectedItem()).getText();
        String residenceStatus = ((SpinnerItem)residence_status_spinner.getSelectedItem()).getText();
        String sinceLast = ((SpinnerItem)residencestatus_month_spinner.getSelectedItem()).getText()+((SpinnerItem)residencestatus_year_spinner.getSelectedItem()).getText();
        String familyMember = ((SpinnerItem)totalfamilymember_spinner.getSelectedItem()).getText();
        String earningMemeber = ((SpinnerItem)earningmember_spinner.getSelectedItem()).getText();
        String easeOfLocation = ((SpinnerItem)easeoflocation_spinner.getSelectedItem()).getText();
        String locality = ((SpinnerItem)locality_spinner.getSelectedItem()).getText();
        String area = String.valueOf(approxarea_edittext.getText());
        String buildType = ((SpinnerItem)accomadationtype_spinner.getSelectedItem()).getText();
        String livingStandard = ((SpinnerItem)statndard_living_spinner.getSelectedItem()).getText();
        String neighbour = String.valueOf(neighbour1_edittext.getText());

        String output = String.format(format,personMet.toUpperCase(),relation.toUpperCase(),residenceStatus.toUpperCase(),sinceLast.toUpperCase(),familyMember.toUpperCase(),earningMemeber.toUpperCase(),easeOfLocation.toUpperCase(),locality.toUpperCase(),area,buildType.toUpperCase(),livingStandard.toUpperCase(),neighbour.toUpperCase());
        return output;
    }


    @Override
    public void onRefresh() {
        loadData();
    }
}
