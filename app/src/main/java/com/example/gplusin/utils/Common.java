package com.example.gplusin.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.gplusin.Modal;
import com.example.gplusin.activity.OtpActivity;
import com.example.gplusin.fragment.adhoc_income.AdhocIncomeHistory;
import com.example.gplusin.pojo.AdhocIncomepojo;
import com.example.gplusin.pojo.Businessexpensepojo;
import com.example.gplusin.pojo.LeaveDataHistory;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.retrofit.RetrofitIO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Common {
    public static String mobilenumber;
    public static String backendcode;
    public static String countrycode;
    public static Uri userimagemultipart;

    public static final String BASE_URL="http://ec2-3-110-123-206.ap-south-1.compute.amazonaws.com:5000";

  //  public static final String BASE_URL="http://192.168.132.98:5000";
    public static Modal updatedetaileemployee;
    public static LeaveDataHistory updateleavedetaile;
    public static AdhocIncomepojo updateadhocincome;
    public static Businessexpensepojo updatebusiness_expense;
    public static AdhocIncomeHistory adhocincomecontex;

    public static String employedetaileurl="UseremploymentMain";
    public static String Useremploymentscreendetaile="Useremploymentscreendetaile";


    public static ApiServise getAPI(){
        return RetrofitIO.getClient( BASE_URL ).create( ApiServise.class );
    }


    public static void keyHashes(Context context){
        try {

            PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest messageDigest=MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                String keyhashes=new String(Base64.encode(messageDigest.digest(),0));
                Log.e("Key Hashes", "Facebook Key Hashes "+ keyhashes);
            }


        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public static void updateToken(OtpActivity otpActivity, String token) {


//        String phone = user.getUserid();
//        FirebaseDatabase.getInstance().getReference( Common.TOKEN_REF )
//                .child( user.getUserid() )
//                // .child( String.valueOf( FirebaseMessaging.getInstance().subscribeToTopic("allDevices") ) )
//                .setValue( new TokenModal( phone, newToken ) )
//                .addOnFailureListener( e -> {
//                    // Toast.makeText( context, "" + e.getMessage(), Toast.LENGTH_SHORT ).show();
//                } );



    }


//    public static void updateToken(Context context, String newToken) {
//
//        User user=new User( context );
//        if (user.getUserid().equals("")){
//            // String phone="1";
////            String androidID = Settings.Secure.getString( context.getContentResolver(), Settings.Secure.ANDROID_ID);
////            FirebaseDatabase.getInstance().getReference( Common.TOKEN_REF )
////                    //  .child( String.valueOf( FirebaseInstanceId.getInstance().getInstanceId() ) )
////                    .child( androidID )
////                    // .child( String.valueOf( FirebaseMessaging.getInstance().subscribeToTopic("allDevices") ) )
////                    .setValue( new TokenModal( androidID, newToken ) )
////                    .addOnFailureListener( e -> {
////                       // Toast.makeText( context, "" + e.getMessage(), Toast.LENGTH_SHORT ).show();
////                    } );
//        }else {
//            String phone = user.getUserid();
//            FirebaseDatabase.getInstance().getReference( Common.TOKEN_REF )
//                    .child( user.getUserid() )
//                    // .child( String.valueOf( FirebaseMessaging.getInstance().subscribeToTopic("allDevices") ) )
//                    .setValue( new TokenModal( phone, newToken ) )
//                    .addOnFailureListener( e -> {
//                        // Toast.makeText( context, "" + e.getMessage(), Toast.LENGTH_SHORT ).show();
//                    } );
//        }
//    }


//    User user;
//    ApiServise apiServise;
//    ProgressDialog dialog;
//    user=new User(BankDetaile.this);
//    apiServise = Common.getAPI();
//    dialog= new ProgressDialog(BankDetaile.this);
//        dialog.setMessage("Please Wait...");


//    JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty( "registeruniqid", user.getUserid() );
//        jsonObject.addProperty( "bank_account_number",binding.accountnumber.getText().toString() );
//        jsonObject.addProperty( "ifsc_code", binding.ifsccode.getText().toString());
//        jsonObject.addProperty( "bank_name", binding.bankname.getText().toString());
//        jsonObject.addProperty( "bank_branch", binding.bankbranch.getText().toString() );
//
//        dialog.show();
//        apiServise.registerupdatelendeasy(jsonObject)
//            .enqueue( new Callback<RegisterOne>() {
//        @Override
//        public void onResponse(Call<RegisterOne> call, Response<RegisterOne> response) {
//
//            dialog.cancel();
//            if (response.isSuccessful()) {
//                if (response.body().result.equals( "Update Success" )) {
//                    user.setBank_account_number(binding.accountnumber.getText().toString());
//                    user.setIfsc_code(binding.ifsccode.getText().toString());
//                    user.setBank_name(binding.bankname.getText().toString());
//                    user.setBank_branch( binding.bankbranch.getText().toString());
//                    Toast.makeText(BankDetaile.this, "Success", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(BankDetaile.this, MainActivity.class));
//                    finish();
//
//                } else {
//                    Toast.makeText(BankDetaile.this, response.body().result, Toast.LENGTH_SHORT).show();
//                }
//            } else {
//
//
//            }
//        }
//
//        @Override
//        public void onFailure(Call<RegisterOne> call, Throwable t) {
//            dialog.cancel();
//            // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
//        }
//    } );



//    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
//    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
//    implementation 'io.reactivex.rxjava2:rxjava:2.2.9'
//    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.6.1'
//    implementation 'androidx.room:room-rxjava2:2.2.0-rc01'
//    annotationProcessor 'androidx.room:room-compiler:2.2.0-rc01'
//    implementation 'com.squareup.retrofit2:converter-scalars:2.0.1'
//    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
//    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
//    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
//    implementation 'com.squareup.okhttp3:logging-interceptor:3.4.1'

}
