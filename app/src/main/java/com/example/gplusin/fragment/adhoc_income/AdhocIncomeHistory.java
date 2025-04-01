package com.example.gplusin.fragment.adhoc_income;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.adapter.AdapterAdhocIncomedetail;
import com.example.gplusin.databinding.ActivityAdhocDetaileHistoryBinding;
import com.example.gplusin.databinding.ActivityAdhocIncomeHistoryBinding;
import com.example.gplusin.databinding.AddUpdateFragmentBinding;
import com.example.gplusin.pojo.AdhocIncomepojo;
import com.example.gplusin.pojo.Adhoc_incomeOne;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.service.DownloadService;
import com.example.gplusin.utils.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdhocIncomeHistory extends AppCompatActivity {
    ActivityAdhocIncomeHistoryBinding binding;

    ProgressDialog dialog;

    User user;
    ApiServise apiServise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc_income_history);

        binding = ActivityAdhocIncomeHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        Common.adhocincomecontex= AdhocIncomeHistory.this;


        dialog = new ProgressDialog(AdhocIncomeHistory.this);
        dialog.setMessage("Please Wait...");
        user=new User(AdhocIncomeHistory.this);

        binding.giveloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context1111.clickbuton("1");

                startActivity(new Intent(AdhocIncomeHistory.this, AdhocIncomeAdd.class));
            }
        });

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void callhistoricaldata() {

        String typechild="";

        if (user.getRegistertype().equals("google")){

            typechild=user.getUserid();

        }else {

            typechild=user.getPhone_number();

        }


        Log.e("typechild",typechild);
        dialog.show();





        apiServise.adhoc_incomefromid(user.getUserid())
                .enqueue( new Callback<Adhoc_incomeOne>() {
                    @Override
                    public void onResponse(Call<Adhoc_incomeOne> call, Response<Adhoc_incomeOne> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {

                            if (response.body().result.size() > 0) {
                                binding.featureproduct.setVisibility(View.VISIBLE);
                                binding.featureproduct.setAdapter( new AdapterAdhocIncomedetail( AdhocIncomeHistory.this,response.body().result ) );
                            }else {

                                binding.featureproduct.setVisibility(View.GONE);
                            }





                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<Adhoc_incomeOne> call, Throwable t) {
                        dialog.cancel();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );




//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
////                .child("profiles")
////                .child(typechild)
//                .child("adhoc_income").orderByChild("UserID").equalTo(typechild).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                dialog.cancel();
//
//
//
//
//                Iterator<DataSnapshot> dataSnapshots = snapshot.getChildren().iterator();
//                List<AdhocIncomepojo> fcmUsers = new ArrayList<>();
//                while (dataSnapshots.hasNext()) {
//                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
//                    AdhocIncomepojo fcmUser = dataSnapshotChild.getValue(AdhocIncomepojo.class);
//                    fcmUsers.add(fcmUser);
//
//                }
//
//                // Check your arraylist size and pass to list view like
//                if(fcmUsers.size()>0)
//                {
//                    binding.featureproduct.setVisibility(View.VISIBLE);
//                    binding.featureproduct.setAdapter( new AdapterAdhocIncomedetail( AdhocIncomeHistory.this,fcmUsers ) );
//
//                }else
//                {
//                    // Display dialog for there is no user available.
//                    binding.featureproduct.setVisibility(View.GONE);
//                }
//
//
//                //   for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {
//
////                if(snapshot.child("employeename").exists()) {
////
////                    //  String name = snapshot.child("name").getValue(String.class);
////
////                      Toast.makeText(getContext(), "true"+snapshot, Toast.LENGTH_SHORT).show();
////                }else {
////
////
////                    //  Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
////
//////                    if (datachanege==true) {
//////                        showhistoricaldata();
//////                        datachanege=false;
//////                    }
////
////                }
//                //   }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                dialog.cancel();
//            }
//        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        callhistoricaldata();
    }

    public static class DownloadReceiver extends ResultReceiver {

        Context context;
        ProgressDialog progressDialog ;

        public DownloadReceiver(Handler handler, Context context) {
            super(handler);
            this.context = context;

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            super.onReceiveResult(resultCode, resultData);

            if (resultCode == DownloadService.UPDATE_PROGRESS) {


                int progress = resultData.getInt("progress"); //get the progress
                progressDialog.setProgress(progress);
                progressDialog.setMessage("Images Is Downloading");
                progressDialog.show();

                if (progress == 100) {

                    progressDialog.dismiss();
                    Toast.makeText(context, "Download Complete Go To Gln_Folder", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}