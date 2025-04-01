package com.example.gplusin.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.gplusin.databinding.ActivityChooseLoginPageBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;

public class ChooseLoginPage extends AppCompatActivity {

    ActivityChooseLoginPageBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    HashMap<String, Object> room;
    int RC_SIGN_IN = 11;
    String name="",emailid="",image="";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_choose_login_page);

        binding = ActivityChooseLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        user = new User(ChooseLoginPage.this);

        dialog = new ProgressDialog(ChooseLoginPage.this);
        dialog.setMessage("Please Wait...");

        mAuth = FirebaseAuth.getInstance();




//        LoginManager.getInstance().registerCallback(mCallbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                        dialog.cancel();
//                        Toast.makeText(ChooseLoginPage.this, "Cancel", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                        dialog.cancel();
//                        Toast.makeText(ChooseLoginPage.this, ""+exception, Toast.LENGTH_SHORT).show();
//                    }
//                });



        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseLoginPage.this,LoginForm.class));
                overridePendingTransition(0,0);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseLoginPage.this, UserLogin.class));
                overridePendingTransition(0,0);
            }
        });

        binding.googleauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(ChooseLoginPage.this, gso);


                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        binding.facebookauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            authWithGoogle(account.getIdToken());
        }
    }


    void authWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            name=user.getDisplayName();
                            emailid=user.getEmail();
                            image=user.getPhotoUrl().toString();

                           // createtoken(user.getUid(),user.getPhotoUrl().toString());

                            dialog.cancel();
                            Intent intent = new Intent(ChooseLoginPage.this,CaptureData.class);
                            intent.putExtra("name",name);
                            intent.putExtra("emailid",emailid);
                            intent.putExtra("image",image);
                            intent.putExtra("uid",user.getUid());
                            intent.putExtra("type","google");
                            startActivity(intent);


                            //Log.e("profile", user.getPhotoUrl().toString());
                        } else {
                            Log.e("err", task.getException().getLocalizedMessage());
                        }
                    }
                });
    }


//    private void createtoken(String uid, String image) {
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
//            String newToken = instanceIdResult.getToken();
//            Log.e("newToken", newToken);
//            // Common.updateToken(OtpActivity.this, instanceIdResult.getToken());
//            // inserttoken(newToken);
//
//            registeruser(newToken,uid,image);
//
//        });
//    }

//    private void registeruser(String newToken, String uid, String image) {
//
//
//        room = new HashMap<>();
//        room.put("name", name);
//        room.put("date_of_birth", "");
//        room.put("phone_number", "" );
//        room.put("emailid", emailid);
//        room.put("gender", "");
//        room.put("password", "");
//        room.put("image",image );
//        room.put("register_type", "google");
//        room.put("token", newToken);
//        room.put("userid", uid);
//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(uid)
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
//                    user.setPhone_number("");
//                    user.setRegistertype("google");
//                    startActivity(new Intent(ChooseLoginPage.this, MainActivity.class));
//                    finishAffinity();
//                } else {
//                    Toast.makeText(ChooseLoginPage.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }


}