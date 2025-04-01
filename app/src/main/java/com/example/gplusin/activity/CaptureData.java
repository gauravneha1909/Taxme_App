package com.example.gplusin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gplusin.MainActivity;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.ActivityCaptureDataBinding;
import com.example.gplusin.databinding.ActivityLoginFormBinding;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DatePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureData extends AppCompatActivity {
    ActivityCaptureDataBinding binding;
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
    HashMap<String, Object> room;
    String image="",name="",emailid="",uid="",type="";
    User user;
    String datedata="";

    ApiServise apiServise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_data);
        binding = ActivityCaptureDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();
        dialog= new ProgressDialog(CaptureData.this);

        user = new User(CaptureData.this);

        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        image=intent.getStringExtra("image");
        emailid=intent.getStringExtra("emailid");
        uid=intent.getStringExtra("uid");
        type=intent.getStringExtra("type");

//        Glide.with(getApplicationContext())
//                .asBitmap()
//                .load( image )
//                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .placeholder( R.drawable.ic_profile )
//                .centerCrop()
//                .fitCenter()
//                .dontAnimate()
//                .into( binding.imgProfile );


        dialog.setMessage("Please Wait...");



       // binding.male.setBackgroundResource( R.drawable.bg_gender_one );

        // binding.txtCountryName.setText("India");
       // binding.imgCountyFlag.setImageResource(com.yesterselga.countrypicker.R.drawable.flag_nz);
        binding.txtCountryCode.setText("+64");

        binding.etFullName.setText(name);
        binding.etemailid.setText(emailid);




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
        binding.linFrequency11111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

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
                    Toast.makeText(CaptureData.this, "Please enter Name", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if(binding.etMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CaptureData.this, "Please enter phone number", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (!binding.etemailid.getText().toString().trim().matches(emailPattern)) {

                    Toast.makeText(CaptureData.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT ).show();

                    return;
                }


                if(datedata.isEmpty()) {
                    Toast.makeText(CaptureData.this, "Please Select Data Of Birth", Toast.LENGTH_SHORT ).show();
                    return;
                }


//                if (binding.password.getText().toString().length() < 6) {
//
//                    Toast.makeText(CaptureData.this, "pls enter more the 6 character in password", Toast.LENGTH_SHORT ).show();
//
//                    return;
//
//                }





                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(CaptureData.this, instanceIdResult -> {
                    String newToken = instanceIdResult.getToken();
                    Log.e("newToken", newToken);
                    // Common.updateToken(OtpActivity.this, instanceIdResult.getToken());
                    // inserttoken(newToken);

                    registeruser(newToken);

                });





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

                newFragment.show(CaptureData.this.getSupportFragmentManager(), "datePicker");



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
//                        TedBottomPicker.with( CaptureData.this )
//                                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
//                                .setSelectedUri( selectFileUri1 )
//                                //.showVideoMedia()
//                                .setPeekHeight( 1200 )
//                                .show( uri -> {
//                                    selectFileUri1 = uri;
//
//                                    Glide.with(CaptureData.this)
//                                            .asBitmap()
//                                            .load( uri )
//                                            .diskCacheStrategy( DiskCacheStrategy.ALL )
//                                            .placeholder( R.drawable.ic_profile )
//                                            .centerCrop()
//                                            .fitCenter()
//                                            .dontAnimate()
//                                            .into( binding.imgProfile );
//                                    file = new File( SiliCompressor.with(CaptureData.this ).compress( com.iceteck.silicompressorr.FileUtils.getPath(
//                                            CaptureData.this, selectFileUri1 ),
//                                            new File( CaptureData.this.getCacheDir(), "temp" ) ) );
//
//                                    // cateimage = MultipartBody.Part.createFormData( "image", fileName,requestFile  );
//
//
//                                    //     videophoto = MultipartBody.Part.createFormData( "image", file.getPath(), RequestBody.create( MediaType.parse( "multipart/form-data" ), file ) );
//
//                                    //  File file=new File(String.valueOf(selectFileUri1));
//                                    CropImage.activity( Uri.fromFile(file))
//                                            .setGuidelines( CropImageView.Guidelines.ON )
//                                            .setCropShape( CropImageView.CropShape.OVAL )
//                                            .setActivityTitle( "Crop Image" )
//                                            .setFixAspectRatio( true )
//                                            .setCropMenuCropButtonTitle( "Done" )
//                                            .start(CaptureData.this);
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

    private void registeruser(String tokenfirebase) {


//        room = new HashMap<>();
//        room.put("name", binding.etFullName.getText().toString());
//        room.put("date_of_birth", binding.txtDate.getText().toString().trim());
//        room.put("phone_number", binding.etMobile.getText().toString().trim() );
//        room.put("emailid", binding.etemailid.getText().toString().trim());
//        room.put("gender", selectoperator);
//        room.put("password", "" );
//        room.put("image",image );
//        room.put("register_type", type);
//        room.put("token", tokenfirebase);
//        room.put("userid", uid);
//        room.put("country_code",binding.txtCountryCode.getText().toString().trim() );
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
//                    user.setName(binding.etFullName.getText().toString());
//                    user.setEmailid(binding.etemailid.getText().toString().trim());
//                    user.setImage(image);
//                    user.setPhone_number(binding.etMobile.getText().toString().trim());
//                    user.setRegistertype("google");
//                    startActivity(new Intent(CaptureData.this, MainActivity.class));
//                    finishAffinity();
//                } else {
//                    Toast.makeText(CaptureData.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty( "country_code",binding.txtCountryCode.getText().toString().trim().replace("+","") );
        jsonObject.addProperty( "date_of_birth", binding.txtDate.getText().toString().trim() );
        jsonObject.addProperty( "emailid", binding.etemailid.getText().toString().trim() );
        jsonObject.addProperty( "gender", selectoperator );
        jsonObject.addProperty( "name", binding.etFullName.getText().toString() );
        jsonObject.addProperty( "password", "" );
        jsonObject.addProperty( "phone_number",binding.etMobile.getText().toString().trim() );
        jsonObject.addProperty( "register_type", type );
        jsonObject.addProperty( "token", tokenfirebase );

        apiServise.registeruser( jsonObject )
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();

                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(CaptureData.this, "Success", Toast.LENGTH_SHORT).show();
                                user.setUserid(String.valueOf(response.body().getData().get(0).getId()));
                                user.setName(name);
                                user.setEmailid(emailid);
                                user.setImage(image);
                                user.setPhone_number(Common.mobilenumber);
                                user.setRegistertype(type);
                                startActivity(new Intent(CaptureData.this, MainActivity.class));
                                finishAffinity();

                            } else {

                                Toast.makeText(CaptureData.this, "Success", Toast.LENGTH_SHORT).show();
                                user.setUserid(String.valueOf(response.body().getData().get(0).getId()));
                                user.setName(name);
                                user.setEmailid(emailid);
                                user.setImage(image);
                                user.setPhone_number(Common.mobilenumber);
                                user.setRegistertype(type);
                                startActivity(new Intent(CaptureData.this, MainActivity.class));
                                finishAffinity();


                            }
                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterData> call, Throwable t) {

                        Toast.makeText(CaptureData.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );


    }



//    private void checkPermission(PermissionListener permissionListener) {
//        TedPermission.with( CaptureData.this)
//                .setPermissionListener(permissionListener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions( Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
//                .check();
//    }



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult( requestCode, resultCode, data );
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult( data );
//            if (resultCode == RESULT_OK) {
//                uploaduri = result.getUri();
////                binding.image.setImageURI( resultUri );
////                binding.image.setImageURI( selectFileUri1 );
//
//
//                Glide.with(CaptureData.this)
//                        .asBitmap()
//                        .load( uploaduri )
//                        .diskCacheStrategy( DiskCacheStrategy.ALL )
//                        .placeholder( R.drawable.ic_profile )
//                        .centerCrop()
//                        .fitCenter()
//                        .dontAnimate()
//                        .into( binding.imgProfile );
//
//
//
//
//
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                // Toast.makeText( this, "" + error.getMessage(), Toast.LENGTH_SHORT ).show();
//            }
//        }
//    }

    private void selectgender() {


        final String[] mStrings = {"Male", "Female","Others"};
        AlertDialog.Builder builder = new AlertDialog.Builder( CaptureData.this );
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