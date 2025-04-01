package com.example.gplusin.retrofit;


//import com.example.lendeasy.modal.BalanceDataOne;
//import com.example.lendeasy.modal.ComingDataOne;
//import com.example.lendeasy.modal.RegisterOne;
//import com.example.lendeasy.modalnew.CountNotificationOne;
//import com.example.lendeasy.modalnew.NewLoginOne;
//import com.example.lendeasy.modalnew.NewNotificationDataOne;
//import com.example.lendeasy.modalnew.NewOutStandingLoanOne;
//import com.example.lendeasy.modalnew.NewRegisterOne;
//import com.example.lendeasy.modalnew.NotificationSeenUser;
//import com.example.lendeasy.modalnew.PortfolioOne;
//import com.example.lendeasy.modalnew.UpdateProfileOne;


import com.example.gplusin.Modal;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.pojo.Adhoc_incomeOne;
import com.example.gplusin.pojo.Adhoc_work_hoursOne;
import com.example.gplusin.pojo.BusinessExpenseOne;
import com.example.gplusin.pojo.EmployeeDetailsMainOne;
import com.example.gplusin.pojo.Leave_daysOne;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServise {

//    @FormUrlEncoded
//    @POST("/registeruser")
//    Call<String> registeruser(
//            @Field("country_code") String country_code,
//            @Field("date_of_birth") String date_of_birth,
//            @Field("emailid") String emailid,
//            @Field("gender") String gender,
//            @Field("name") String name,
//            @Field("password") String password,
//            @Field("phone_number") String phone_number,
//            @Field("register_type") String register_type
//    );


    @POST("/registeruser")
    Call<RegisterData> registeruser(
            @Body JsonObject jsonObject);

    @POST("/loginuser")
    Call<RegisterData> loginuserlendeasy(
            @Body JsonObject jsonObject);

    @POST("/UseremploymentUpdate")
    Call<RegisterData> UseremploymentMainUpdate(
            @Body HashMap jsonObject);

    @POST("/UseremploymentAdd")
    Call<RegisterData> UseremploymentMain(
            @Body HashMap jsonObject);

    @POST("/UseremploymentDelete")
    Call<RegisterData> UseremploymentMainDelete(
            @Body HashMap jsonObject);

//    @FormUrlEncoded
//    @POST("/UseremploymentMain")
//    Call<RegisterData> UseremploymentMain(
//            @Field("EmployerName") String EmployerName,
//            @Field("EmploymentStartDate") String EmploymentStartDate,
//            @Field("EmploymentEndDate") String EmploymentEndDate,
//            @Field("StandardDailyHours") String StandardDailyHours,
//            @Field("Paymentrateamount") String Paymentrateamount,
//            @Field("Paymentratefrequency") String Paymentratefrequency,
//            @Field("IsGSTregistered") String IsGSTregistered,
//            @Field("WitholdingTaxRate") String WitholdingTaxRate,
//            @Field("UserID") String UserID,
//            @Field("FirstPaymentDate") String FirstPaymentDate,
//            @Field("PaymentFrequency") String PaymentFrequency,
//            @Field("EmploymentID") String EmploymentID,
//            @Field("EmploymentRegion") String EmploymentRegion
//    );

    @POST("/Business_expense")
    Call<RegisterData> Business_expense(
            @Body HashMap jsonObject);

    @POST("/adhoc_income")
    Call<RegisterData> adhoc_income(
            @Body HashMap jsonObject);

    @POST("/leave_json")
    Call<RegisterData> leave_days(
            @Body HashMap jsonObject);

    @POST("/delete_leave_json")
    Call<RegisterData> leave_daysdelete(
            @Body HashMap jsonObject);

    @POST("/update_leave_json")
    Call<RegisterData> leave_daysUpdate(
            @Body HashMap jsonObject);

    @POST("/adhoc_work_hours")
    Call<RegisterData> adhoc_work_hours(
            @Body HashMap jsonObject);

    @POST("/update_adhoc_work_hours")
    Call<RegisterData> adhoc_work_hoursupdate(
            @Body HashMap jsonObject);

    @POST("/delete_adhoc_work_hours")
    Call<RegisterData> adhoc_work_hoursdelete(
            @Body HashMap jsonObject);

    @POST("/historical_data")
    Call<RegisterData> historical_data(
            @Body HashMap jsonObject);


    @FormUrlEncoded
    @POST("/historical_datafromid")
    Call<String> historical_datafromid(
            @Field("UserID") String UserID);



//    @FormUrlEncoded
//    @POST("/useremploymentmainfromid")
//    Call<EmployeeDetailsMainOne> useremploymentmainfromid(
//            @Field("UserID") String UserID);


    @POST("/userEmployeeFetch")
    Call<EmployeeDetailsMainOne> useremploymentmainfromid(
            @Body HashMap jsonObject);


    @FormUrlEncoded
    @POST("/adhoc_work_hoursfromid")
    Call<Adhoc_work_hoursOne> adhoc_work_hoursfromid(
            @Field("userid") String UserID);


    @FormUrlEncoded
    @POST("/Business_expensefromid")
    Call<BusinessExpenseOne> Business_expensefromid(
            @Field("UserID") String UserID);

    @FormUrlEncoded
    @POST("/adhoc_incomefromid")
    Call<Adhoc_incomeOne> adhoc_incomefromid(
            @Field("UserID") String UserID);

    @FormUrlEncoded
    @POST("/leave_jsonfromid")
    Call<Leave_daysOne> leave_daysfromid(
            @Field("userid") String UserID);

//    @GET("/home_video")
//    Call<HomePageOne> home_video();

//    @Multipart
//    @POST("/registeruserlendeasy")
//    Call<NewRegisterOne> registeruser(
//            @Part("Username") String name,
//            @Part("UserPhoneNumber") String phone_number,
//            @Part("UserEmailID") String emailid,
//            @Part("USERGENDER") String gender,
//            @Part("USERDOB") String date_of_birth,
//            @Part("Password") String password,
//            @Part("LoginMethod") String registertype,
//            @Part MultipartBody.Part image,
//            @Part("UserMethodLoginId") String registeruniqid,
//            @Part("UserPhoneNumberInternational") String phone_number_In,
//            @Part("UserPhoneNumberNational") String phone_number_N
//    );
//
//
//    @FormUrlEncoded
//    @POST("/registeruserlendeasy")
//    Call<NewRegisterOne> registerusernull(
//            @Field("Username") String name,
//            @Field("UserPhoneNumber") String phone_number,
//            @Field("UserEmailID") String emailid,
//            @Field("USERGENDER") String gender,
//            @Field("USERDOB") String date_of_birth,
//            @Field("Password") String password,
//            @Field("LoginMethod") String registertype,
//            @Field("UserMethodLoginId") String registeruniqid,
//            @Field("UserImage") String profileimage,
//            @Field("UserPhoneNumberInternational") String phone_number_In,
//            @Field("UserPhoneNumberNational") String phone_number_N
//    );
//
//
//    @POST("/registerupdatelendeasy")
//    Call<RegisterOne> registerupdatelendeasy(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/registeruserlendeasyone")
//    Call<NewRegisterOne> registeruserlendeasyone(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/loginuserlendeasy")
//    Call<NewLoginOne> loginuserlendeasy(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/notificationgetdata")
//    Call<NewLoginOne> notificationgetdata(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/forgetlendepassword")
//    Call<NewLoginOne> forgetlendepassword(
//            @Body JsonObject jsonObject);
//
//    @POST("/updatepassword")
//    Call<NewLoginOne> updatepassword(
//            @Body JsonObject jsonObject);
//
//    @POST("/updateemailid")
//    Call<NewLoginOne> updateemailid(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/deleteaccount")
//    Call<NewLoginOne> deleteaccount(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/userrequestsgive")
//    Call<NewLoginOne> userrequestsgive(
//            @Body JsonObject jsonObject);
//
//    @POST("/lendeasyupdateloandetailes")
//    Call<NewLoginOne> lendeasyupdateloandetailes(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/insertnotification")
//    Call<String> insertnotification(
//            @Body JsonObject jsonObject);
//
//
//    @GET("/querykatnak")
//    Call<String> querykatnak();
//
//    @POST("/notificationcount")
//    Call<CountNotificationOne> notificationcount(
//            @Body JsonObject jsonObject);
//
//    @POST("/notificationseen")
//    Call<NotificationSeenUser> notificationseen(
//            @Body JsonObject jsonObject);
//
//    @POST("/deletenotification")
//    Call<NotificationSeenUser> deletenotification(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/notificationsetting")
//    Call<NotificationSeenUser> notificationsetting(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/userrequestsrequest")
//    Call<NewLoginOne> userrequestsrequest(
//            @Body JsonObject jsonObject);
//
//    @POST("/userrequestsdelete")
//    Call<RegisterOne> userrequestsdelete(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/useracceptloandelete")
//    Call<RegisterOne> useracceptloandelete(
//            @Body RequestBody jsonObject);
//
//    @POST("/userforcloseloandelete")
//    Call<RegisterOne> userforcloseloandelete(
//            @Body RequestBody jsonObject);
//
//
//    @POST("/updateuserrequest")
//    Call<RegisterOne> updateuserrequest(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/checkregisterrlendeasy")
//    Call<RegisterOne> checkregisterrlendeasy(
//            @Body JsonObject jsonObject);
//
//    @POST("/balacedata")
//    Call<BalanceDataOne> balacedata(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/potpoliodata")
//    Call<PortfolioOne> potpoliodata(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/upcommingodata")
//    Call<ComingDataOne> upcomingdata(
//            @Body JsonObject jsonObject);
//
//    @POST("/historicaldata")
//    Call<ComingDataOne> histroricaldata(
//            @Body JsonObject jsonObject);
//
//    @POST("/portfolioloandetail")
//    Call<ComingDataOne> portfolioloandetail(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/updatepaidloanstatus")
//    Call<ComingDataOne> updatepaidloanstatus(
//            @Body JsonObject jsonObject);
//
//
//
//
//    @POST("/usergetnotificationdata")
//    Call<NewNotificationDataOne> usergetnotificationdata(
//            @Body JsonObject jsonObject);
//
//    @POST("/outstandingrequest")
//    Call<NewOutStandingLoanOne> outstandingrequest(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/outstandingrequestcanceldecline")
//    Call<NewOutStandingLoanOne> outstandingrequestcanceldecline(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/outstandingrequestrequest")
//    Call<NewOutStandingLoanOne> outstandingrequestrequest(
//            @Body JsonObject jsonObject);
//
//    @POST("/outstandingrequestrequestcanceldecline")
//    Call<NewOutStandingLoanOne> outstandingrequestrequestcanceldecline(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/outstandingrequestgive")
//    Call<NewOutStandingLoanOne> outstandingrequestgive(
//            @Body JsonObject jsonObject);
//
//    @POST("/outstandingrequestgivecanceldecline")
//    Call<NewOutStandingLoanOne> outstandingrequestgivecanceldecline(
//            @Body JsonObject jsonObject);
//
//
//    @POST("/outstandingrequestdetailes")
////    @FormUrlEncoded
//    Call<NewOutStandingLoanOne> outstandingrequestdetailes(
//            @Body JsonObject jsonObject);
////          @Field("id") String id,
////          @Field("usettype") String usettype);
//
//
//    @POST("/outstandingrequestdetailescanceldecline")
////    @FormUrlEncoded
//    Call<NewOutStandingLoanOne> outstandingrequestdetailescanceldecline(
//            @Body JsonObject jsonObject);
//
//
//
//    @POST("/lendeasyupdateuseraccept")
//    Call<NewOutStandingLoanOne> lendeasyupdateuseraccept(
//            @Body JsonObject jsonObject);
//
//
//
//    @Multipart
//    @POST("/lendeasyupdateprofile")
//    Call<UpdateProfileOne> lendeasyupdateprofile(
//            @Part MultipartBody.Part image,
//            @Part("UseruniqueID") String UseruniqueID,
//            @Part("Username") String Username,
//            @Part("UserEmailID") String UserEmailID,
//            @Part("USERGENDER") String USERGENDER,
//            @Part("USERDOB") String USERDOB,
//            @Part("UserPhoneNumber") String UserPhoneNumber
//    );

}

