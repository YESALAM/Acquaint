package io.github.yesalam.acquaint.Util.Listener;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;


/**
 * Created by yesalam on 7/24/17.
 */

public class TimeClick implements View.OnClickListener {

    private Context context;

    public TimeClick(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(final View v) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        new TimePickerDialog(context, new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ((EditText) v).setText(hourOfDay + ":" + minute + ":" + "00");
            }
        },
                hour,
                minute,
                false//        DateFormat.is24HourFormat(context)
        ).show();
    }
}
