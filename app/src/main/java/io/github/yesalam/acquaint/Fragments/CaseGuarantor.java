package io.github.yesalam.acquaint.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Activity.IndiCaseActivity;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.GuarantorId;
import io.github.yesalam.acquaint.Util.Id.OfficeId;
import io.github.yesalam.acquaint.Util.Id.ResidentialId;
import io.github.yesalam.acquaint.Util.Listener.DateClick;
import io.github.yesalam.acquaint.Util.Listener.HaveClickListener;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.MultipartBody;
import okhttp3.Request;

import static io.github.yesalam.acquaint.Util.SpinnerLists.getAssignedToType;
import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.WebHelper.NO_CONNECTION;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CaseGuarantor extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "CaseGuarantor";
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
    IndiCaseActivity activity;
    ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (IndiCaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_guarantor, container, false);
        ButterKnife.bind(this, view);
        /*refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        initForm();
        if (activity.guarMap != null) {
            update(activity.guarMap);
        }//else refreshLayout.setRefreshing(true);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting Data");

        return view;
    }

    private void initForm() {
        if (!have_guarantor_radiobutton.isChecked())
            guarantor_residential_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantorListener = new HaveClickListener(guarantor_residential_frame);
        have_guarantor_radiobutton.setOnClickListener(haveguarantorListener);

        dob_residential_edittext.setOnClickListener(new DateClick(getContext()));

        ArrayAdapter<SpinnerItem> assignedto_gurantor = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        assignedto_gurantor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantor.addAll(getAssignedToType(getActivity()));
        assignedto_residential_spinner.setAdapter(assignedto_gurantor);

        if (!haveguarantoroffice_radiobutton.isChecked())
            guarantor_office_frame.setVisibility(View.GONE);
        HaveClickListener haveguarantoroffice = new HaveClickListener(guarantor_office_frame);
        haveguarantoroffice_radiobutton.setOnClickListener(haveguarantoroffice);


        ArrayAdapter<SpinnerItem> assignedto_gurantoroffice = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item);
        assignedto_gurantoroffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedto_gurantoroffice.addAll(getAssignedToType(getActivity()));
        assignedto_guarantoroffice_spinner.setAdapter(assignedto_gurantoroffice);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        if (validate()) areYouSure();
    }

    public void areYouSure() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        WebHelper webHelper = WebHelper.getInstance(getContext());
                        if (webHelper.isConnected()) {
                            progressDialog.show();
                            oktoSubmit();
                        } else {
                            //cacheData();
                            //finish();
                        }
                        //finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }

    private void oktoSubmit() {
        //map.remove("img_src");
        if(activity.guarMap==null) {
            Toast.makeText(activity, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return;
        }
        Map map = activity.guarMap;
        map.remove("action:ChangeTab1");
        map.remove("action:ChangeTab2");
        map.remove("action:Cancel");
        map.remove("action:ChangeTab0");
        map.remove("");
        Map<String, String> valuesMap = getValues();
        map.putAll(valuesMap);
        //logId(map);
        submitMultiPart(map);

    }


    public void submitMultiPart(Map<String, String> map) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : map.keySet()) {
            requestBodyBuilder.addFormDataPart(key, map.get(key));
        }

        MultipartBody requestBody = requestBodyBuilder.build();

        String CASE_EDIT = "/Users/Cases/Edit/" + activity.caseid;

        Request request = new Request.Builder()
                .url(ACQUAINT_URL + CASE_EDIT)
                .post(requestBody)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + CASE_EDIT + " submitting data");

        WebHelper.getInstance(getContext()).requestCall(request, new WebHelper.CallBack() {
            @Override
            public void onPositiveResponse(String html) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Data Submited", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onNegativeResponse(int code) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //cacheData();
                        Toast.makeText(getContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //finish();
                    }
                });
            }
        });

    }

    private void logId(Map<String, String> map) {
        for (String key : map.keySet()) {
            Log.e(LOG_TAG, key + " : " + map.get(key));
        }
    }

    public void update(Map<String, String> guarMap) {
        //logIt(guarMap);
        //refreshLayout.setRefreshing(false);
        logId(guarMap);
        String haveGuar = guarMap.get(GuarantorId.haveGuarantor);
        if (haveGuar == null) return;
        if (haveGuar.equalsIgnoreCase("true")) {
            updateResidential(guarMap);
            String haveOffice = guarMap.get(GuarantorId.guarHaveOfficeAddress);
            if (haveOffice.equalsIgnoreCase("true")) updateOffice(guarMap);

        }


    }

    private void updateOffice(Map<String, String> guarMap) {
        guarantor_office_frame.setVisibility(View.VISIBLE);
        haveguarantoroffice_radiobutton.setChecked(true);
        companyname_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyName));
        address_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyAddress));
        city_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyCity));
        state_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyState));
        mobile_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyMobile));
        phone_guarantoroffice_edittext.setText(guarMap.get(GuarantorId.guarCompanyPhone));

        needVerification_office_row.setVisibility(View.GONE);

        status_office_textview.setText(guarMap.get(GuarantorId.guarOfficeStatus));
        status_office_row.setVisibility(View.VISIBLE);

        String assignedto = guarMap.get(GuarantorId.guarOfficeAssignedTo);
        int positionassignedto = ((ArrayAdapter) assignedto_guarantoroffice_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_guarantoroffice_spinner.setSelection(positionassignedto);


    }

    private void updateResidential(Map<String, String> guarMap) {
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

        if (guarMap.get(GuarantorId.guarStatus).trim().equalsIgnoreCase("")) {
            needverificaton_residential_radiobutton.setVisibility(View.VISIBLE);
            String needVerifi = guarMap.get(GuarantorId.guarNeedsVerification);
            if(needVerifi.equalsIgnoreCase("true")) needverificaton_residential_radiobutton.setChecked(true);
        } else {
            status_residential_textview.setText(guarMap.get(GuarantorId.guarStatus));
            status_row_residential.setVisibility(View.VISIBLE);
            needVerification_residential_row.setVisibility(View.GONE);
        }


        String gender = guarMap.get(GuarantorId.guarGender);
        int genderId = gender.equalsIgnoreCase("F") ? R.id.radio_button_female_guarantor_residential_detail : R.id.radio_button_male_guarantor_residential_detail;
        gender_residential_radiogroup.check(genderId);

        String assignedto = guarMap.get(GuarantorId.guarAssignedTo);
        int positionassignedto = ((ArrayAdapter) assignedto_residential_spinner.getAdapter()).getPosition(new SpinnerItem(assignedto));
        assignedto_residential_spinner.setSelection(positionassignedto);


    }

    @Override
    public void onRefresh() {
        activity.loadGuarantor();
    }

    private Map<String, String> getValues() {
        Map<String, String> map = new HashMap<>();
        if (have_guarantor_radiobutton.isChecked()) {
            map.put(GuarantorId.haveGuarantor, "true");

            map.put(GuarantorId.guarName, String.valueOf(name_residential_edittext.getText()));
            map.put(GuarantorId.guarDOB, String.valueOf(dob_residential_edittext.getText()));
            map.put(GuarantorId.guarPAN, String.valueOf(pan_residential_edittext.getText()));

            String gender = gender_residential_radiogroup.getCheckedRadioButtonId() == R.id.radio_button_female_guarantor_residential_detail ? "F" : "M";
            map.put(GuarantorId.guarGender, gender);

            map.put(GuarantorId.guarAddress, String.valueOf(address_residential_editext.getText()));
            map.put(GuarantorId.guarCity, String.valueOf(city_residential_edittext.getText()));
            map.put(GuarantorId.guarState, String.valueOf(state_residential_edittext.getText()));
            map.put(GuarantorId.guarPin, String.valueOf(pin_residential_edittext.getText()));
            map.put(GuarantorId.guarEMail, String.valueOf(email_residential_edittext.getText()));
            map.put(GuarantorId.guarMobile, String.valueOf(mobile_residential_editext.getText()));
            map.put(GuarantorId.guarPhone, String.valueOf(phone_residential_edittex.getText()));

            if (needverificaton_residential_radiobutton.isChecked()) {
                map.put(GuarantorId.guarNeedsVerification, "true");
                map.put(GuarantorId.guarAssignedTo, ((SpinnerItem) assignedto_residential_spinner.getSelectedItem()).getValue());
            }
            if (haveguarantoroffice_radiobutton.isChecked()) {
                map.put(GuarantorId.guarHaveOfficeAddress, "true");
                map.put(GuarantorId.guarCompanyName, String.valueOf(companyname_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyAddress, String.valueOf(address_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyCity, String.valueOf(city_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyState, String.valueOf(state_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyMobile, String.valueOf(mobile_guarantoroffice_edittext.getText()));
                map.put(GuarantorId.guarCompanyPhone, String.valueOf(phone_guarantoroffice_edittext.getText()));

                if (needverification_guarantoroffice_radiobutton.isChecked()) {
                    map.put(GuarantorId.guarCompanyNeedsVerification, "true");
                    map.put(GuarantorId.guarOfficeAssignedTo, ((SpinnerItem) assignedto_guarantoroffice_spinner.getSelectedItem()).getValue());
                }

            }


        }

        return map;
    }

    private boolean validate() {



        String pickupdate = String.valueOf(name_residential_edittext.getText());
        if (pickupdate.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Pickup Date is empty", Toast.LENGTH_LONG).show();
            return false;
        }


        if (have_guarantor_radiobutton.isChecked()) {
            if (needverificaton_residential_radiobutton.isChecked()) {
                try {
                    int assignedTooffice = Integer.parseInt(((SpinnerItem) assignedto_residential_spinner.getSelectedItem()).getValue());
                    if (assignedTooffice == 0) {
                        Toast.makeText(getContext(), "AssignedTo is Missing", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (NumberFormatException nfe) {
                }
            }
        }



        if (haveguarantoroffice_radiobutton.isChecked()) {
            if (needverification_guarantoroffice_radiobutton.isChecked()) {
                try {
                    int assignedTooffice = Integer.parseInt(((SpinnerItem) assignedto_guarantoroffice_spinner.getSelectedItem()).getValue());
                    if (assignedTooffice == 0) {
                        Toast.makeText(getContext(), "AssignedTo Office is Missing", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (NumberFormatException nfe) {
                }
            }
        }




        return true;
    }

}
