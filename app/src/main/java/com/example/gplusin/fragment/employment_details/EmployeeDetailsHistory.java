package com.example.gplusin.fragment.employment_details;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.gplusin.AdapterEmployeedetail;
import com.example.gplusin.Modal;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.databinding.AddUpdateFragmentBinding;
import com.example.gplusin.fragment.addupdate.AddUpdateViewModel;
import com.example.gplusin.interface111111.Clickemployedetails;
import com.example.gplusin.pojo.EmployeeDetailsMainOne;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetailsHistory extends AppCompatActivity {

    private AddUpdateViewModel mViewModel;

    Clickemployedetails context1111;
    AddUpdateFragmentBinding binding;

    ProgressDialog dialog;

    User user;
    ApiServise apiServise;


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        context1111=(Clickemployedetails) context;
//
//
//    }


    //    public AddUpdateFragment() {
//
//    }

//    public static AddUpdateFragment newInstance() {
//        return new AddUpdateFragment(Context mainActivity);
//    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
//        binding = AddUpdateFragmentBinding.inflate(inflater, container, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_leave);

        binding = AddUpdateFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.changeColor));

        dialog = new ProgressDialog(EmployeeDetailsHistory.this);
        dialog.setMessage("Please Wait...");
        user=new User(EmployeeDetailsHistory.this);



        binding.giveloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(EmployeeDetailsHistory.this, AddEmployeeDetaileFragment.class));
            }
        });

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


      //  return binding.getRoot();
    }


    private void callhistoricaldata() {



        dialog.show();

        HashMap<String, Object> room = new HashMap<>();



        User user=new User(this);
        room.put("EmployerName", null);
        room.put("EmploymentStartDate", null);
        room.put("EmploymentEndDate", null);
        room.put("StandardDailyHours",null);
        room.put("Paymentrateamount", null);
        room.put("Paymentratefrequency", null);
        room.put("IsGSTregistered", null);
        room.put("WitholdingTaxRate",null );
        room.put("UserID", user.getUserid());
        room.put("FirstPaymentDate",null);
        room.put("PaymentFrequency",null);
        room.put("EmploymentID", null);
        room.put("EmploymentRegion", null);
        room.put("ActionType", "2");



        apiServise.useremploymentmainfromid(room)
                .enqueue( new Callback<EmployeeDetailsMainOne>() {
                    @Override
                    public void onResponse(Call<EmployeeDetailsMainOne> call, Response<EmployeeDetailsMainOne> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {

                            if (response.body().result.size() > 0) {
                                binding.featureproduct.setVisibility(View.VISIBLE);
                                binding.featureproduct.setAdapter( new AdapterEmployeedetail( EmployeeDetailsHistory.this,response.body().result ) );
                                // Add more log statements or use the data as needed
                            }else {

                                binding.featureproduct.setVisibility(View.GONE);
                            }





                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeDetailsMainOne> call, Throwable t) {
                        dialog.cancel();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );

    }



    @Override
    protected void onStart() {
        super.onStart();

        callhistoricaldata();
    }
}