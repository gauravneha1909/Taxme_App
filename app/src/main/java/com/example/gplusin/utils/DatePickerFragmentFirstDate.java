package com.example.gplusin.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragmentFirstDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final Calendar c = Calendar.getInstance();
    private final Calendar cf = Calendar.getInstance();
    private DatePickerFragment.DatePickerListener listener;

    String startdate="";
    String enddate="";
    String data="";

    public DatePickerFragmentFirstDate() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int customStyle = -1;

        Bundle arguments = getArguments();
        if (arguments != null)
            customStyle = arguments.getInt("customStyle", -1);
        startdate = arguments.getString("startdate", "");
        enddate = arguments.getString("enddate", "");


        c.setTime(new Date(startdate));


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        cf.setTime(new Date(enddate));


//        int yearcf = cf.get(Calendar.YEAR);
//        int monthcf = cf.get(Calendar.MONTH);
//        int daycf = cf.get(Calendar.DAY_OF_MONTH);
//



        if (customStyle != -1) {

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), customStyle, this, year, month, day);
            // long now = System.currentTimeMillis() - 1000;
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            dialog.getDatePicker().setMaxDate(c.getTimeInMillis()+(1000*60*60*24* Integer.valueOf(enddate)-1));

            dialog.getDatePicker().setMaxDate(cf.getTimeInMillis());
            return dialog;
        }

        else {
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);

            //  long now = System.currentTimeMillis() - 1000;
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            dialog.getDatePicker().setMaxDate(cf.getTimeInMillis());
            return dialog;
            // return new DatePickerDialog(getActivity(), this, year, month, day);
        }


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (listener != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            data = sdf.format(c.getTime());

            listener.onDatePicked(data);


        }
    }

    public void setDatePickerListener(DatePickerFragment.DatePickerListener listener) {
        this.listener = listener;
    }

    public interface DatePickerListener {
        void onDatePicked(String date);
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
}
