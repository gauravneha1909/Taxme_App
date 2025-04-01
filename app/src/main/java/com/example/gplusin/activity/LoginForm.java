package com.example.gplusin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.R;
import com.example.gplusin.databinding.ActivityLoginFormBinding;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DatePickerFragment;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;

public class LoginForm extends AppCompatActivity {
    ActivityLoginFormBinding binding;
    String selectoperator="Male";
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    ProgressDialog dialog;
    String phone="";
    String backendcode="";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Uri selectFileUri1;
    Uri uploaduri=null;
    MultipartBody.Part videophoto = null;
    File file;
    String datedata="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login_form);

        dialog= new ProgressDialog(LoginForm.this);

        dialog.setMessage("Please Wait...");

        binding = ActivityLoginFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

       // binding.male.setBackgroundResource( R.drawable.bg_gender_one );

        // binding.txtCountryName.setText("India");
//        binding.imgCountyFlag.setImageResource(com.yesterselga.countrypicker.R.drawable.flag_nz);
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



        binding.slectgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectgender();
            }
        });



//        binding.male.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.male.setBackgroundResource( R.drawable.bg_gender_one );
//                binding.female.setBackgroundResource( R.drawable.bg_gender );
//                binding.others.setBackgroundResource( R.drawable.bg_gender );
//                selectoperator="Male";
//            }
//        } );
//
//        binding.female.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.male.setBackgroundResource( R.drawable.bg_gender );
//                binding.female.setBackgroundResource( R.drawable.bg_gender_one );
//                binding.others.setBackgroundResource( R.drawable.bg_gender );
//                selectoperator="Female";
//            }
//        } );
//
//        binding.others.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.male.setBackgroundResource( R.drawable.bg_gender );
//                binding.female.setBackgroundResource( R.drawable.bg_gender );
//                binding.others.setBackgroundResource( R.drawable.bg_gender_one );
//                selectoperator="Others";
//            }
//        } );


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent( LoginForm.this, OtpActivity.class );
//
//                startActivity( intent );


                if (binding.etFullName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginForm.this, "Please enter Name", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if(binding.etMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginForm.this, "Please enter phone number", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (!binding.etemailid.getText().toString().trim().matches(emailPattern)) {

                    Toast.makeText(LoginForm.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT ).show();

                    return;
                }

                //binding.txtDate

                if(datedata.isEmpty()) {
                    Toast.makeText(LoginForm.this, "Please Select Data Of Birth", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (binding.password.getText().toString().trim().length() < 6) {

                    Toast.makeText(LoginForm.this, "pls enter more the 6 character in password", Toast.LENGTH_SHORT ).show();

                    return;

                }



                Common.mobilenumber=binding.etMobile.getText().toString();


                callmethod();





            }
        });


        binding.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  showDialog(Date_id);

                DatePickerFragment newFragment = new DatePickerFragment();

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {
                        binding.txtDate.setText(date);
                        datedata=date;
                    }
                });

                newFragment.show(LoginForm.this.getSupportFragmentManager(), "datePicker");



            }
        });

//        binding.imgEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                PermissionListener permissionListener = new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                        TedBottomPicker.with( LoginForm.this )
//                                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
//                                .setSelectedUri( selectFileUri1 )
//                                //.showVideoMedia()
//                                .setPeekHeight( 1200 )
//                                .show( uri -> {
//                                    selectFileUri1 = uri;
//
//                                    Glide.with(getApplicationContext())
//                                            .asBitmap()
//                                            .load( uri )
//                                            .diskCacheStrategy( DiskCacheStrategy.ALL )
//                                            .placeholder( R.drawable.ic_profile )
//                                            .centerCrop()
//                                            .fitCenter()
//                                            .dontAnimate()
//                                            .into( binding.imgProfile );
//                                     file = new File( SiliCompressor.with(LoginForm.this ).compress( com.iceteck.silicompressorr.FileUtils.getPath(
//                                            LoginForm.this, selectFileUri1 ),
//                                            new File( LoginForm.this.getCacheDir(), "temp" ) ) );
//
//                                    // cateimage = MultipartBody.Part.createFormData( "image", fileName,requestFile  );
//
//
//                               //     videophoto = MultipartBody.Part.createFormData( "image", file.getPath(), RequestBody.create( MediaType.parse( "multipart/form-data" ), file ) );
//
//                                  //  File file=new File(String.valueOf(selectFileUri1));
//                                    CropImage.activity( Uri.fromFile(file))
//                                            .setGuidelines( CropImageView.Guidelines.ON )
//                                            .setCropShape( CropImageView.CropShape.OVAL )
//                                            .setActivityTitle( "Crop Image" )
//                                            .setFixAspectRatio( true )
//                                            .setCropMenuCropButtonTitle( "Done" )
//                                            .start(LoginForm.this);
//
//                                } );
//
//                    }
//
//                    @Override
//                    public void onPermissionDenied(List<String> deniedPermissions) {
//
//                    }
//                };
//                checkPermission( permissionListener );
//
//
//
//
//            }
//        });

    }

    private void callmethod() {

        firebaseotp(binding.etMobile.getText().toString());


    }

    private void firebaseotp(String toString) {


        dialog.show();
        phone = toString;
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(binding.txtCountryCode.getText().toString() + toString,
//                60,
//                TimeUnit.SECONDS, LoginForm.this,
//                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//                        String code = phoneAuthCredential.getSmsCode();
//                        //  binding.txtPinEntry.setText(code);
//                        // RequsetSignup();
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                        // hud.dismiss();
//                        dialog.cancel();
//                        Toast.makeText(LoginForm.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("errrr", e.getMessage());
//                    }
//
//                    @Override
//                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        super.onCodeSent(s, forceResendingToken);
//                        Log.e("aaaa",s);
//                        backendcode=s;
                        Common.backendcode="1234";
                        dialog.cancel();
                        Common.countrycode=binding.txtCountryCode.getText().toString();
                        RequsetSignup();
//                    }
//                });






    }

    private void RequsetSignup() {


        Common.userimagemultipart=uploaduri ;

        // startActivity(new Intent(LoginForm.this,OtpActivity.class));


        Intent intent=new Intent( LoginForm.this, OtpActivity.class );
        intent.putExtra( "name",binding.etFullName.getText().toString() );
        intent.putExtra( "emailid",binding.etemailid.getText().toString().trim() );
        intent.putExtra( "gender",selectoperator );
        intent.putExtra( "date_of_birth",binding.txtDate.getText().toString().trim() );
        intent.putExtra( "password",binding.password.getText().toString().trim() );
        intent.putExtra("country_code", binding.txtCountryCode.getText().toString().trim().replace("+",""));

        startActivity( intent );



    }


//    private void checkPermission(PermissionListener permissionListener) {
//        TedPermission.with( LoginForm.this)
//                .setPermissionListener(permissionListener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions( Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
//                .check();
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult( data );
//            if (resultCode == RESULT_OK) {
//                uploaduri = result.getUri();
//                binding.image.setImageURI( resultUri );
//                binding.image.setImageURI( selectFileUri1 );


//                Glide.with(getApplicationContext())
//                        .asBitmap()
//                        .load( uploaduri )
//                        .diskCacheStrategy( DiskCacheStrategy.ALL )
//                        .placeholder( R.drawable.ic_profile )
//                        .centerCrop()
//                        .fitCenter()
//                        .dontAnimate()
//                        .into( binding.imgProfile );







//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                // Toast.makeText( this, "" + error.getMessage(), Toast.LENGTH_SHORT ).show();
//            }
//        }
    }

    private void selectgender() {


        final String[] mStrings = {"Male", "Female","Others"};
        AlertDialog.Builder builder = new AlertDialog.Builder( LoginForm.this );
        builder.setTitle( "Select Your Gender" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                selectoperator=mStrings[which];
                dialog.dismiss();
                binding.slectgender.setText( mStrings[which] );
            }
        } );
        builder.show();



    }
}