package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Listener.TimeClick;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.Util.TeleId;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.Call;
import okhttp3.Request;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getRelationType;
import static io.github.yesalam.acquaint.Util.SpinnerLists.getStatusType;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 10-06-2017.
 */

public class TeleVerificationDialog extends Activity implements WebHelper.CallBack, SwipeRefreshLayout.OnRefreshListener {

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



    String LOG_TAG = "TeleVerificationDialog" ;
    String investigationId ;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_televerification);
        Intent intent = getIntent();
        investigationId = intent.getStringExtra("investigationid");
        ButterKnife.bind(this);
        initForm();

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        loadData();

    }

    private void initForm(){
        ArrayAdapter<SpinnerItem> relation_adapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        relation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation_adapter.addAll(getRelationType());
        relation_residence_spinner.setAdapter(relation_adapter);
        relation_office_spinner.setAdapter(relation_adapter);

        firstcallingdate_office_edittext.setOnClickListener(new DateClick(this));
        firstcallingdate_residence_edittext.setOnClickListener(new DateClick(this));
        secondcallingdate_office_edittext.setOnClickListener(new DateClick(this));
        secondcallingdate_residence_edittext.setOnClickListener(new DateClick(this));
        thirdcallingdate_office_edittext.setOnClickListener(new DateClick(this));
        thirdcallingdate_residence_edittext.setOnClickListener(new DateClick(this));

        firstcallingtime_office_edittext.setOnClickListener(new TimeClick(this));
        firstcallingtime_residence_edittext.setOnClickListener(new TimeClick(this));
        secondcallingtime_office_edittext.setOnClickListener(new TimeClick(this));
        secondcallingtime_residence_edittext.setOnClickListener(new TimeClick(this));
        thiredcallingtime_office_edittext.setOnClickListener(new TimeClick(this));
        thiredcallingtime_residence_edittext.setOnClickListener(new TimeClick(this));



        ArrayAdapter<SpinnerItem> status_adapter = new ArrayAdapter<SpinnerItem>(this,android.R.layout.simple_spinner_item);
        status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_adapter.addAll(getStatusType());
        status_residence_spinner.setAdapter(status_adapter);
        status_office_spinner.setAdapter(status_adapter);

        title_textview.setText("Tele Verification "+investigationId);
    }

    private void update(Map<String,String> map){
        refreshLayout.setRefreshing(false);
        caseid_textview.setText(map.get(TeleId.caseId));
        applicatiorefno_textview.setText(map.get(TeleId.applicationRefNo));
        name_textview.setText(map.get(TeleId.name));
        applicantname_textview.setText(map.get(TeleId.applicantName));
        coapplicant_textview.setText(map.get(TeleId.co_Applicant));
        residenceaddress_textview.setText(map.get(TeleId.residenceAddress));
        mobileresidence_textview.setText(map.get(TeleId.mobile));

        personspoken_residence_edittext.setText(map.get(TeleId.personSpokenTo_residence));

        String relationResidence =  map.get(TeleId.relation_residence);
        if(relationResidence!=null){
            int positionresidence = ((ArrayAdapter)relation_residence_spinner.getAdapter()).getPosition(new SpinnerItem(relationResidence));
            relation_residence_spinner.setSelection(positionresidence);
        }



        firstcallingdate_residence_edittext.setText(map.get(TeleId.fdate_residence));
        firstcallingtime_residence_edittext.setText(map.get(TeleId.ftime_residence));
        secondcallingdate_residence_edittext.setText(map.get(TeleId.sdate_residence));
        secondcallingtime_residence_edittext.setText(map.get(TeleId.stime_residence));
        thirdcallingdate_residence_edittext.setText(map.get(TeleId.tdate_residence));
        thiredcallingtime_residence_edittext.setText(map.get(TeleId.ttime_residence));
        remark_residence_edittext.setText(map.get(TeleId.remark_residence));

        String statusResidence =  map.get(TeleId.status_residence);
        if(statusResidence!=null){
            int positionstatus = ((ArrayAdapter)status_residence_spinner.getAdapter()).getPosition(new SpinnerItem(statusResidence));
            status_residence_spinner.setSelection(positionstatus);
        }

        companyname_textview.setText(map.get(TeleId.companyName));
        companyaddress_textview.setText(map.get(TeleId.address_office));
        mobile_office_textview.setText(map.get(TeleId.mobile_office));

        personspoken_office_edittext.setText(map.get(TeleId.personSpokenTo_office));
        designation_office_edittext.setText(map.get(TeleId.designation));

        String relationoffice =  map.get(TeleId.relation_office);
        if(relationoffice!=null){
            int officerelation = ((ArrayAdapter)relation_office_spinner.getAdapter()).getPosition(new SpinnerItem(relationoffice));
            relation_office_spinner.setSelection(officerelation);
        }

        firstcallingdate_office_edittext.setText(map.get(TeleId.fdate_office));
        firstcallingtime_office_edittext.setText(map.get(TeleId.ftime_office));
        secondcallingdate_office_edittext.setText(map.get(TeleId.sdate_office));
        secondcallingtime_office_edittext.setText(map.get(TeleId.stime_office));
        thirdcallingdate_office_edittext.setText(map.get(TeleId.tdate_office));
        thiredcallingtime_office_edittext.setText(map.get(TeleId.ttime_office));
        remark_office_edittext.setText(map.get(TeleId.remark_office));

        String statusoffice =  map.get(TeleId.status_office);
        if(statusoffice!=null){
            int officestatus = ((ArrayAdapter)status_office_spinner.getAdapter()).getPosition(new SpinnerItem(statusoffice));
            status_office_spinner.setSelection(officestatus);
        }




    }

    private void loadData(){
        refreshLayout.setRefreshing(true);
        String TELE_VERIFICATION_DETAIL = "/Users/Verifications/TeleVerification/"+investigationId;
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL+TELE_VERIFICATION_DETAIL)
                .build();

        WebHelper.getInstance(this).requestCall(request,this);
    }

    public void cancel(View view){finish();}

    public void save(View view){
    }

    @Override
    public void onPositiveResponse(String htmldoc) {
        final Map map = parseData(htmldoc);
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
                Snackbar.make(title_textview, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                        .show(); // Don’t forget to show!
                break;


        }
    }

    private boolean validate() {

        String status = ((SpinnerItem) status_residence_spinner.getSelectedItem()).getValue();
        try {
            int sts = Integer.parseInt(status);

            Toast.makeText(this, "Please select status", Toast.LENGTH_SHORT).show();
            return false;
        } catch (NumberFormatException nfe) {
            //map.put(OVerificationId.status,recommendation);
        }


        return true;
    }

    private Map<String, String> parseData(String html){
        Map<String,String> map = new HashMap<>();

        Document document = Jsoup.parse(html);
        String applicantName = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(3) > td.table-number.Impair").text();
        String coapplicant = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(5) > td.table-number.Impair").text();
        String companyName = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(7) > td.table-number > label").text();
        String companyAddress = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(8) > td.table-number > label").text();
        String companyMobile = document.select("#body > section > form > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(9) > td.table-number").text();


        map.put("applicantName",applicantName);
        map.put("coApplicantName",coapplicant);
        map.put("comapnyName",companyName);
        map.put("companyAddress",companyAddress);
        map.put("companyMobile",companyMobile);



        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for(Element input:elements){
            String name = input.attr("name");
            String value =input.attr("value");

            if(name.equalsIgnoreCase("ResiStatus") || name.equalsIgnoreCase("OfficeStatus")){
                String checked = input.attr("checked");
                if(checked.equalsIgnoreCase("checked")){
                    map.put(name,value);
                }
            }else map.put(input.attr("name"),input.val());
            //Log.e(LOG_TAG,input.id()+" -> "+input.val());
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


        return map;
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
