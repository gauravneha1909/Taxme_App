package com.example.gplusin.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LeaveDataHistory {

    @SerializedName("json_message")
    public String leavejson="";

    public String employeename="";
    public Date startdate;
    public Date enddate;
    public String total_days="";


    @SerializedName("leave_json_id")
    public String keyid="";

    @SerializedName("adhoc_work_hour_json_id")
    public String adhoc_work_hour_json_id="";

    public String total_hours ="";

    public String EmploymentID ="";


    public String getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(String total_hours) {
        this.total_hours = total_hours;
    }

    public String getLeavejson() {
        return leavejson;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public void setLeavejson(String leavejson) {
        this.leavejson = leavejson;
    }

//    public String getStartdate() {
//        return startdate;
//    }
//
//    public void setStartdate(String startdate) {
//        this.startdate = startdate;
//    }
//
//    public String getEnddate() {
//        return enddate;
//    }
//
//    public void setEnddate(String enddate) {
//        this.enddate = enddate;
//    }

    public String getTotal_days() {
        return total_days;
    }

    public void setTotal_days(String total_days) {
        this.total_days = total_days;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }
}
