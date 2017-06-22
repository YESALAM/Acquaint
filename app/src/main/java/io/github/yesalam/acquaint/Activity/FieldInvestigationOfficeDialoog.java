package io.github.yesalam.acquaint.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Id.OVerificationId;
import io.github.yesalam.acquaint.Util.ScalingUtilities;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Request;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.SpinnerLists.*;

/**
 * Created by yesalam on 12-06-2017.
 */

public class FieldInvestigationOfficeDialoog extends AppCompatActivity implements WebHelper.CallBack {

    private static final String LOG_TAG = "FieldInviOffice";
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
    @BindView(R.id.proof_attached_row)
    TableRow proof_attached_row;
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
    List<String> image_list ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_indie_field_office_investigation);
        Intent intent = getIntent() ;
        investigationId = intent.getStringExtra("investigationid");
        client =intent.getStringExtra("client");
        ButterKnife.bind(this);
        image_list = new ArrayList<>();
        initForm();
        loadData();
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
        confirmedByAdapter.addAll(getOfficeAddressConfirmedByType());
        confirmedby_spinner.setAdapter(confirmedByAdapter);

        ArrayAdapter<SpinnerItem> typeOfProofAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        typeOfProofAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfProofAdapter.addAll(getOfficeProofType());
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
        locality_adapter.addAll(getOfficeLocalityType());
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
        untraceable_row.setVisibility(View.GONE);
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
        proof_attached_row.setVisibility(View.GONE);

        autoremark_verifier_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierremark_edittext.setText(autoRemark());
            }
        });

        autoremark_supervisor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        image_list.add(tpath);
        File file = new File(tpath);

        ImageView imageView = new ImageView(this);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight(380);
        imageView.setMaxWidth(250);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(this).load(file).error(R.mipmap.logo).into(imageView);

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


    public void cancel(View view){
        finish();
    }

    public void save(View view){}


    private void loadData(){
        String TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/OfficeVerification/"+investigationId;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL+TELE_VERIFICATION_DETAIL)
                .build();
        WebHelper.getInstance(this).requestCall(request,this);
    }

    @Override
    public void onPositiveResponse(String htmldoc) {
        final Map map = parse(htmldoc);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                update(map);
            }
        });
    }

    private void logId(Map<String,String> map){
        for(String key:map.keySet()){
            Log.e(LOG_TAG,key+" : "+map.get(key));
        }
    }

    private void update(Map<String,String> map){
        logId(map);

        investigaion_title_textview.setText("Field Investigations "+investigationId);
        if(client.contains("Indiabulls")){
            clienttitle_textview.setText("Indiabulls Office Verification");
        }else{
            clienttitle_textview.setText("SBI Office Verification");
        }

        caseid_textview.setText(map.get(OVerificationId.caseid));
        name_textview.setText(map.get(OVerificationId.name));
        applicantname_textview.setText(map.get(OVerificationId.applicantName));
        coapplicant_textview.setText(map.get(OVerificationId.coApplicantName));
        companyname_textview.setText(map.get(OVerificationId.companyName));
        address_textview.setText(map.get(OVerificationId.address));
        applicantiorefno_textview.setText(map.get(OVerificationId.applicationRefNo));
        contactno_textview.setText(map.get(OVerificationId.mobile));
        fielexecutive_name.setText(map.get(OVerificationId.feName));

       /* String proof_Attached = map.get(OVerificationId.proofAttached);
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
        }*/


        officeaddress_edittext.setText(map.get(OVerificationId.officeAddress));
        String typeOfProof = map.get(OVerificationId.typeofProof);
        if(typeOfProof!=null){
            int positionTOP = ((ArrayAdapter)typeofproof_spinner.getAdapter()).getPosition(new SpinnerItem(typeOfProof));
            typeofproof_spinner.setSelection(positionTOP);
        }


        neighbour1_edittext.setText(map.get(OVerificationId.collegue1));
        neighbour2_edittext.setText(map.get(OVerificationId.collegue2));
        visitdate_edittext.setText(map.get(OVerificationId.firstVisitDate));
        visittime_edittext.setText(map.get(OVerificationId.visitTime));
        fieldexecutive_name_textview.setText(map.get(OVerificationId.feName));

        String isRcbCase = map.get(OVerificationId.isRCB);
        if(isRcbCase!=null){
            boolean isRcb = isRcbCase.equalsIgnoreCase("True");
            isrcb_radiogroup.check(isRcb ? R.id.yes_is_rcb_case_radiobutton : R.id.no_is_rcb_case_radiobutton);
        }


        addressupdateion_edittext.setText(map.get(OVerificationId.updateAddress));
        mobilenoupdation_edittext.setText(map.get(OVerificationId.updateMobileNo));
        phonenoupdation_edittext.setText(map.get(OVerificationId.updatePhoneNo));
        employementupdation_edittext.setText(map.get(OVerificationId.updateEmploymentDetail));

        for(int i=0;i<5;i++){
            String img = map.get("img_src"+i);
            if(img == null) break;

            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(250);
            imageView.setMaxWidth(380);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(this).load(ACQUAINT_URL+img).error(R.mipmap.logo).into(imageView);

            image_holder.addView(imageView);

        }

        verifierremark_edittext.setText(map.get(OVerificationId.verifierRemark));
        supervisorremark_edittext.setText(map.get(OVerificationId.superVisorRemark));



        String recommendation = map.get(OVerificationId.status);
        if(recommendation!=null){
            int positionrecommendation = ((ArrayAdapter)recommendation_spinner.getAdapter()).getPosition(new SpinnerItem(recommendation));
            recommendation_spinner.setSelection(positionrecommendation);
        }

        String address_confirmed = map.get(OVerificationId.addressConfirmed);
        if(address_confirmed == null){
            return;
        }else {
            boolean addressConfirmed = map.get(OVerificationId.addressConfirmed).equalsIgnoreCase("True");
            addressconfirmed_radiogroup.check(addressConfirmed ? R.id.yes_address_confirmed_radiobutton : R.id.no_address_confirmed_radiobutton);


            String confirmedBy = map.get(OVerificationId.addressConfirmedBy);
            if (confirmedBy != null) {
                int positonConfirmedBy = ((ArrayAdapter) confirmedby_spinner.getAdapter()).getPosition(new SpinnerItem(confirmedBy));
                confirmedby_spinner.setSelection(positonConfirmedBy);
            }


            if (addressConfirmed) {

                nameofemployer_edittext.setText(map.get(OVerificationId.nameofEmployer));
                personmet_office_edittext.setText(map.get(OVerificationId.personMet));
                personment_designation_edittext.setText(map.get(OVerificationId.personMetDesignation));
                addressof_employer_edittext.setText(map.get(OVerificationId.addressofEmployer));
                phone_office_edittext.setText(map.get(OVerificationId.phone));
                extension_edittext.setText(map.get(OVerificationId.extension));
                residence_edittext.setText(map.get(OVerificationId.residenceNo));
                mobile_edittext.setText(map.get(OVerificationId.mobileNo));

                String board_seen = map.get(OVerificationId.companyBoardSeen);
                if(board_seen!=null){
                    boolean boardseen = board_seen.equalsIgnoreCase("True");
                    company_boardseen_radiogroup.check(boardseen ? R.id.yes_board_seen_radiobutton : R.id.no_board_seen_radiobutton);
                }

                String typeOfEmployer = map.get(OVerificationId.typeofEmployer);
                if (typeOfEmployer != null) {
                    int positionEmployerType = ((ArrayAdapter) typeofemployer_spinner.getAdapter()).getPosition(new SpinnerItem(typeOfEmployer));
                    typeofemployer_spinner.setSelection(positionEmployerType);
                }

                String natureOfBusiness = map.get(OVerificationId.natureofBusiness);
                if (natureOfBusiness != null) {
                    int positionBusinessNature = ((ArrayAdapter) natureof_business_spinner.getAdapter()).getPosition(new SpinnerItem(natureOfBusiness));
                    natureof_business_spinner.setSelection(positionBusinessNature);
                }

                lineof_business_edittext.setText(map.get(OVerificationId.lineofBusiness));
                yearof_establishment_edittext.setText(map.get(OVerificationId.yearsofEstablishment));

                String levelOfBusiness = map.get(OVerificationId.levelofBusinessActivity);
                if (levelOfBusiness != null) {
                    int positionBusinessLevel = ((ArrayAdapter) levelof_business_spinner.getAdapter()).getPosition(new SpinnerItem(levelOfBusiness));
                    levelof_business_spinner.setSelection(positionBusinessLevel);
                }

                noofemployee_see_edittext.setText(map.get(OVerificationId.noofEmployees));
                noofbranch_edittext.setText(map.get(OVerificationId.noofBranches));


                String officeAmbience = map.get(OVerificationId.officeAmbience);
                if (officeAmbience != null) {
                    int positonOfficeAmbience = ((ArrayAdapter) office_abmience_spinner.getAdapter()).getPosition(new SpinnerItem(officeAmbience));
                    office_abmience_spinner.setSelection(positonOfficeAmbience);
                }

                String typeOfLocality = map.get(OVerificationId.typeofLocality);
                if (typeOfLocality != null) {
                    int positionLocality = ((ArrayAdapter) locality_office_spinner.getAdapter()).getPosition(new SpinnerItem(typeOfLocality));
                    locality_office_spinner.setSelection(positionLocality);
                }

                area_edittext.setText(map.get(OVerificationId.area));
                nearest_landmark_edittext.setText(map.get(OVerificationId.nearestLandMark));

                String easeOfLocating = map.get(OVerificationId.easeofLocating);
                if (easeOfLocating != null) {
                    int positionEaseOfLocating = ((ArrayAdapter) easeof_locating_spinner.getAdapter()).getPosition(new SpinnerItem(easeOfLocating));
                    easeof_locating_spinner.setSelection(positionEaseOfLocating);
                }

                yearsofcurrent_employement_edittext.setText(map.get(OVerificationId.yearsofCurrentEmployment));


                String termsOfEmployement = map.get(OVerificationId.termsofEmployement);
                if (termsOfEmployement != null) {
                    int positionEmployementTerms = ((ArrayAdapter) termsof_employement_spinner.getAdapter()).getPosition(new SpinnerItem(termsOfEmployement));
                    termsof_employement_spinner.setSelection(positionEmployementTerms);
                }

                String grade = map.get(OVerificationId.grade);
                if (grade != null) {
                    int positionGrade = ((ArrayAdapter) grade_spinner.getAdapter()).getPosition(new SpinnerItem(grade));
                    grade_spinner.setSelection(positionGrade);
                }

                grade_edittext.setText(map.get(OVerificationId.otherGrade));
                current_salary_edittext.setText(map.get(OVerificationId.cuurentSalary));

                applicantage_office2_edittext.setText(map.get(OVerificationId.applicantAge));
                nameofcompany_office2_edittext.setText(map.get(OVerificationId.nameofCompany));
                establishmentyear_office2_edittext.setText(map.get(OVerificationId.estiblishmentYear));
                designation_office2_edittext.setText(map.get(OVerificationId.designation));
                phone_office2_edittext.setText(map.get(OVerificationId.phoneOffice));
                extension_office2_edittext.setText(map.get(OVerificationId.offExtension));


                String typeOfCompany = map.get(OVerificationId.typeofCompany);
                if (typeOfCompany != null) {
                    int positionCompanyType = ((ArrayAdapter) typeofcompany_spinner.getAdapter()).getPosition(new SpinnerItem(typeOfCompany));
                    typeofcompany_spinner.setSelection(positionCompanyType);
                }


                String natureOfCompany = map.get(OVerificationId.natureofCompany);
                if (natureOfCompany != null) {
                    int positionCompanyNature = ((ArrayAdapter) natureofcompany_office2_spinner.getAdapter()).getPosition(new SpinnerItem(natureOfCompany));
                    natureofcompany_office2_spinner.setSelection(positionCompanyNature);
                }

                noofemployees_office2_edittext.setText(map.get(OVerificationId.noofCompEmployee));
                noofbranches_office2_edittext.setText(map.get(OVerificationId.noofCompBranches));
                area_office2_edittext.setText(map.get(OVerificationId.compArea));
                nearestlandmark_office2_edittext.setText(map.get(OVerificationId.compLandMark));

            } else {
                String reason = map.get(OVerificationId.notConfirmedType);
                if (reason != null) {
                    boolean untraceable = reason.equalsIgnoreCase("U");
                    reason_radiogruop.check(untraceable ? R.id.untraceable_reason_not_confirmed_radiobutton : R.id.mismatch_reason_not_confirmed_radiobutton);
                }



                address_belongto_edittext.setText(map.get(OVerificationId.toWhomAddressBelongs));

                reason_edittext.setText(map.get(OVerificationId.reasonNotConfirmed));
                resultOfCalling_edittex.setText(map.get(OVerificationId.resultofCalling));

                String notConfirmedLocality = map.get(OVerificationId.notConfirmedLocality);
                if (notConfirmedLocality != null) {
                    int positionNotConfirmedLocality = ((ArrayAdapter) locality_spinner_not.getAdapter()).getPosition(new SpinnerItem(notConfirmedLocality));
                    locality_spinner_not.setSelection(positionNotConfirmedLocality);
                }

            }


        }



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
        String lastName = "" ;
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

           /* if(name.equalsIgnoreCase(lastName)){
                //we are repeating
                String checked = input.attr("checked");
                if(checked.equalsIgnoreCase("checked")){
                    map.put(name,value);
                }
          *//*  }

            if(name.equalsIgnoreCase("ResiStatus") || name.equalsIgnoreCase("OfficeStatus")){
                String checked = input.attr("checked");
                if(checked.equalsIgnoreCase("checked")){
                    map.put(name,value);
                }*//*
            }else map.put(input.attr("name"),input.val());*/
            //Log.e(LOG_TAG,input.id()+" -> "+input.val());
            lastName = name ;
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
        String format = "VISIT DONE AND MET WITH MR. %s (%s) WHO CONFIRMED THE EMPLOYMENT EXISTENCE OF APPLICANT; AS PER HIM APPLICANT IS WORKING WITH %s FROM LAST %s YEARS AS (%s IN %s); APPROXIMATE SALARY IS RS. %s; COMPANY IS A %s; TOTAL EMPLOYEE %s WAS SEEN; CO-WORKER CHECK DONE WITH MR. %s ";
        String personMet = String.valueOf(personmet_office_edittext.getText());
        String designation = String.valueOf(personment_designation_edittext.getText());
        String employer = String.valueOf(nameofemployer_edittext.getText());
        String sinceLast = String.valueOf(yearsofcurrent_employement_edittext.getText());
        String designationApplicant = String.valueOf(grade_edittext.getText());
        String terms = ((SpinnerItem) termsof_employement_spinner.getSelectedItem()).getText();
        String salary = String.valueOf(current_salary_edittext.getText());
        String companyType = ((SpinnerItem)typeofemployer_spinner.getSelectedItem()).getText();
        String totalEmployee = String.valueOf(noofemployee_see_edittext.getText());
        String collegue = String.valueOf(neighbour1_edittext.getText());

        String output = String.format(format,personMet,designation,employer,sinceLast,designationApplicant,terms,salary,companyType,totalEmployee,collegue);
        return output;
    }
}
