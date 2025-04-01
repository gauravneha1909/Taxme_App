package com.example.gplusin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.ActivityHistoricalAddUpdateBinding;
import com.example.gplusin.databinding.ActivityProfileBinding;
import com.example.gplusin.fragment.adhocworkhour.AdhocworkhourActivityUpdate;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historical_add_update extends AppCompatActivity {
    ActivityHistoricalAddUpdateBinding binding;
    User user;
    ProgressDialog dialog1111;

    ApiServise apiServise;

    String action ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_add_update);

        binding = ActivityHistoricalAddUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        user=new User(Historical_add_update.this);

        dialog1111 = new ProgressDialog(Historical_add_update.this);
        dialog1111.setMessage("Please Wait...");


        String typechild="";

        if (user.getRegistertype().equals("google")){

            typechild=user.getUserid();

        }else {

            typechild=user.getPhone_number();

        }


        Log.e("typechild",typechild);
        dialog1111.show();




        apiServise.historical_datafromid(user.getUserid())
                .enqueue( new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        dialog1111.cancel();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {

                                    action ="2";

                                    JSONArray resultArray = jsonObject.getJSONArray("result");
                                    if (resultArray.length() > 0) {
                                        JSONObject financialData = resultArray.getJSONObject(0);
                                        String financialYear = financialData.getString("FinancialYear");
                                        String accPaid = financialData.getString("HistoricalACCpaid");
                                        String incomeEarned = financialData.getString("HistoricalIncomeearned");
                                        String taxPaid = financialData.getString("HistoricalTaxpaid");
                                      //  String userID = financialData.getString("UserID");

                                        // Now you can use the extracted data
                                        Log.d("FinancialData", "Financial Year: " + financialYear);
                                        Log.d("FinancialData", "ACC Paid: " + accPaid);
                                        // Add more log statements or use the data as needed

                                        binding.HistoricalACC.setText(accPaid);
                                        binding.HistoricalIncome.setText(incomeEarned);
                                        binding.HistoricalTax.setText(taxPaid);
                                    }
                                } else {

                                    action="1";

                                    // Handle case where success is false
                                   // Toast.makeText(Historical_add_update.this, , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

                                action="1";
                                e.printStackTrace();
                            }
                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dialog1111.cancel();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );





        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String userid="";

//                if (user.getRegistertype().equals("register")){
//
//                    userid=user.getPhone_number();
//                }else {

                    userid=user.getUserid();

        //        }



                HashMap<String, Object> room = new HashMap<>();
                room.put("HistoricalIncomeearned", binding.HistoricalIncome.getText().toString());
                room.put("HistoricalTaxpaid", binding.HistoricalTax.getText().toString());
                room.put("HistoricalACCpaid", binding.HistoricalACC.getText().toString());
//                room.put("Historical_gST_collection", binding.HistoricalGSTCollection.getText().toString());
//                room.put("Historical_gST_claimed", binding.HistoricalGSTClaimed.getText().toString());
                room.put("FinancialYear", "20242025");
                room.put("UserID", userid);

                room.put("action", action);








                dialog1111.show();


                apiServise.historical_data(room)
                        .enqueue( new Callback<RegisterData>() {
                            @Override
                            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                                dialog1111.cancel();
                                if (response.isSuccessful()) {
                                    if (response.body().getResult().equals( "Success" )) {

                                        Toast.makeText(Historical_add_update.this, "Success", Toast.LENGTH_SHORT).show();
                                        onBackPressed();

                                    } else {
                                        Toast.makeText(Historical_add_update.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {


                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterData> call, Throwable t) {
                                dialog1111.cancel();
                                // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                            }
                        } );


//                FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                        .child("historical_data")
//                        .child(userid)
//                        .updateChildren(room).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//
//                        dialog1111.cancel();
//                        Toast.makeText(Historical_add_update.this, "Success", Toast.LENGTH_SHORT).show();
//                        finish();
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        dialog1111.cancel();
//
//                    }
//                });


            }
        });

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}