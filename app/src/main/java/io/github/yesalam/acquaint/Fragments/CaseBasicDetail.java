package io.github.yesalam.acquaint.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Activity.IndiCaseActivity;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.CaseBasicId;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Listener.HaveClickListener;
import io.github.yesalam.acquaint.Util.Id.OfficeId;
import io.github.yesalam.acquaint.Util.Id.ResidentialId;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Request;

import static io.github.yesalam.acquaint.Util.Maps.getBranchHash;
import static io.github.yesalam.acquaint.Util.Maps.getClientHash;
import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.SpinnerLists.*;

/**
 * Created by yesalam on 08-06-2017.
 */

public class CaseBasicDetail extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.office_needVerification_row)
    TableRow needVerificatin_office_row;
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

    String LOG_TAG = "CaseBasicDetail" ;
    boolean spinnerupdated=false ;
    String branch;
    String contact;
    SwipeRefreshLayout refreshLayout;

    IndiCaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (IndiCaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_basic_detail,container,false);
        ButterKnife.bind(this,view);
        prepareForm();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if(activity.formMap!=null){
            update(activity.formMap);
        }else refreshLayout.setRefreshing(true);

        return view;
    }


   /* private void loadData(){
        Log.e(LOG_TAG, "loading case page");
        final String CASE_EDIT_URL = "/Users/Cases/Edit/" + activity.caseid;

        Log.e(LOG_TAG, "loading url " + ACQUAINT_URL+CASE_EDIT_URL);
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL+CASE_EDIT_URL)
                .build();

        WebHelper helper = WebHelper.getInstance(getContext());
        helper.requestCall(request,this);
    }*/


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
                if (val == 0) return;
                String jsonString = getBranchHash().get(val);
                spinnerUpdate(jsonString,contact,contact_person_spinner,contactadapter);
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
                if (val == 0) return;
                String jsonString = getClientHash().get(val);
                spinnerUpdate(jsonString,branch,branch_spinner,branchadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        adapter.addAll(getLoanTypes());
        loan_type_spinner.setAdapter(adapter);



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


        assignedto_office_spinner.setAdapter(assignedtoadapter);

        havepermanentaddress_radiobutton.setChecked(false);
        permanent_address_frame.setVisibility(View.GONE);
        HaveClickListener permanentHave = new HaveClickListener(permanent_address_frame);
        havepermanentaddress_radiobutton.setOnClickListener(permanentHave);


        assigneto_permanent_spinner.setAdapter(assignedtoadapter);

        investigaionstatusrow_permanent_tablerow.setVisibility(View.GONE);
        statusrow_office_tablerow.setVisibility(View.GONE);
    }


    private void spinnerUpdate(String jsonString,String value,Spinner spinner,ArrayAdapter adapter){
        try {
            JSONArray array = new JSONArray(jsonString);
            ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
            int positionBranch = 0 ;
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String id = object.getString("id").trim();
                String name = object.getString("name");
                if(id.equalsIgnoreCase(value.trim())) positionBranch = i ;
                list.add(new SpinnerItem(name, id));
            }
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
            if(value==null)return;
            spinner.setSelection(positionBranch);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void update(Map<String,String> map){
        refreshLayout.setRefreshing(false);
        spinnerupdated=true ;
        String client =  map.get(CaseBasicId.client);
        int positinclient = ((ArrayAdapter)client_spinner.getAdapter()).getPosition(new SpinnerItem(client));
        client_spinner.setSelection(positinclient);

        branch = map.get(CaseBasicId.branch);


        contact = map.get(CaseBasicId.contactPerson);


        String loantype =  map.get(CaseBasicId.loantype);
        int positinloantype = ((ArrayAdapter)loan_type_spinner.getAdapter()).getPosition(new SpinnerItem(loantype));
        loan_type_spinner.setSelection(positinloantype);

        pickup_date_edittext.setText(map.get(CaseBasicId.pickupDate));

        int radiobutton = CaseBasicId.isReVerification.equalsIgnoreCase("Yes")?R.id.radio_button_yes_reverification:R.id.radio_button_no_reverification;
        reverification_radiogroup.check(radiobutton);

        String pickup =  map.get(CaseBasicId.pickupBy);
        Log.e(LOG_TAG,map.get(CaseBasicId.pickupBy));
        int positionpickup = ((ArrayAdapter)pickupby_spinner.getAdapter()).getPosition(new SpinnerItem(pickup));
        pickupby_spinner.setSelection(positionpickup);

        loanamount_edittext.setText(map.get(CaseBasicId.loanAmount));
        loantenure_edittext.setText(map.get(CaseBasicId.loanTenure));
        applicationrefno_edittext.setText(map.get(CaseBasicId.applicationRefNo));
        status_textview.setText(map.get(CaseBasicId.status));
        email_senton_textview.setText(map.get(CaseBasicId.emailSentOn));

        name_resident_edittext.setText(map.get(ResidentialId.name));
        dob_edittext.setText(map.get(ResidentialId.dateOfBirth));
        pan_edittext.setText(map.get(ResidentialId.pan));


        int gender = map.get(ResidentialId.gender).equalsIgnoreCase("M")? R.id.radio_button_male_residential_detail:R.id.radio_button_female_residential_detail ;
        gender_radiogroup.check(gender);

        address_residential_edittext.setText(map.get(ResidentialId.address));
        city_residential_edittext.setText(map.get(ResidentialId.city));
        state_residential_edittxt.setText(map.get(ResidentialId.state));
        pin_residential_edittext.setText(map.get(ResidentialId.pin));
        email_residential_edittext.setText(map.get(ResidentialId.email));
        mobile_residential_edittext.setText(map.get(ResidentialId.mobile));
        phone_residential_edittext.setText(map.get(ResidentialId.phone));


        String assignedto =  map.get(ResidentialId.assignedTo);
        int positionassignedto = ((ArrayAdapter)assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_residential_spinner.setSelection(positionassignedto);

        investigationstatus_residential.setText(map.get(ResidentialId.status));

        boolean haveCompany = !map.get(OfficeId.companyAddressId).equalsIgnoreCase("0");
        havecompany_radiobutton.setChecked(haveCompany);
        if(haveCompany){
            applicant_office_frame.setVisibility(View.VISIBLE);
            needVerificatin_office_row.setVisibility(View.GONE);
            statusrow_office_tablerow.setVisibility(View.VISIBLE);
        }

        companyname_office_edittext.setText(map.get(OfficeId.companyName));
        address_office_edittext.setText(map.get(OfficeId.address));
        city_office_edittext.setText(map.get(OfficeId.city));
        state_office_edittext.setText(map.get(OfficeId.state));
        mobile_office_edittext.setText(map.get(OfficeId.mobile));
        phone_office_edittext.setText(map.get(OfficeId.phone));
        status_office_textview.setText(map.get(OfficeId.status));

        String assignedtooffice =  map.get(ResidentialId.assignedTo);
        int positionassignedtooffice = ((ArrayAdapter)assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedtooffice));
        assignedto_office_spinner.setSelection(positionassignedtooffice);



    }



   /* private Map<String,String> parseAData(String html){
        Map<String,String> map = new HashMap<>();

        Document document = Jsoup.parse(html);
        String emailsent = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody > tr:nth-child(6) > td.table-number > table > tbody > tr > td:nth-child(3)").text();
        String residence = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > aside > table > tbody > tr:nth-child(7) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(8) > td:nth-child(4)").text();
        String office = document.select("#trOfficeAddress > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        String permanent = document.select("#trPerAddress > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td:nth-child(2)").text();
        map.put("emailsentstatus",emailsent);
        map.put("ResidenceStatus",residence);
        map.put("officestatus",office);
        map.put("permanentstatus",permanent);

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for(Element input:elements){
            map.put(input.attr("name"),input.val());
            //Log.e(LOG_TAG,input.attr("name")+" -> "+input.val());
        }

        Elements selects = form.getElementsByTag("select");
        for(Element select:selects){
            String id = select.id();
            //Log.e(LOG_TAG,id);
            try{
                String value = select.getElementsByAttributeValue("selected","selected").first().text();
                //Log.e(LOG_TAG,value);
                map.put(id,value);
            }catch (NullPointerException npe){
                npe.printStackTrace();
            }
        }

        return map;
    }*/

   /* @Override
    public void onPositiveResponse(final String htmldoc) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e(LOG_TAG,"CaseEdit loaded");
                //progressBar.setVisibility(View.GONE);
                Map map = parseAData(htmldoc);
                update(map);

            }
        });
    }*/

    @Override
    public void onRefresh() {
        activity.loadBasicDetailPage();
    }
}
