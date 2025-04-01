package com.example.gplusin.fragment.business_expense;

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
import com.example.gplusin.databinding.ActivityBusinessExpenseUpdateBinding;
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

public class Business_expense_Update extends AppCompatActivity {
    ActivityBusinessExpenseUpdateBinding binding;

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
        setContentView(R.layout.activity_business_expense_update);

        binding = ActivityBusinessExpenseUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        storageReference= FirebaseStorage.getInstance().getReference("adhoc_income_proof_image");

        dialog = new ProgressDialog(Business_expense_Update.this);
        dialog.setMessage("Please Wait...");
        user=new User(Business_expense_Update.this);

        apiServise= Common.getAPI();



        binding.expenseName.setText(Common.updatebusiness_expense.expense_name);

        binding.expenseAmount.setText(Common.updatebusiness_expense.expense_amount);


        binding.claimableGst.setText(Common.updatebusiness_expense.claimable_gst);

        binding.txtpaymentdatefirst.setText(DataUtils.formatDate(Common.updatebusiness_expense.Business_expense_Date));
        firstpaymentdate= DataUtils.formatDate(Common.updatebusiness_expense.Business_expense_Date);





        if (Common.updatebusiness_expense.is_gst_paid.equals("0")){
            binding.gstAmount.setText("0");
            binding.gstAmount.setEnabled(false);

            binding.isGstPaid.setText("No");
            taxreducesourceString="No";

        }else {

            binding.gstAmount.setText(Common.updatebusiness_expense.gst_amount);
            binding.gstAmount.setEnabled(true);

            binding.isGstPaid.setText("Yes");
            taxreducesourceString="Yes";

        }


        if (Common.updatebusiness_expense.image_type.equals("image")) {

            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(Common.updatebusiness_expense.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_profile)
                    .centerCrop()
                    .fitCenter()
                    .dontAnimate()
                    .into(binding.imgProfile);

            pdfname="";

            uploadtype="image";

            binding.imgProfile.setVisibility(View.VISIBLE);


        } else if (Common.updatebusiness_expense.image_type.equals("pdf")){

            uploadtype="pdf";

            pdfname=Common.updatebusiness_expense.pdfname;

            binding.imgProfile.setVisibility(View.GONE);
            binding.pdfname.setText(Common.updatebusiness_expense.pdfname);

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



                newFragment.show(Business_expense_Update.this.getSupportFragmentManager(), "datePicker");



            }
        });




        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.expenseName.getText().toString().isEmpty()) {
                    Toast.makeText(Business_expense_Update.this, "Enter Expense Name", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.expenseAmount.getText().toString().isEmpty()) {
                    Toast.makeText(Business_expense_Update.this, "Enter Expense Amount", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (taxreducesourceString.equals("")) {
                    Toast.makeText(Business_expense_Update.this, "Select Is GST Paid?", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (binding.gstAmount.getText().toString().isEmpty()) {
                    Toast.makeText(Business_expense_Update.this, "Enter GST Amount", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.claimableGst.getText().toString().isEmpty()) {
                    Toast.makeText(Business_expense_Update.this, "Enter Claimable GST", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (firstpaymentdate.equals("")) {
                    Toast.makeText(Business_expense_Update.this, "Select Business expense Date", Toast.LENGTH_SHORT ).show();
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

        binding.isGstPaid.setOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder( Business_expense_Update.this );
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
//                            TedBottomPicker.with( Business_expense_Update.this )
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
                            Business_expense_Update.this,
                            Manifest.permission
                                    .READ_EXTERNAL_STORAGE)
                            != PackageManager
                            .PERMISSION_GRANTED) {
                        // When permission is not granted
                        // Result permission
                        ActivityCompat.requestPermissions(
                                Business_expense_Update.this,
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
        AlertDialog.Builder builder = new AlertDialog.Builder( Business_expense_Update.this );
        builder.setTitle( "Select Is GST Paid" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                taxreducesourceString = mStrings[which];
                binding.isGstPaid.setText( mStrings[which] );

                if (mStrings[which].equals("No")){
                    binding.gstAmount.setText("0");
                    binding.gstAmount.setEnabled(false);
//                    binding.gstAmount.setFocusable(false);
//                    binding.gstAmount.setFocusableInTouchMode(true);
                }else {

                    binding.gstAmount.setText(Common.updatebusiness_expense.gst_amount);
                    binding.gstAmount.setEnabled(true);
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
                    Toast.makeText(Business_expense_Update.this, "Failed!", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Business_expense_Update.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });



    }

    private void uploadimage(String mUri) {


        String userid="";

        userid=user.getUserid();

        HashMap<String, Object> room = new HashMap<>();

        String taxdecuted="";
        if (taxreducesourceString.equals("Yes")){

            taxdecuted="1";
        }else {

            taxdecuted="0";

        }

        String dateincome= DataUtils.formatDatehypen(new Date(firstpaymentdate));



        room.put("ExpenseName", binding.expenseName.getText().toString());
        room.put("ExpenseAmount", binding.expenseAmount.getText().toString().trim());
        room.put("IsGSTpaid", taxdecuted);
        room.put("GSTAmount", binding.gstAmount.getText().toString().trim());
        room.put("ClaimableGSTPercentage", binding.claimableGst.getText().toString().trim());
        room.put("UserID", userid);
        room.put("ExpenseID", Common.updatebusiness_expense.keyid);
        room.put("image_type", uploadtype);
        room.put("pdfname", pdfname);
        room.put("ExpenseDate", dateincome);
        room.put("action", "2");





//        room.put("expense_name", binding.expenseName.getText().toString());
//        room.put("expense_amount", binding.expenseAmount.getText().toString().trim());
//        room.put("is_gst_paid", taxreducesourceString);
//        room.put("gst_amount", binding.gstAmount.getText().toString().trim());
//        room.put("claimable_gst", binding.claimableGst.getText().toString().trim());
//        room.put("userid", userid);
//        room.put("keyid", Common.updatebusiness_expense.keyid);
//        room.put("image_type", uploadtype);
//        room.put("pdfname", pdfname);
//        room.put("Business_expense_Date", firstpaymentdate);

        try {
            if (selectFileUri1.toString().isEmpty()) {

                room.put("image", Common.updatebusiness_expense.image);

            } else {

                room.put("image", mUri);


            }
        }catch (Exception e){

            room.put("image", Common.updatebusiness_expense.image);
        }



        dialog.show();

        apiServise.Business_expense(room)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(Business_expense_Update.this, "Success", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(Business_expense_Update.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
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