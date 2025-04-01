package com.example.gplusin.pojo;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Businessexpensepojo {

    @SerializedName("expensename")
    public String expense_name="";

//    @PropertyName("ExpenseAmount")

    @SerializedName("expenseamount")
    public String expense_amount="";

    @SerializedName("IsGSTpaid")
    public String is_gst_paid="";

    @SerializedName("GSTAmount")
    public String gst_amount="";

    @SerializedName("ClaimableGSTPercentage")
    public String claimable_gst="";

    @SerializedName("Expenseid")
    public String keyid="";

    public String image="";
    public String image_type="";
    public String pdfname="";

    @SerializedName("expensedate")
    public Date Business_expense_Date;
}
