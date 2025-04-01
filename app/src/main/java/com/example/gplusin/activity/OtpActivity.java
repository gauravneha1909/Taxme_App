package com.example.gplusin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.gplusin.MainActivity;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.ActivityOtpBinding;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;
    //    ApiServise apiServise;
    User user;
    Intent intent;
    String name, emailid, gender, date_of_birth, password,country_code="";
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    String orderid;
    StorageReference storageReference;
    StorageTask uploadTask;

    ApiServise apiServise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));


        user = new User(OtpActivity.this);

        storageReference= FirebaseStorage.getInstance().getReference("profile_image");

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        orderid = datetime.toString();

        intent = getIntent();
        name = intent.getStringExtra("name");
        emailid = intent.getStringExtra("emailid");
        gender = intent.getStringExtra("gender");
        date_of_birth = intent.getStringExtra("date_of_birth");
        password = intent.getStringExtra("password");
        country_code = intent.getStringExtra("country_code");

        dialog = new ProgressDialog(OtpActivity.this);
        dialog.setMessage("Please Wait...");
        countdata();

        binding.txtMobile.setText("Enter OTP sent to " + Common.countrycode + " " + Common.mobilenumber);
//        Toast.makeText( Verify.this, "Enter Your Otp Number "+Common.otpnumber, Toast.LENGTH_SHORT ).show();
//        apiServise = Common.getAPI();
//        user=new User( getActivity() );
        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(OtpActivity.this, "Your Otp: 1234", Toast.LENGTH_LONG).show();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userotp = binding.otpView.getText().toString().trim();

              //  PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Common.backendcode, userotp);


//                if (Common.userimagemultipart!=null){
//                    dialog.show();
//                    uploadimage(credential);
//
//
//                }else {


                    if (userotp.equals("1234")) {
                        dialog.show();
                        signInTheUserByCredentials(null, "");
                    }else {

                        Toast.makeText(OtpActivity.this, "Please Check. Wrong Otp!", Toast.LENGTH_SHORT).show();

                    }
   //             }
                
               


                // startActivity(new Intent(OtpActivity.this,LoginForm.class));
                //  finishAffinity();
            }
        });


    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadimage(PhoneAuthCredential credential) {


        final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                +"."+getFileExtension(Common.userimagemultipart));

        uploadTask = fileReference.putFile(Common.userimagemultipart);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw  task.getException();
                }

                return  fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    signInTheUserByCredentials(credential,mUri);


                } else {
                    dialog.cancel();
                    Toast.makeText(OtpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
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

    private void createtoken(String uid, String image) {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
           // Common.updateToken(OtpActivity.this, instanceIdResult.getToken());
            // inserttoken(newToken);

            registeruser(newToken,"","");

        });
    }

    private void registeruser(String newToken, String uid, String image) {

//        HashMap<String, Object> room = new HashMap<>();
//        room.put("name", name);
//        room.put("date_of_birth", date_of_birth);
//        room.put("phone_number",Common.countrycode + "" + Common.mobilenumber );
//        room.put("emailid", emailid);
//        room.put("gender", gender);
//        room.put("password", password);
//        room.put("image",image );
//        room.put("register_type", "register");
//        room.put("token", newToken);
//        room.put("userid", uid);
//        room.put("country_code", country_code);
//
//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(Common.mobilenumber)
//                .setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<Void> task) {
//
//                dialog.cancel();
//
//                if(task.isSuccessful()){
//                    user.setUserid(String.valueOf(uid));
//                    user.setName(name);
//                    user.setEmailid(emailid);
//                    user.setImage(image);
//                    user.setPhone_number(Common.mobilenumber);
//                    user.setRegistertype("register");
//                    startActivity(new Intent(OtpActivity.this, MainActivity.class));
//                    finishAffinity();
//                } else {
//                    Toast.makeText(OtpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty( "country_code",country_code );
        jsonObject.addProperty( "date_of_birth", date_of_birth );
        jsonObject.addProperty( "emailid", emailid );
        jsonObject.addProperty( "gender", gender );
        jsonObject.addProperty( "name", name );
        jsonObject.addProperty( "password", password );
        jsonObject.addProperty( "phone_number", Common.mobilenumber );
        jsonObject.addProperty( "register_type", "SignUp" );
        jsonObject.addProperty( "token", newToken );



//HomePageOne
        //SaveVideoOne
        apiServise.registeruser( jsonObject )
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();

                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(OtpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                user.setUserid(String.valueOf(response.body().getData().get(0).getId()));
                                user.setName(name);
                                user.setEmailid(emailid);
                                user.setImage(image);
                                user.setPhone_number(Common.mobilenumber);
                                user.setRegistertype("register");
                                startActivity(new Intent(OtpActivity.this, MainActivity.class));
                                finishAffinity();

                            } else {

                                Toast.makeText(OtpActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();


                            }
                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterData> call, Throwable t) {

                        Toast.makeText(OtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );


    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential, String image) {

        createtoken("","");
//        mAuth.signInWithCredential(credential).addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    // dialog.cancel();
//                    Toast.makeText(OtpActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    createtoken(user.getUid(),image);
//                } else {
//                    dialog.cancel();
//                    Toast.makeText(OtpActivity.this, "Otp Wrong Please Check", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }
}