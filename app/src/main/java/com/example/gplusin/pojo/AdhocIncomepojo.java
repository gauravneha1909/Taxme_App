package com.example.gplusin.pojo;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AdhocIncomepojo {

    @SerializedName("incomename")
    public String income_source="";

    @SerializedName("incomeamount")
    public String income_amount="";

    @SerializedName("istaxdeducted")
    public String taxdeductedsource="";

    @SerializedName("TDSAmount")
    public String tax_reduce_amount="";


//    @SerializedName("IncomeID")
    @SerializedName("AdhocIncomeID")
    public String keyid="";


    public String image="";


    public String image_type="";


    public String pdfname="";

    @SerializedName("Incomedate")
    public Date Adhoc_Income_Date;
}
