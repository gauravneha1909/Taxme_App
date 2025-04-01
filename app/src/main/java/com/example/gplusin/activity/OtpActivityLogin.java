package com.example.gplusin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.MainActivity;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.databinding.ActivityOtpBinding;
import com.example.gplusin.databinding.ActivityOtpLoginBinding;
import com.example.gplusin.utils.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class OtpActivityLogin extends AppCompatActivity {

    ActivityOtpLoginBinding binding;
    //    ApiServise apiServise;
    User user;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);

        binding = ActivityOtpLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        user = new User(OtpActivityLogin.this);

        dialog = new ProgressDialog(OtpActivityLogin.this);
        dialog.setMessage("Please Wait...");
        countdata();

        binding.txtMobile.setText("Enter OTP sent to " + Common.countrycode + " " + Common.mobilenumber);
//        Toast.makeText( Verify.this, "Enter Your Otp Number "+Common.otpnumber, Toast.LENGTH_SHORT ).show();
//        apiServise = Common.getAPI();
//        user=new User( getActivity() );
        mAuth = FirebaseAuth.getInstance();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userotp = binding.otpView.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Common.backendcode, userotp);


                    dialog.show();
                    signInTheUserByCredentials(credential,"");





                // startActivity(new Intent(OtpActivity.this,LoginForm.class));
                //  finishAffinity();
            }
        });



    }

    private void countdata() {
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                binding.timedata.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                binding.timedata.setText("Completed");
            }
        }.start();

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential, String image) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(OtpActivityLogin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser userdata = mAuth.getCurrentUser();
                    getdatatableuser(String.valueOf(userdata));



                } else {
                    dialog.cancel();
                    Toast.makeText(OtpActivityLogin.this, "Otp Wrong Please Check", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getdatatableuser(String userid) {

        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
                .child("profiles")
                .child(Common.mobilenumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child("name").exists()) {

                        String name = snapshot.child("name").getValue(String.class);
                        String emailid = snapshot.child("emailid").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);
                       // boolean isAvailable = snapshot.child("isAvailable").getValue(Boolean.class);
                        user.setName(name);
                        user.setEmailid(emailid);
                        user.setImage(image);
                        user.setPhone_number(Common.mobilenumber);
                        user.setRegistertype("register");

                    // dialog.cancel();
                    Toast.makeText(OtpActivityLogin.this, "Success", Toast.LENGTH_SHORT).show();
                    user.setUserid(String.valueOf(userid));
                    startActivity(new Intent(OtpActivityLogin.this, MainActivity.class));
                    finishAffinity();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

}