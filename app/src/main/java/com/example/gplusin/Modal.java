package com.example.gplusin;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Modal {

//    @PropertyName("EmployerName")
    @SerializedName("EmployerName")
    public String employeename="";

    @SerializedName("EmploymentStartDate")
    public Date startdate;

    public String Paymentratefrequency;

    @SerializedName("EmploymentEndDate")
    public Date enddate;

    @SerializedName("StandardDailyHours")
    public String daikyhour="";

    @SerializedName("Paymentrateamount")
    public String paymentratestring="";

    @SerializedName("IsGSTregistered")
    public String gstregisterstring="";

    @SerializedName("WitholdingTaxRate")
    public String withoutTaxRate="";

    @SerializedName("FirstPaymentDate")
    public Date firstpayment;

    @SerializedName("PaymentFrequency")
    public String frequency="";

    @SerializedName("EmploymentID")
    public String keyid="";

    @SerializedName("EmploymentRegion")
    public String employeeregion="";


    public Modal() {
    }

    public String getEmployeeregion() {
        return employeeregion;
    }

    public void setEmployeeregion(String employeeregion) {
        this.employeeregion = employeeregion;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

//    public String getFirstpayment() {
//        return firstpayment;
//    }
//
//    public void setFirstpayment(String firstpayment) {
//        this.firstpayment = firstpayment;
//    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

//    public String getStartdate() {
//        return startdate;
//    }
//
//    public void setStartdate(String startdate) {
//        this.startdate = startdate;
//    }

//    public String getEnddate() {
//        return enddate;
//    }
//
//    public void setEnddate(String enddate) {
//        this.enddate = enddate;
//    }

    public String getDaikyhour() {
        return daikyhour;
    }

    public void setDaikyhour(String daikyhour) {
        this.daikyhour = daikyhour;
    }

    public String getPaymentratestring() {
        return paymentratestring;
    }

    public void setPaymentratestring(String paymentratestring) {
        this.paymentratestring = paymentratestring;
    }

    public String getGstregisterstring() {
        return gstregisterstring;
    }

    public void setGstregisterstring(String gstregisterstring) {
        this.gstregisterstring = gstregisterstring;
    }

    public String getWithoutTaxRate() {
        return withoutTaxRate;
    }

    public void setWithoutTaxRate(String withoutTaxRate) {
        this.withoutTaxRate = withoutTaxRate;
    }
}
