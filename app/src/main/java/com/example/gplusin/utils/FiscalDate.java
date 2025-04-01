package com.example.gplusin.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class FiscalDate {

    private static final int    FIRST_FISCAL_MONTH  = Calendar.MARCH;

    private Calendar            calendarDate;

    public FiscalDate(Calendar calendarDate) {
        this.calendarDate = calendarDate;
    }

    public FiscalDate(Date date) {
        this.calendarDate = Calendar.getInstance();
        this.calendarDate.setTime(date);
    }

    public int getFiscalMonth() {
        int month = calendarDate.get(Calendar.MONTH);
        int result = ((month - FIRST_FISCAL_MONTH - 1) % 12) + 1;
        if (result < 0) {
            result += 12;
        }
        return result;
    }

    public int getFiscalYear() {
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        return (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
    }

    public int getCalendarMonth() {
        return calendarDate.get(Calendar.MONTH);
    }

    public int getCalendarYear() {
        return calendarDate.get(Calendar.YEAR);
    }

//    public static void main(String[] args) {
//        displayFinancialDate(Calendar.getInstance());
//        displayFinancialDate(setDate(2013, 1, 1));
//        displayFinancialDate(setDate(2012, 6, 25));
//    }

    public static Calendar setDate(String datess) {
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);

        calendar.setTime(new Date(datess));

        return calendar;
    }

    public static String displayFinancialDate(Calendar calendar) {
        FiscalDate fiscalDate = new FiscalDate(calendar);
        int year = fiscalDate.getFiscalYear();
        System.out.println("Current Date : " + calendar.getTime().toString());
        System.out.println("Fiscal Years : " + year + "-" + (year + 1));
        System.out.println("Fiscal Month : " + fiscalDate.getFiscalMonth());
        System.out.println(" ");

        Log.e("whjwjwbnw",year + "-" + (year + 1));

        return year + "-" + (year + 1) ;
    }


}
