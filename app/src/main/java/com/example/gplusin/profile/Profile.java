package com.example.gplusin.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.activity.DeleteAccount;

import com.example.gplusin.activity.Splash;
import com.example.gplusin.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {
    ActivityProfileBinding binding;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));


        user=new User(Profile.this);

        setuserdata();


        binding.Logoutqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder( Profile.this );
                builder.setTitle( "Are You Sure " );
                builder.setMessage( "Do you want to Logout?" );
                builder.setNegativeButton( "No",(dialog, i) -> {
                    dialog.dismiss();
                } ).setPositiveButton( "Yes",(dialog, i) -> {
                    User user=new User(  Profile.this );
                    user.setFirstTimeLaunch( true );
                    startActivity( new Intent(  Profile.this, Splash.class ) );
                    finishAffinity();
                    dialog.dismiss();
                } );

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( Color.RED );
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });


        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        binding.updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(Profile.this, EditProfile.class));
            }
        });


        binding.deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Profile.this, DeleteAccount.class));


            }
        });

    }

    private void setuserdata() {

        binding.emailnumber.setText(user.getName());
        binding.username.setText(user.getName());
        binding.emailid.setText(user.getEmailid());
        binding.gender.setText(user.getGender());



        try {
            if (user.getName().contains(" ")) {
                String[] getdata = user.getName().split(" ");

//                TextDrawable drawable = TextDrawable.builder()
//                        .beginConfig()
//                        .textColor(Color.WHITE)
//                        .fontSize(60) /* size in px */
//                        .bold()
//                        .toUpperCase()
//                        .endConfig()
//                        .buildRound(getdata[0].substring(0, 1) + getdata[1].substring(0, 1), Color.parseColor("#022641"));
//
//                binding.image.setImageDrawable(drawable);


            } else {


//                TextDrawable drawable = TextDrawable.builder()
//                        .beginConfig()
//                        .textColor(Color.WHITE)
//                        .fontSize(60) /* size in px */
//                        .bold()
//                        .toUpperCase()
//                        .endConfig()
//                        .buildRound(String.valueOf(user.getName().substring(0, 1)), Color.parseColor("#022641"));
//
//
//                binding.image.setImageDrawable(drawable);
            }
        }catch (Exception e){


        }

//        MaterialTextDrawable.Companion.with(Profile.this)
//                .text(String.valueOf(user.getName().substring(0, 1)))
//                .into(binding.image);

//        Glide.with( Profile.this )
//                .asBitmap()
//                .load( user.getImage() )
//                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .placeholder( R.drawable.ic_profile )
//                .centerCrop()
//                .fitCenter()
//                .dontAnimate()
//                .into( binding.profileimage );
//

//        Glide.with( Profile.this )
//                .asBitmap()
//                .load( user.getImage() )
//                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .centerCrop()
//                .fitCenter()
//                .dontAnimate()
//                .into( binding.image );

    }

    @Override
    protected void onStart() {
        super.onStart();
        setuserdata();
    }
}