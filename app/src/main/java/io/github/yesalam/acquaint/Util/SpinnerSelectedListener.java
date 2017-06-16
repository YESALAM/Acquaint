package io.github.yesalam.acquaint.Util;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.yesalam.acquaint.Pojo.SpinnerItem;

import static io.github.yesalam.acquaint.Util.Util.getBranchHash;
import static io.github.yesalam.acquaint.Util.Util.getClientHash;

/**
 * Created by yesalam on 16-06-2017.
 */

public class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
    private boolean isClient;
    private Spinner nextSpinner;
    private String nextDefault;

    public SpinnerSelectedListener(boolean isClient, Spinner nextSpinner, String nextDefault) {

        this.isClient = isClient;
        this.nextSpinner = nextSpinner;
        this.nextDefault = nextDefault;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
        int val = Integer.parseInt(item.getValue());
        if (val == 0) return;
        String jsonString;
        if (isClient) jsonString = getClientHash().get(val);
        else jsonString = getBranchHash().get(val);
        try {
            JSONArray array = new JSONArray(jsonString);
            ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String value = object.getString("id");
                String name = object.getString("name");
                list.add(new SpinnerItem(name, value));
            }
            ((ArrayAdapter) nextSpinner.getAdapter()).clear();
            ((ArrayAdapter) nextSpinner.getAdapter()).addAll(list);
            if (nextDefault == null) return;

            int positionNextDefault = ((ArrayAdapter) nextSpinner.getAdapter()).getPosition(new SpinnerItem(nextDefault));
            //Log.e(LOG_TAG,"branch position"+positionbranch);
            //branch_spinner.setOnItemSelectedListener(new SpinnerSelectedListener(true,false,contact_person_spinner.getAdapter()));
            nextSpinner.setSelection(positionNextDefault);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setNextDefault(String nextDefault) {
        this.nextDefault = nextDefault;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
