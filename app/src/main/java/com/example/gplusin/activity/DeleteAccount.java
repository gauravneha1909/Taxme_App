package com.example.gplusin.activity;

import static android.content.ContentValues.TAG;

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

import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.databinding.ActivityDeleteAccountBinding;
import com.example.gplusin.databinding.ActivityEditProfileBinding;
import com.example.gplusin.utils.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class DeleteAccount extends AppCompatActivity {
    ActivityDeleteAccountBinding binding;

    ProgressDialog dialog;
    private FirebaseAuth mAuth;
    User user;
    String typechild="";
    String phone_number="",country_code="",phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Window window = this.getWindow();


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        dialog= new ProgressDialog(DeleteAccount.this);

        dialog.setMessage("Please Wait...");

        mAuth = FirebaseAuth.getInstance();

        user=new User(DeleteAccount.this);

        if (user.getRegistertype().equals("google")){

            typechild=user.getUserid();

        }else {

            typechild=user.getPhone_number();

        }


        dialog.show();
        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
                .child("profiles")
                .child(typechild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                //   for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {

                dialog.cancel();
                if(snapshot.child("name").exists()) {

                    binding.lineone.setVisibility(View.VISIBLE);

//                    String name = snapshot.child("name").getValue(String.class);
//                    String date_of_birth= snapshot.child("date_of_birth").getValue(String.class);
//                    String emailid = snapshot.child("emailid").getValue(String.class);
//                    String gender = snapshot.child("gender").getValue(String.class);
                     phone_number = snapshot.child("phone_number").getValue(String.class);
                     country_code = snapshot.child("country_code").getValue(String.class);

                    binding.sentotpmessage.setText("Verify the OTP sent on your mobile "+country_code+phone_number+" to delete your account");




                }else {



                }


            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                dialog.cancel();

            }
        });

        binding.sendotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                firebaseotp();
            }
        });

        binding.verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userotp = binding.otpView.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Common.backendcode, userotp);


                dialog.show();
                signInTheUserByCredentials(credential,"");
            }
        });


        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void firebaseotp() {


        dialog.show();
        phone = phone_number;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(country_code+phone,
                60,
                TimeUnit.SECONDS, DeleteAccount.this,
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
                        Toast.makeText(DeleteAccount.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("errrr", e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        Log.e("aaaa",s);
//                        backendcode=s;
                        Common.backendcode=s;
                        dialog.cancel();
                        Common.countrycode=country_code;
                      //  RequsetSignup();

                        binding.lineone.setVisibility(View.GONE);
                        binding.linetwo.setVisibility(View.VISIBLE);

                    }
                });

    }


    private void signInTheUserByCredentials(PhoneAuthCredential credential, String image) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(DeleteAccount.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser userdata = mAuth.getCurrentUser();


                    deleteaccount();


                } else {
                    dialog.cancel();
                    Toast.makeText(DeleteAccount.this, "Otp Wrong Please Check", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void deleteaccount() {

        DatabaseReference ref = FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference();

        Query applesQuery = ref.
                  child("profiles").child(typechild);
                      //  child(Common.employedetaileurl).child(amnsm.get(position).keyid);  //.orderByChild("keyid").equalTo(amnsm.get(position).keyid)

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();


//                    FirebaseDatabase.getInstance().getReference()
//                            .child("UserEmploymentWorkSchedule").child(amnsm.get(position).keyid).removeValue();

                    dialog.dismiss();
                    User user=new User(  DeleteAccount.this );
                    user.setFirstTimeLaunch( true );
                    startActivity( new Intent(  DeleteAccount.this, Splash.class ) );
                    finishAffinity();

                    Toast.makeText(DeleteAccount.this, "Delete Success", Toast.LENGTH_SHORT).show();





                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
                dialog.dismiss();
            }
        });




    }
}