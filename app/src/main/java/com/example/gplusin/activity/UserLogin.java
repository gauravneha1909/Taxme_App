package com.example.gplusin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.MainActivity;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.ActivityUserLoginBinding;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.JsonObject;


import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLogin extends AppCompatActivity {
    ActivityUserLoginBinding binding;
    ProgressDialog dialog;
    String phone="";
    String backendcode="";

    private FirebaseAuth mAuth;
    ApiServise apiServise;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_user_login);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();
        user = new User(UserLogin.this);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        dialog = new ProgressDialog(UserLogin.this);
        dialog.setMessage("Please Wait...");

        mAuth = FirebaseAuth.getInstance();

       // binding.imgCountyFlag.setImageResource(com.yesterselga.countrypicker.R.drawable.flag_nz);
        binding.txtCountryCode.setText("+64");

//        CountryPicker picker = CountryPicker.newInstance("Select Country", Theme.DARK);  // dialog title and theme
//        picker.setListener(new CountryPickerListener() {
//            @Override
//            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
//
//                binding.imgCountyFlag.setImageResource(flagDrawableResID);
//                binding.txtCountryCode.setText(dialCode);
//                //  Log.e("aa","name "+name+" url "+flagDrawableResID+" dialcode "+dialCode);
//                picker.dismiss();
//            }
//        });
//        binding.linFrequency11111.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
//            }
//        });
//
//        binding.imgCountyFlag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
//            }
//        });
//
//        binding.txtCountryCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
//            }
//        });
//


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!binding.emailaddress.getText().toString().matches(emailPattern)) {
//
//                    Toast.makeText(UserLogin.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT ).show();
//
//                    return;
//                }


                if (binding.password.getText().toString().length() < 6) {

                    Toast.makeText(UserLogin.this, "pls enter more the 6 character in password", Toast.LENGTH_SHORT ).show();

                    return;

                }

                Common.mobilenumber=binding.emailaddress.getText().toString().trim();

                // signin existing user
                dialog.show();
                callmethod();


            }

        });


        binding.forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLogin.this,ForgetPassword.class));
                overridePendingTransition(0,0);
            }
        });
    }


    private void firebaseotp(String toString) {


        dialog.show();
        phone = toString;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(binding.txtCountryCode.getText().toString() + toString,
                60,
                TimeUnit.SECONDS, UserLogin.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        String code = phoneAuthCredential.getSmsCode();
                        //  binding.txtPinEntry.setText(code);
                        // RequsetSignup();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // hud.dismiss();
                        dialog.cancel();
                        Toast.makeText(UserLogin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("errrr", e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        Log.e("aaaa",s);
                        backendcode=s;
                        Common.backendcode=s;
                        dialog.cancel();
                        Common.countrycode=binding.txtCountryCode.getText().toString();
                        RequsetSignup();
                    }
                });

    }

    private void RequsetSignup() {




        Intent intent=new Intent( UserLogin.this, OtpActivityLogin.class );

        startActivity( intent );



    }


    private void callmethod() {

        String idStr = binding.emailaddress.getText().toString().substring(binding.emailaddress.getText().toString().lastIndexOf( '+' ) + 1 );
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty( "emailid", idStr );
        jsonObject.addProperty( "password",binding.password.getText().toString() );

        dialog.show();
        apiServise.loginuserlendeasy(jsonObject)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Login Success" )) {

//                                if (response.body().data.get(0).UserStatus.equals("Deleted")){
//
//                                    Toast.makeText(UserLogin.this, "you do not have an account with given details", Toast.LENGTH_LONG).show();
//
//                                }else {

                                user.setName(response.body().getData().get(0).getName());
                                user.setEmailid(response.body().getData().get(0).getEmailid());
                               // user.setImage(image);
                                user.setPhone_number(response.body().getData().get(0).getPhoneNumber());
                                user.setRegistertype(response.body().getData().get(0).getRegisterType());

                                // dialog.cancel();
                                Toast.makeText(UserLogin.this, "Success", Toast.LENGTH_SHORT).show();
                                user.setUserid(String.valueOf(response.body().getData().get(0).getId()));
                                startActivity(new Intent(UserLogin.this, MainActivity.class));
                                finishAffinity();

                             //   }


                            } else {
                                Toast.makeText(UserLogin.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
                            }
                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterData> call, Throwable t) {
                        dialog.cancel();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );





    }

}