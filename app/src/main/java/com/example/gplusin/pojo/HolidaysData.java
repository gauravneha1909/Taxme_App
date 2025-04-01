package com.example.gplusin.pojo;

public class HolidaysData {

    public String Holiday="";
    public String Observed_date="";

    public String provinceName="";
    public String province_Observed_date="";

    public HolidaysData(String holiday, String observed_date,String provinceName, String province_Observed_date) {
        Holiday = holiday;
        Observed_date = observed_date;
        this.provinceName=provinceName;
        this.province_Observed_date=province_Observed_date;
    }



}
