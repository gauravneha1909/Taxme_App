package com.example.gplusin.fragment.adhocworkhour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.Modal;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.adapter.LeaveDateAdapter;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.ActivityAdhocIncomeBinding;
import com.example.gplusin.pojo.EmployeeDetailsMainOne;
import com.example.gplusin.pojo.LeaveData;
import com.example.gplusin.pojo.UserEmploymentadhocworkhourspojo;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DataUtils;
import com.example.gplusin.utils.DatePickerFragment;
import com.example.gplusin.utils.DatePickerFragmentFirstDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdhocworkhourActivity extends AppCompatActivity {

    ActivityAdhocIncomeBinding binding;

    List<LeaveData> leaveData=new ArrayList<>();
    List<UserEmploymentadhocworkhourspojo> useremploymentleaveschedulepojos=new ArrayList<>();

    static final long ONE_DAY = 24 * 60 * 60 * 1000L;

    String startdate="",enddate="",employeenamid="";
    ProgressDialog dialog;

    User user;

    String employeenametextstring="";

    String employeenamstartdate="",employeenamenddate="";
    String key;

    ApiServise apiServise;
    final int[] checkedItem = {-1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc_income);


        binding = ActivityAdhocIncomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        key = database.getReference().push().getKey();
        Log.e("bvxbvx",key);


        dialog = new ProgressDialog(AdhocworkhourActivity.this);
        dialog.setMessage("Please Wait...");
        user=new User(AdhocworkhourActivity.this);

        binding.startdateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  showDialog(Date_id);

                if (employeenamstartdate.equals("")) {
                    Toast.makeText(AdhocworkhourActivity.this, "Select Employment Name", Toast.LENGTH_SHORT ).show();
                    return;
                }

                DatePickerFragmentFirstDate newFragment = new DatePickerFragmentFirstDate();

//                String aaaaaa= getDatesBetween(startdate,enddate);
//                Toast.makeText(AddEmployeeDetaileFragment.this, ""+aaaaaa, Toast.LENGTH_SHORT).show();
//                getDatesBetween(startdate,enddate)

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                b.putString("startdate", employeenamstartdate);
                b.putString("enddate",employeenamenddate );
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {
                        binding.txtstartdate.setText(date);
                        startdate=date;


                        if (startdate.equals("") ){

                            //  Toast.makeText(LeaveActivity.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();

                        }else if (enddate.equals("")){

                            //  Toast.makeText(LeaveActivity.this, "Please Select End Date", Toast.LENGTH_SHORT).show();

                        }else {

                            leaveData.clear();
                            setdateselect();
                        }
                    }
                });

                newFragment.show(AdhocworkhourActivity.this.getSupportFragmentManager(), "datePicker");



            }
        });



        binding.employeenddateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startdate.equals("")) {
                    Toast.makeText(AdhocworkhourActivity.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }


                DatePickerFragmentFirstDate newFragment = new DatePickerFragmentFirstDate();

//                String aaaaaa= getDatesBetween(startdate,enddate);
//                Toast.makeText(AddEmployeeDetaileFragment.this, ""+aaaaaa, Toast.LENGTH_SHORT).show();
//                getDatesBetween(startdate,enddate)

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                b.putString("startdate", startdate);
                b.putString("enddate",employeenamenddate );
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {
                        binding.txtendDate.setText(date);
                        enddate=date;




                        if (startdate.equals("") ){

                            Toast.makeText(AdhocworkhourActivity.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();

                        }else if (enddate.equals("")){

                            Toast.makeText(AdhocworkhourActivity.this, "Please Select End Date", Toast.LENGTH_SHORT).show();

                        }else {

                            leaveData.clear();
                            setdateselect();
                        }
                    }
                });

                newFragment.show(AdhocworkhourActivity.this.getSupportFragmentManager(), "datePicker");



            }
        });

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.linselectemployeename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callhistoricaldata();
            }
        });


//        binding.submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (startdate.equals("") ){
//
//                    Toast.makeText(LeaveActivity.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();
//
//                }else if (enddate.equals("")){
//
//                    Toast.makeText(LeaveActivity.this, "Please Select End Date", Toast.LENGTH_SHORT).show();
//
//                }else {
//
//                    setdateselect();
//                }
//
//            }
//        });
    }

    private void setdateselect() {

//        getDatesBetween("03/23/2023","03/28/2023");

        getDatesBetween(startdate,enddate);
    }



    public void getDatesBetween(String startDate,String endDate) {


        long  from= Date.parse(startDate);

        long to=Date.parse(endDate);

        int x=0;

        while(from <= to) {
            x=x+1;
            System.out.println ("Dates  : "+new Date(from));
            System.out.println ("Dates1  : "+from);

            String fDate = new SimpleDateFormat("E dd-MMM-yyyy").format(new Date(from));
            System.out.println ("Dates33 : "+fDate);
            from += ONE_DAY;

            LeaveData addleavedata=new LeaveData();
            addleavedata.setDatename(fDate);
            addleavedata.setHour("0");
            leaveData.add(addleavedata);

        }
        System.out.println ("No of Dates  :"+ x);


        binding.featureproduct.setAdapter( new LeaveDateAdapter(this,leaveData ) );

        if (leaveData.size()!=0){

            binding.lineworkhour.setVisibility(View.VISIBLE);


            int finalX = x;
            binding.saveworkhour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Gson gson=new Gson();

                    String data= gson.toJson(leaveData);

                    Log.e("vcvcvc",data);


                    sevefirebasedata(data,String.valueOf(finalX));



                }
            });




        }


    }


//    private void repaymentenddata(String data, String s) {
//
//
//
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
//
//
//
//
//
//
//        long  from= Date.parse(startdate);
//
//        long to=Date.parse(enddate);
//
//        int x=0;
//        int totalhour=0;
//
//        while(from <= to) {
//            x=x+1;
//            System.out.println ("Dates  : "+new Date(from));
//            System.out.println ("Dates1  : "+from);
//
//            String fDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(from));
//            String daysname = new SimpleDateFormat("EEEE").format(new Date(from));
//            System.out.println ("Dates33 : "+daysname);
//            from += ONE_DAY;
//
//
//            UserEmploymentadhocworkhourspojo addleavedata=new UserEmploymentadhocworkhourspojo();
//
//
//            addleavedata.Userid=userid;
//            addleavedata.Employmentid=employeenamid;
//            addleavedata.Employmentstartdate=employeenamstartdate;
//            addleavedata.Employmentenddate=employeenamenddate;
//            addleavedata.Adhocworkstartdate=startdate;
//            addleavedata.Adhocworkenddate=enddate;
//            addleavedata.Adhocworkdate=leaveData.get(x-1).getDatename();
//            addleavedata.Adhocworkhours=leaveData.get(x-1).getHour();
//            addleavedata.Isdeleted="";
//
//           // addleavedata.isadhocworkhouradded= "Y";
//           // addleavedata.Isupdatebypaycheck="N";
//
//            totalhour= totalhour + Integer.valueOf(leaveData.get(x-1).getHour());
//
//            useremploymentleaveschedulepojos.add(addleavedata);
//
//        }
//        System.out.println ("No of Dates  :"+ x);
//
//
//        int finalTotalhour = totalhour;
//        new Handler(Looper.getMainLooper()).post(() -> {
//
//            //UI THREAD CODE HERE
//
//            //  callstorefirebase();
//
//           // sevefirebasedata(data,String.valueOf(s),String.valueOf(finalTotalhour));
//
//        });
//
//
//
//
//    }

    private void sevefirebasedata(String data,String totaldays) {

        int totalhour=0;
        for (int i=0; i<leaveData.size();i++){
            totalhour= totalhour + Integer.valueOf(leaveData.get(i).getHour());

        }

        String userid="";
        userid=user.getUserid();

        Date currentTime = Calendar.getInstance().getTime();
        HashMap<String, Object> room = new HashMap<>();
       // room.put("leavejson", data);
        room.put("employeename", employeenametextstring);
        room.put("startdate", startdate);
        room.put("enddate", enddate);
        room.put("total_days", totaldays);
        room.put("userid", userid);
        room.put("total_hours",totalhour );

        room.put("EmploymentID",employeenamid );
        room.put("json_message",data );
//        room.put("processingTime", currentTime );
//        room.put("action", "1" );




        dialog.show();

        apiServise.adhoc_work_hours(room)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(AdhocworkhourActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(AdhocworkhourActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
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


    private void callhistoricaldata() {

        dialog.show();

        HashMap<String, Object> room = new HashMap<>();



        User user=new User(this);
        room.put("EmployerName", "");
        room.put("EmploymentStartDate", "");
        room.put("EmploymentEndDate", "");
        room.put("StandardDailyHours","");
        room.put("Paymentrateamount", 0);
        room.put("Paymentratefrequency", "");
        room.put("IsGSTregistered", "");
        room.put("WitholdingTaxRate",0 );
        room.put("UserID", user.getUserid());
        room.put("FirstPaymentDate","");
        room.put("PaymentFrequency","");
        room.put("EmploymentID", "");
        room.put("EmploymentRegion", "");
        room.put("ActionType", "2");



        apiServise.useremploymentmainfromid(room)
                .enqueue( new Callback<EmployeeDetailsMainOne>() {
                    @Override
                    public void onResponse(Call<EmployeeDetailsMainOne> call, Response<EmployeeDetailsMainOne> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().success.equals( "true" )) {

                                opendailogemployee(response.body());

                            } else {
                                Toast.makeText(AdhocworkhourActivity.this, response.body().success, Toast.LENGTH_SHORT).show();
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



//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
////                .child("profiles")
////                .child(typechild)
//                .child(Common.employedetaileurl).orderByChild("UserID").equalTo(typechild).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
//
//                dialog.cancel();
//
//
//
//                List<String> daaaa=new ArrayList<>();
//                Iterator<DataSnapshot> dataSnapshots = snapshot.getChildren().iterator();
//                List<Modal> fcmUsers = new ArrayList<>();
//                while (dataSnapshots.hasNext()) {
//                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
//                    Modal fcmUser = dataSnapshotChild.getValue(Modal.class);
//                    fcmUsers.add(fcmUser);
//
//                    String employeename= fcmUser.employeename;
//
//                    daaaa.add(employeename);
//
//                }
//
//                // Check your arraylist size and pass to list view like
//                if(fcmUsers.size()>0)
//                {
//
//                    // binding.featureproduct.setAdapter( new AdapterEmployeedetail( AddUpdateFragment.this,fcmUsers ) );
//
//
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdhocworkhourActivity.this);
//
//                    // set the custom icon to the alert dialog
//                    // alertDialog.setIcon(R.drawable.loadingpage);
//
//                    // title of the alert dialog
//                    alertDialog.setTitle("Employer Name");
//
//                    // list of the items to be displayed to the user in the
//                    // form of list so that user can select the item from
//
//
//
////                 Gson gson=new Gson();
////
////                 String aaaaaa=gson.toJson(daaaa);
////
////                 String datafinal= aaaaaa.replaceAll("^\\[", "").replaceAll("\\]$", "");
////
////                 Log.e("mnxmnx",datafinal);
//
//                    // String[] listItems = new String[] {"Android Development", "Web Development", "Machine Learning"};
//
//                    String[] listItems = new String[daaaa.size()];
//                    for (int i = 0; i < daaaa.size(); i++) {
//                        listItems[i] = daaaa.get( i );
//                    }
//
//
//                    // the function setSingleChoiceItems is the function which
//                    // builds the alert dialog with the single item selection
//                    alertDialog.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
//                        // update the selected item which is selected by the user so that it should be selected
//                        // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
//                        checkedItem[0] = which;
//
//                        // now also update the TextView which previews the selected item
//                        binding.employeenametext.setText(listItems[which]);
//
//                        employeenametextstring=listItems[which];
//
//
//
//                        employeenamstartdate=fcmUsers.get(which).startdate;
//                        employeenamenddate=fcmUsers.get(which).enddate;
//
//                        employeenamid=fcmUsers.get(which).keyid;
//
//
//                        binding.selectemployeedate.setVisibility(View.VISIBLE);
//                        binding.selectemployeedate.setText("Employment Date "+employeenamstartdate+" to "+employeenamenddate);
//
//
//                        // when selected an item the dialog should be closed with the dismiss method
//                        dialog.dismiss();
//                    });
//
//                    // set the negative button if the user is not interested to select or change already selected item
//                    alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
//
//                    });
//
//                    // create and build the AlertDialog instance with the AlertDialog builder instance
//                    AlertDialog customAlertDialog = alertDialog.create();
//
//                    // show the alert dialog when the button is clicked
//                    customAlertDialog.show();
//
//
//
//
//
//                }else
//                {
//
//                    Toast.makeText(AdhocworkhourActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
//
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
//            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
//
//                dialog.cancel();
//            }
//        });



    }

    private void opendailogemployee(EmployeeDetailsMainOne body) {


        List<Modal> fcmUsers = new ArrayList<>();

        fcmUsers.addAll(body.result);

        // Check your arraylist size and pass to list view like
        if(fcmUsers.size()>0)
        {

            // binding.featureproduct.setAdapter( new AdapterEmployeedetail( AddUpdateFragment.this,fcmUsers ) );


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdhocworkhourActivity.this);

            // set the custom icon to the alert dialog
            // alertDialog.setIcon(R.drawable.loadingpage);

            // title of the alert dialog
            alertDialog.setTitle("Employer Name");

            // list of the items to be displayed to the user in the
            // form of list so that user can select the item from



//                 Gson gson=new Gson();
//
//                 String aaaaaa=gson.toJson(daaaa);
//
//                 String datafinal= aaaaaa.replaceAll("^\\[", "").replaceAll("\\]$", "");
//
//                 Log.e("mnxmnx",datafinal);

            // String[] listItems = new String[] {"Android Development", "Web Development", "Machine Learning"};

            String[] listItems = new String[fcmUsers.size()];
            for (int i = 0; i < fcmUsers.size(); i++) {
                listItems[i] = fcmUsers.get( i ).employeename;
            }


            // the function setSingleChoiceItems is the function which
            // builds the alert dialog with the single item selection
            alertDialog.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                // update the selected item which is selected by the user so that it should be selected
                // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
                binding.employeenametext.setText(listItems[which]);

                employeenametextstring=listItems[which];



                employeenamstartdate=String.valueOf(DataUtils.formatDate(fcmUsers.get(which).startdate));
                employeenamenddate= String.valueOf(DataUtils.formatDate(fcmUsers.get(which).enddate));

                employeenamid=fcmUsers.get(which).keyid;


                binding.selectemployeedate.setVisibility(View.VISIBLE);
                binding.selectemployeedate.setText("Employment Date "+employeenamstartdate+" to "+employeenamenddate);


                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
            });

            // set the negative button if the user is not interested to select or change already selected item
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {

            });

            // create and build the AlertDialog instance with the AlertDialog builder instance
            AlertDialog customAlertDialog = alertDialog.create();

            // show the alert dialog when the button is clicked
            customAlertDialog.show();





        }else
        {

            Toast.makeText(AdhocworkhourActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();

        }



    }


    private void calldataworkschede() {

//        HashMap<String, Object> room11 = new HashMap<>();
//        room11.put("Useremploymentworkschedule", leaveData);



        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
                .child("UserEmploymentAdhocWorkHours")
                .child(key)
                .setValue(useremploymentleaveschedulepojos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

                dialog.cancel();

                if(task.isSuccessful()){



                    // getActivity().getFragmentManager().beginTransaction().remove(getParentFragment()).commit();
                    Toast.makeText(AdhocworkhourActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    onBackPressed();



                } else {
                    Toast.makeText(AdhocworkhourActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }



}