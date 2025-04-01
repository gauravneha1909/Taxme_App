package com.example.gplusin.fragment.adhoc_income;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.ActivityAdhocIncomeAddBinding;
import com.example.gplusin.databinding.ActivityAdhocIncomeUpdate2Binding;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DataUtils;
import com.example.gplusin.utils.DatePickerFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdhocIncomeUpdate extends AppCompatActivity {

    ActivityAdhocIncomeUpdate2Binding binding;

    String taxreducesourceString="",uploadtype="",pdfname="",firstpaymentdate="";

    ProgressDialog dialog;

    User user;
    Uri selectFileUri1=null;
    File file;
    StorageReference storageReference;
    StorageTask uploadTask;
    HashMap<String, Object> room;
    ActivityResultLauncher<Intent> resultLauncher;

    ApiServise apiServise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc_income_update2);

        binding = ActivityAdhocIncomeUpdate2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        apiServise= Common.getAPI();

        storageReference= FirebaseStorage.getInstance().getReference("adhoc_income_proof_image");

        dialog = new ProgressDialog(AdhocIncomeUpdate.this);
        dialog.setMessage("Please Wait...");
        user=new User(AdhocIncomeUpdate.this);



        binding.incomeSource.setText(Common.updateadhocincome.income_source);

        binding.incomeAmount.setText(Common.updateadhocincome.income_amount);



        binding.txtpaymentdatefirst.setText(DataUtils.formatDate(Common.updateadhocincome.Adhoc_Income_Date));
        firstpaymentdate=DataUtils.formatDate(Common.updateadhocincome.Adhoc_Income_Date);





        if (Common.updateadhocincome.taxdeductedsource.equals("0")){
            binding.taxReduce.setText("0");
            binding.taxReduce.setEnabled(false);
            binding.taxdeductedsource.setText("No");
            taxreducesourceString="No";


        }else {

            binding.taxReduce.setText(Common.updateadhocincome.tax_reduce_amount);
            binding.taxReduce.setEnabled(true);

            binding.taxdeductedsource.setText("Yes");
            taxreducesourceString="Yes";

        }


        if (Common.updateadhocincome.image_type.equals("image")) {

            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(Common.updateadhocincome.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_profile)
                    .centerCrop()
                    .fitCenter()
                    .dontAnimate()
                    .into(binding.imgProfile);

            pdfname="";

            uploadtype="image";

            binding.imgProfile.setVisibility(View.VISIBLE);



        } else if (Common.updateadhocincome.image_type.equals("pdf")){

            uploadtype="pdf";

            pdfname=Common.updateadhocincome.pdfname;

            binding.imgProfile.setVisibility(View.GONE);
            binding.pdfname.setText(Common.updateadhocincome.pdfname);

        }



        // Initialize result launcher
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result)
                    {
                        // Initialize result data
                        Intent data = result.getData();
                        // check condition
                        if (data != null) {
                            // When data is not equal to empty
                            // Get PDf uri
                            Uri sUri = data.getData();
                            // set Uri on text view
//                            tvUri.setText(Html.fromHtml(
//                                    "<big><b>PDF Uri</b></big><br>"
//                                            + sUri));

                            // Get PDF path
                            String sPath =sUri.getPath();
                            // Set path on text view
//                            tvPath.setText(Html.fromHtml(
//                                    "<big><b>PDF Path</b></big><br>"
//                                            + sPath));

                            Log.e("gsdhgsdhgf",""+sUri+" "+sPath);

                            selectFileUri1= sUri;
                            uploadtype="pdf";

                            binding.imgProfile.setVisibility(View.GONE);
                            binding.pdfname.setText(sPath);

                            pdfname=sPath;


                        }
                    }
                });


        binding.firstpaymentdateline.setOnClickListener(new View.OnClickListener() {
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
                        binding.txtpaymentdatefirst.setText(date);
                        firstpaymentdate=date;

                    }
                });



                newFragment.show(AdhocIncomeUpdate.this.getSupportFragmentManager(), "datePicker");



            }
        });




        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.incomeSource.getText().toString().isEmpty()) {
                    Toast.makeText(AdhocIncomeUpdate.this, "Enter Income Source", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.incomeAmount.getText().toString().isEmpty()) {
                    Toast.makeText(AdhocIncomeUpdate.this, "Enter Income Amount", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (taxreducesourceString.equals("")) {
                    Toast.makeText(AdhocIncomeUpdate.this, "Select Tax Deducted at Source", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (binding.taxReduce.getText().toString().isEmpty()) {
                    Toast.makeText(AdhocIncomeUpdate.this, "Enter Tax Reduce", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (firstpaymentdate.equals("")) {
                    Toast.makeText(AdhocIncomeUpdate.this, "Select Adhoc Income Date", Toast.LENGTH_SHORT ).show();
                    return;
                }


                try {

                    if (selectFileUri1.toString().isEmpty()) {
                        uploadimage("");
                    } else {

                        uploadimagefirebase();

                    }
                }catch (Exception e){

                    uploadimage("");
                }



            }
        });

        binding.taxdeductedsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callgstregister();
            }
        });



        binding.imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                PermissionListener permissionListener = new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                        TedBottomPicker.with( EditProfile.this )
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
//                                    file = new File( SiliCompressor.with(EditProfile.this ).compress( com.iceteck.silicompressorr.FileUtils.getPath(
//                                            EditProfile.this, selectFileUri1 ),
//                                            new File( EditProfile.this.getCacheDir(), "temp" ) ) );
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
//                                            .start(EditProfile.this);
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


                selectimagetype();


            }
        });


    }

    private void selectimagetype() {


        final String[] mStrings = {"Image", "Pdf"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AdhocIncomeUpdate.this );
        builder.setTitle( "Select Option" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                dialog.dismiss();
                if (mStrings[which].equals("Image")){

//                    PermissionListener permissionListener = new PermissionListener() {
//                        @Override
//                        public void onPermissionGranted() {
//                            TedBottomPicker.with( AdhocIncomeUpdate.this )
//                                    //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
//                                    .setSelectedUri( selectFileUri1 )
//                                    //.showVideoMedia()
//                                    .setPeekHeight( 1200 )
//                                    .show( uri -> {
//                                        selectFileUri1 = uri;
//
//                                        uploadtype="image";
//
//                                        pdfname="";
//
//                                        binding.imgProfile.setVisibility(View.VISIBLE);
//                                        binding.pdfname.setText("");
//
//                                        Glide.with(getApplicationContext())
//                                                .asBitmap()
//                                                .load( uri )
//                                                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                                                .placeholder( R.drawable.ic_profile )
//                                                .centerCrop()
//                                                .fitCenter()
//                                                .dontAnimate()
//                                                .into( binding.imgProfile );
////                                        file = new File( SiliCompressor.with(AdhocIncomeAdd.this ).compress( com.iceteck.silicompressorr.FileUtils.getPath(
////                                                AdhocIncomeAdd.this, selectFileUri1 ),
////                                                new File( AdhocIncomeAdd.this.getCacheDir(), "temp" ) ) );
//
//                                        // cateimage = MultipartBody.Part.createFormData( "image", fileName,requestFile  );
//
//
//                                        //     videophoto = MultipartBody.Part.createFormData( "image", file.getPath(), RequestBody.create( MediaType.parse( "multipart/form-data" ), file ) );
//
//                                        //  File file=new File(String.valueOf(selectFileUri1));
////                                        CropImage.activity( Uri.fromFile(file))
////                                                .setGuidelines( CropImageView.Guidelines.ON )
////                                                .setCropShape( CropImageView.CropShape.OVAL )
////                                                .setActivityTitle( "Crop Image" )
////                                                .setFixAspectRatio( true )
////                                                .setCropMenuCropButtonTitle( "Done" )
////                                                .start(EditProfile.this);
//
//                                    } );
//
//                        }
//
//                        @Override
//                        public void onPermissionDenied(List<String> deniedPermissions) {
//
//                        }
//                    };
//                    checkPermission( permissionListener );


                }else if (mStrings[which].equals("Pdf")){



                    if (ActivityCompat.checkSelfPermission(
                            AdhocIncomeUpdate.this,
                            Manifest.permission
                                    .READ_EXTERNAL_STORAGE)
                            != PackageManager
                            .PERMISSION_GRANTED) {
                        // When permission is not granted
                        // Result permission
                        ActivityCompat.requestPermissions(
                                AdhocIncomeUpdate.this,
                                new String[] {
                                        Manifest.permission
                                                .READ_EXTERNAL_STORAGE },
                                1);
                    }
                    else {
                        // When permission is granted
                        // Create method
                        selectPDF();
                    }

                }

            }
        } );
        builder.show();



    }


    private void callgstregister() {


        final String[] mStrings = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AdhocIncomeUpdate.this );
        builder.setTitle( "Select Tax Deducted at Source" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");

                taxreducesourceString = mStrings[which];
                binding.taxdeductedsource.setText( mStrings[which] );

                if (mStrings[which].equals("No")){
                    binding.taxReduce.setText("0");
                    binding.taxReduce.setEnabled(false);
//                    binding.gstAmount.setFocusable(false);
//                    binding.gstAmount.setFocusableInTouchMode(true);
                }else {

                    binding.taxReduce.setText(Common.updateadhocincome.tax_reduce_amount);
                    binding.taxReduce.setEnabled(true);
//                    binding.gstAmount.setFocusable(true);
//                    binding.gstAmount.setFocusableInTouchMode(false );

                }

                dialog.dismiss();
            }
        } );
        builder.show();


    }






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
//                Glide.with(getApplicationContext())
//                        .asBitmap()
//                        .load( uploaduri )
//                        .diskCacheStrategy( DiskCacheStrategy.ALL )
//                        .placeholder( R.drawable.ic_profile )
//                        .centerCrop()
//                        .fitCenter()
//                        .dontAnimate()
//                        .into( binding.imgProfile );
//
//                uploadimagefirebase(uploaduri);
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



    private void uploadimagefirebase() {

        dialog.show();
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                +"."+getFileExtension(selectFileUri1));

        uploadTask = fileReference.putFile(selectFileUri1);
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

                    uploadimage(mUri);


                } else {
                    dialog.cancel();
                    Toast.makeText(AdhocIncomeUpdate.this, "Failed!", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdhocIncomeUpdate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });



    }

    private void uploadimage(String mUri) {


//        String userid="";
//
//        if (user.getRegistertype().equals("register")){
//
//            userid=user.getPhone_number();
//        }else {
//
//            userid=user.getUserid();
//
//        }


        HashMap<String, Object> room = new HashMap<>();

        String taxdecuted="";
        if (taxreducesourceString.equals("Yes")){

            taxdecuted="1";
        }else {

            taxdecuted="0";

        }

        String dateincome= DataUtils.formatDatehypen(new Date(firstpaymentdate));


        room.put("IncomeName", binding.incomeSource.getText().toString());
        room.put("IncomeAmount", binding.incomeAmount.getText().toString().trim());
        room.put("IstaxDeducted", taxdecuted);
        room.put("TDSAmount", binding.taxReduce.getText().toString().trim());
        room.put("UserID", user.getUserid());
        room.put("IncomeID", Common.updateadhocincome.keyid);
        room.put("image_type", uploadtype);
        room.put("pdfname", pdfname);
        room.put("IncomeDate", dateincome);
        room.put("action", "2");
//        room.put("Isdeleted", "N");





//        room.put("income_source", binding.incomeSource.getText().toString());
//        room.put("income_amount", binding.incomeAmount.getText().toString().trim());
//        room.put("taxdeductedsource", taxreducesourceString);
//        room.put("tax_reduce_amount", binding.taxReduce.getText().toString().trim());
//        room.put("userid", userid);
//        room.put("keyid", Common.updateadhocincome.keyid);
//        room.put("image_type", uploadtype);
//        room.put("pdfname", pdfname);
//        room.put("Adhoc_Income_Date", firstpaymentdate);

        try {
            if (selectFileUri1.toString().isEmpty()) {

                room.put("image", Common.updateadhocincome.image);

            } else {

                room.put("image", mUri);


            }
        }catch (Exception e){

            room.put("image", Common.updateadhocincome.image);
        }



        dialog.show();

        apiServise.adhoc_income(room)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(AdhocIncomeUpdate.this, "Success", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(AdhocIncomeUpdate.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
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


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    private void selectPDF() {
        // Initialize intent
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        // check condition
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF();
        }
        else {
            // When permission is denied
            // Display toast
            Toast
                    .makeText(getApplicationContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

}