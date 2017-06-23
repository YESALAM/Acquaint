package io.github.yesalam.acquaint.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Activity.IndiCaseActivity;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.GuarantorId;
import io.github.yesalam.acquaint.Util.Id.ResidentialId;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Listener.HaveClickListener;

import static io.github.yesalam.acquaint.Util.SpinnerLists.getAssignedToType;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CaseGuarantor extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.have_guarantor_residential_radiobutton)
    CheckBox have_guarantor_radiobutton;
    @BindView(R.id.guarantor_residential_detail_frame)
    LinearLayout guarantor_residential_frame;
    @BindView(R.id.name_guarantor_residential_detail_edittext)
    EditText name_residential_edittext;
    @BindView(R.id.dateofbirth_guarantor_residential_detail_edittext)
    EditText dob_residential_edittext;
    @BindView(R.id.pan_guarantor_residential_detail_edittext)
    EditText pan_residential_edittext;
    @BindView(R.id.radio_group_gender_guarantor_residential_detail)
    RadioGroup gender_residential_radiogroup;
    @BindView(R.id.address_guarantor_residential_detail_edittext)
    EditText address_residential_editext;
    @BindView(R.id.city_guarantor_residential_detail_edittext)
    EditText city_residential_edittext;
    @BindView(R.id.state_guarantor_residential_detail_edittext)
    EditText state_residential_edittext;
    @BindView(R.id.pin_guarantor_residential_detail_edittext)
    EditText pin_residential_edittext;
    @BindView(R.id.email_guarantor_residential_detail_edittext)
    EditText email_residential_edittext;
    @BindView(R.id.mobile_guarantor_residential_detail_edittext)
    EditText mobile_residential_editext;
    @BindView(R.id.phon_guarantor_residential_detail_edittext)
    EditText phone_residential_edittex;
    @BindView(R.id.needVerification_residential_row)
    TableRow needVerification_residential_row;
    @BindView(R.id.need_verification_guarantor_residential_radiobutton)
    CheckBox needverificaton_residential_radiobutton;
    @BindView(R.id.status_residential_row)
    TableRow status_row_residential;
    @BindView(R.id.status_residential_guarantor_textview)
    TextView status_residential_textview;
    @BindView(R.id.assigned_to_guarantor_residential_detail_spinner)
    Spinner assignedto_residential_spinner;

    //RAdio button have office address
    @BindView(R.id.have_guarantor_office_address)
    CheckBox haveguarantoroffice_radiobutton;

    //Applicant_office_detail
    @BindView(R.id.guarantor_office_details_frame)
    FrameLayout guarantor_office_frame;
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
    @BindView(R.id.needVerification_office_row)
    TableRow needVerification_office_row;
    @BindView(R.id.need_verification_guarantor_office_details_radiobutton)
    CheckBox needverification_guarantoroffice_radiobutton;
    @BindView(R.id.status_office_row_guarantor)
    TableRow status_office_row;
    @BindView(R.id.status_office_guarantor)
    TextView status_office_textview;
    @BindView(R.id.assigned_to_guarantor_office_details_spinner)
    Spinner assignedto_guarantoroffice_spinner;

    @BindView(R.id.save_guarantor)
    Button save_button;

    //SwipeRefreshLayout refreshLayout;
    IndiCaseActivity activity ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (IndiCaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_guarantor,container,false);
        ButterKnife.bind(this,view);
        /*refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        initForm();
        if(activity.guarMap!=null){
            update(activity.guarMap);
        }//else refreshLayout.setRefreshing(true);



        return view;
    }

    private void initForm(){
        guarantor_residential_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantorListener = new HaveClickListener(guarantor_residential_frame);
        have_guarantor_radiobutton.setOnClickListener(haveguarantorListener);

        dob_residential_edittext.setOnClickListener(new DateClick(getContext()));

        ArrayAdapter<SpinnerItem> assignedto_gurantor = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        assignedto_gurantor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantor.addAll(getAssignedToType());
        assignedto_residential_spinner.setAdapter(assignedto_gurantor);

        guarantor_office_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantoroffice = new HaveClickListener(guarantor_office_frame);
        haveguarantoroffice_radiobutton.setOnClickListener(haveguarantoroffice);


        ArrayAdapter<SpinnerItem> assignedto_gurantoroffice = new ArrayAdapter<SpinnerItem>(getContext(),android.R.layout.simple_spinner_item);
        assignedto_gurantoroffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantoroffice.addAll(getAssignedToType());
        assignedto_guarantoroffice_spinner.setAdapter(assignedto_gurantoroffice);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save(){}


    private void logIt(Map<String,String> map){
        for(String key:map.keySet()){
            Log.e("Guarantor",key+"->"+map.get(key));
        }
    }

    public void update(Map<String, String> guarMap) {
        //logIt(guarMap);
        //refreshLayout.setRefreshing(false);
        String haveGuar = guarMap.get(GuarantorId.haveGuarantor);
        if(haveGuar==null) return;
        if(haveGuar.equalsIgnoreCase("true")){
          updateResidential(guarMap);
            String haveOffice = guarMap.get(GuarantorId.guarHaveOfficeAddress);
            if(haveOffice.equalsIgnoreCase("true")) updateOffice(guarMap);

        }


    }

    private void updateOffice(Map<String, String> guarMap) {
        haveguarantoroffice_radiobutton.setChecked(true);
        companyname_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyName));
        address_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyAddress));
        city_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyCity));
        state_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyState));
        mobile_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyMobile));
        phone_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyPhone));

        needVerification_office_row.setVisibility(View.GONE);

        status_office_textview.setText(guarMap.get(GuarantorId.guarCompanyStatus));
        status_office_row.setVisibility(View.VISIBLE);

        String assignedto =  guarMap.get(GuarantorId.guarOfficeAssignedTo);
        int positionassignedto = ((ArrayAdapter)assignedto_guarantoroffice_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_guarantoroffice_spinner.setSelection(positionassignedto);




    }

    private void updateResidential(Map<String,String> guarMap){
        have_guarantor_radiobutton.setChecked(true);
        guarantor_residential_frame.setVisibility(View.VISIBLE);

        name_residential_edittext.setText(guarMap.get(GuarantorId.guarName));
        dob_residential_edittext.setText(guarMap.get(GuarantorId.guarDOB));
        pan_residential_edittext.setText(guarMap.get(GuarantorId.guarPAN));
        address_residential_editext.setText(guarMap.get(GuarantorId.guarAddress));
        city_residential_edittext.setText(guarMap.get(GuarantorId.guarCity));
        state_residential_edittext.setText(guarMap.get(GuarantorId.guarState));
        pin_residential_edittext.setText(guarMap.get(GuarantorId.guarPin));
        email_residential_edittext.setText(guarMap.get(GuarantorId.guarEMail));
        mobile_residential_editext.setText(guarMap.get(GuarantorId.guarMobile));
        phone_residential_edittex.setText(guarMap.get(GuarantorId.guarPhone));
        status_residential_textview.setText(guarMap.get(GuarantorId.guarStatus));
        status_row_residential.setVisibility(View.VISIBLE);

        needVerification_residential_row.setVisibility(View.GONE);


        String gender = guarMap.get(GuarantorId.guarGender);
        int genderId = gender.equalsIgnoreCase("F")?R.id.radio_button_female_guarantor_residential_detail:R.id.radio_button_male_guarantor_residential_detail ;
        gender_residential_radiogroup.check(genderId);

        String assignedto =  guarMap.get(GuarantorId.guarAssignedTo);
        int positionassignedto = ((ArrayAdapter)assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_residential_spinner.setSelection(positionassignedto);



    }

    @Override
    public void onRefresh() {
        activity.loadGuarantor();
    }

    public void negativeResponse(int code) {
        switch (code) {
            case NO_CONNECTION:
                Toast.makeText(activity, "Connection not Available", Toast.LENGTH_SHORT).show();
                //refreshLayout.setRefreshing(false);
                break;
        }
    }
}
