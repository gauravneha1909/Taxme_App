package com.example.gplusin.fragment.business_expense;

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

import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.adapter.AdapterBussiness_expensedetail;
import com.example.gplusin.databinding.ActivityAdhocIncomeHistoryBinding;
import com.example.gplusin.databinding.ActivityBusinessExpenseHistoryBinding;
import com.example.gplusin.pojo.BusinessExpenseOne;
import com.example.gplusin.pojo.Businessexpensepojo;
import com.example.gplusin.retrofit.ApiServise;
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

public class Business_expense_History extends AppCompatActivity {
    ActivityBusinessExpenseHistoryBinding binding;

    ProgressDialog dialog;

    User user;
    ApiServise apiServise;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_expense_history);
        binding = ActivityBusinessExpenseHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        dialog = new ProgressDialog(Business_expense_History.this);
        dialog.setMessage("Please Wait...");
        user=new User(Business_expense_History.this);

        binding.giveloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context1111.clickbuton("1");

                startActivity(new Intent(Business_expense_History.this, Business_expense_Add.class));
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


        apiServise.Business_expensefromid(user.getUserid())
                .enqueue( new Callback<BusinessExpenseOne>() {
                    @Override
                    public void onResponse(Call<BusinessExpenseOne> call, Response<BusinessExpenseOne> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {

                            if (response.body().result.size() > 0) {
                                binding.featureproduct.setVisibility(View.VISIBLE);
                                binding.featureproduct.setAdapter( new AdapterBussiness_expensedetail( Business_expense_History.this,response.body().result ) );

                            }else {

                                binding.featureproduct.setVisibility(View.GONE);
                            }





                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<BusinessExpenseOne> call, Throwable t) {
                        dialog.cancel();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );




//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
////                .child("profiles")
////                .child(typechild)
//                .child("Business_expense")
//                .orderByChild("UserID").equalTo(typechild).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                dialog.cancel();
//
//
//
//
//                Iterator<DataSnapshot> dataSnapshots = snapshot.getChildren().iterator();
//                List<Businessexpensepojo> fcmUsers = new ArrayList<>();
//                while (dataSnapshots.hasNext()) {
//                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
//                    Businessexpensepojo fcmUser = dataSnapshotChild.getValue(Businessexpensepojo.class);
//                    fcmUsers.add(fcmUser);
//
//                }
//
//                // Check your arraylist size and pass to list view like
//                if(fcmUsers.size()>0)
//                {
//                    binding.featureproduct.setVisibility(View.VISIBLE);
//                    binding.featureproduct.setAdapter( new AdapterBussiness_expensedetail( Business_expense_History.this,fcmUsers ) );
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
}