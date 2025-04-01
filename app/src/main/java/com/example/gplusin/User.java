package com.example.gplusin;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    String userid="",name="",phone_number="",emailid="",gender="",date_of_birth="",password="",registertype="",image="",registeruniqid="";
    SharedPreferences sharedPreferences;
    boolean editor , registration= true ;
    String appnotification="",emailnotification="",phonenotification="",checknotification="";



    public User(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences( "userinfo",Context.MODE_PRIVATE );
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor = sharedPreferences.edit().clear().commit();

    }


    public String getChecknotification() {
        checknotification=sharedPreferences.getString( "checknotification","" );
        return checknotification;
    }

    public void setChecknotification(String checknotification) {
        sharedPreferences.edit().putString( "checknotification",checknotification ).commit();
        this.checknotification = checknotification;
    }

    public String getAppnotification() {
        appnotification=sharedPreferences.getString( "appnotification","" );
        return appnotification;
    }

    public void setAppnotification(String appnotification) {
        sharedPreferences.edit().putString( "appnotification",appnotification ).commit();
        this.appnotification = appnotification;
    }

    public String getEmailnotification() {
        emailnotification=sharedPreferences.getString( "emailnotification","" );
        return emailnotification;
    }

    public void setEmailnotification(String emailnotification) {
        sharedPreferences.edit().putString( "emailnotification",emailnotification ).commit();
        this.emailnotification = emailnotification;
    }

    public String getPhonenotification() {
        phonenotification=sharedPreferences.getString( "phonenotification","" );
        return phonenotification;
    }

    public void setPhonenotification(String phonenotification) {
        sharedPreferences.edit().putString( "phonenotification",phonenotification ).commit();
        this.phonenotification = phonenotification;
    }

    public String getUserid() {
        userid=sharedPreferences.getString( "userid","" );
        return userid;
    }

    public void setUserid(String userid) {
        sharedPreferences.edit().putString( "userid",userid ).commit();
        this.userid = userid;
    }

    public String getName() {
        name=sharedPreferences.getString( "name","" );
        return name;
    }

    public void setName(String name) {
        sharedPreferences.edit().putString( "name",name ).commit();
        this.name = name;
    }

    public String getPhone_number() {
        phone_number=sharedPreferences.getString( "phone_number","" );
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        sharedPreferences.edit().putString( "phone_number",phone_number ).commit();
        this.phone_number = phone_number;
    }

    public String getEmailid() {
        emailid=sharedPreferences.getString( "emailid","" );
        return emailid;
    }

    public void setEmailid(String emailid) {
        sharedPreferences.edit().putString( "emailid",emailid ).commit();
        this.emailid = emailid;
    }

    public String getGender() {
        gender=sharedPreferences.getString( "gender","" );
        return gender;
    }

    public void setGender(String gender) {
        sharedPreferences.edit().putString( "gender",gender ).commit();
        this.gender = gender;
    }

    public String getDate_of_birth() {
        date_of_birth=sharedPreferences.getString( "date_of_birth","" );
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        sharedPreferences.edit().putString( "date_of_birth",date_of_birth ).commit();
        this.date_of_birth = date_of_birth;
    }

    public String getPassword() {
        password=sharedPreferences.getString( "password","" );
        return password;
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString( "password",password ).commit();
        this.password = password;
    }

    public String getRegistertype() {
        registertype=sharedPreferences.getString( "registertype","" );
        return registertype;
    }

    public void setRegistertype(String registertype) {
        sharedPreferences.edit().putString( "registertype",registertype ).commit();
        this.registertype = registertype;
    }

    public String getImage() {
        image=sharedPreferences.getString( "image","" );
        return image;
    }

    public void setImage(String image) {
        sharedPreferences.edit().putString( "image",image ).commit();
        this.image = image;
    }

    public String getRegisteruniqid() {
        registeruniqid=sharedPreferences.getString( "registeruniqid","" );
        return registeruniqid;
    }

    public void setRegisteruniqid(String registeruniqid) {
        sharedPreferences.edit().putString( "registeruniqid",registeruniqid ).commit();
        this.registeruniqid = registeruniqid;
    }
}
