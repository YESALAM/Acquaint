package io.github.yesalam.acquaint.Util.Listener;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.github.yesalam.acquaint.Activity.CreateCaseDialog;
import okhttp3.Cookie;

/**
 * Created by yesalam on 14-06-2017.
 */

public class DateClick implements View.OnClickListener {
    Context context;
    Calendar myCalendar;


    public DateClick(Context context){
        this.context = context;
        myCalendar = Calendar.getInstance();
    }

    @Override
    public void onClick(final View v) {
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                ((EditText)v).setText(sdf.format((myCalendar.getTime())));
            }
        },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}
