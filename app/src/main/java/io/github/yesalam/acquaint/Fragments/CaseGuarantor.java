package io.github.yesalam.acquaint.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.DateClick;
import io.github.yesalam.acquaint.Util.HaveClickListener;

import static io.github.yesalam.acquaint.Util.SpinnerLists.getAssignedToType;

/**
 * Created by yesalam on 09-06-2017.
 */

public class CaseGuarantor extends Fragment {

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
    @BindView(R.id.need_verification_guarantor_residential_radiobutton)
    CheckBox needverificaton_residential_radiobutton;
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
    @BindView(R.id.need_verification_guarantor_office_details_radiobutton)
    CheckBox needverification_guarantoroffice_radiobutton;
    @BindView(R.id.assigned_to_guarantor_office_details_spinner)
    Spinner assignedto_guarantoroffice_spinner;

    @BindView(R.id.save_guarantor)
    Button save_button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_guarantor,container,false);
        ButterKnife.bind(this,view);

        initForm();
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
}
