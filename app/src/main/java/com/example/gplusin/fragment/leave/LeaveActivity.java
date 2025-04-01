package com.example.gplusin.fragment.leave;

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
import com.example.gplusin.databinding.ActivityLeaveBinding;
import com.example.gplusin.pojo.EmployeeDetailsMainOne;
import com.example.gplusin.pojo.LeaveData;
import com.example.gplusin.pojo.Useremploymentleaveschedulepojo;
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

public class LeaveActivity extends AppCompatActivity {

    ActivityLeaveBinding binding;

    List<LeaveData> leaveData=new ArrayList<>();

    List<Useremploymentleaveschedulepojo> useremploymentleaveschedulepojos=new ArrayList<>();

    static final long ONE_DAY = 24 * 60 * 60 * 1000L;

    String startdate="",enddate="";
    ProgressDialog dialog;

    User user;

    String employeenametextstring="";

    String employeenamstartdate="",employeenamenddate="",employeenamid="";

    final int[] checkedItem = {-1};

    String key;
   // List<String> holidaysData;

    ApiServise apiServise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        binding = ActivityLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));


        dialog = new ProgressDialog(LeaveActivity.this);
        dialog.setMessage("Please Wait...");
        user=new User(LeaveActivity.this);




//        holidaysData=new ArrayList<>();
//        holidaysData.add("01/03/2023");
//        holidaysData.add("02/01/2023");
//        holidaysData.add("06/02/2023");
//        holidaysData.add("07/04/2023");
//        holidaysData.add("10/04/2023");
//        holidaysData.add("25/04/2023");
//        holidaysData.add("05/06/2023");
//        holidaysData.add("14/07/2023");
//        holidaysData.add("23/10/2023");
//        holidaysData.add("25/12/2023");
//        holidaysData.add("26/12/2023");
//
//
//        holidaysData.add("30/01/2023");
//        holidaysData.add("13/03/2023");
//        holidaysData.add("20/10/2023");
//        holidaysData.add("23/01/2023");
//        holidaysData.add("30/10/2023");
//        holidaysData.add("30/01/2023");
//        holidaysData.add("17/11/2023");
//        holidaysData.add("25/09/2023");
//        holidaysData.add("04/12/2023");
//        holidaysData.add("20/03/2023");
//        holidaysData.add("11/04/2023");
//
//
//
//
//
//
//        holidaysData.add("01/01/2024");
//        holidaysData.add("02/01/2024");
//        holidaysData.add("06/02/2024");
//        holidaysData.add("29/03/2024");
//        holidaysData.add("01/04/2024");
//        holidaysData.add("25/04/2024");
//        holidaysData.add("03/06/2024");
//        holidaysData.add("28/06/2024");
//        holidaysData.add("28/10/2024");
//        holidaysData.add("25/12/2024");
//        holidaysData.add("26/12/2024");
//
//
//        holidaysData.add("29/01/2024");
//        holidaysData.add("11/03/2024");
//        holidaysData.add("25/10/2024");
//        holidaysData.add("22/01/2024");
//        holidaysData.add("04/11/2024");
//        holidaysData.add("29/01/2024");
//        holidaysData.add("15/11/2024");
//        holidaysData.add("23/09/2024");
//        holidaysData.add("02/12/2024");
//        holidaysData.add("25/03/2024");
//        holidaysData.add("02/04/2024");
//        holidaysData.add("02/12/2024");





//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//         key = database.getReference().push().getKey();
//        Log.e("bvxbvx",key);




        binding.startdateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  showDialog(Date_id);

                if (employeenamstartdate.equals("")) {
                    Toast.makeText(LeaveActivity.this, "Select Employee Name", Toast.LENGTH_SHORT ).show();
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

                            binding.lineworkhour.setVisibility(View.INVISIBLE);
                            leaveData.clear();
                            setdateselect();
                        }
                    }
                });

                newFragment.show(LeaveActivity.this.getSupportFragmentManager(), "datePicker");



            }
        });



        binding.employeenddateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startdate.equals("")) {
                    Toast.makeText(LeaveActivity.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
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

                            Toast.makeText(LeaveActivity.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();

                        }else if (enddate.equals("")){

                            Toast.makeText(LeaveActivity.this, "Please Select End Date", Toast.LENGTH_SHORT).show();

                        }else {
                            binding.lineworkhour.setVisibility(View.INVISIBLE);
                            leaveData.clear();
                            setdateselect();
                        }
                    }
                });

                newFragment.show(LeaveActivity.this.getSupportFragmentManager(), "datePicker");



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

    private void updateworkschedule() {

        String typechild="";

        if (user.getRegistertype().equals("google")){

            typechild=user.getUserid();

        }else {

            typechild=user.getPhone_number();

        }


        Log.e("typechild",typechild);

        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(typechild)
                .child("UserEmploymentWorkSchedule").child(employeenamid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {



                //  List<String> daaaa=new ArrayList<>();
                Iterator<DataSnapshot> dataSnapshots = snapshot.getChildren().iterator();
                List<UpdateLeavepojo> fcmUsersupdateleave = new ArrayList<>();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    UpdateLeavepojo fcmUser = dataSnapshotChild.getValue(UpdateLeavepojo.class);
                    fcmUsersupdateleave.add(fcmUser);

//                   String employeename= fcmUser.employeename;
//
//                    daaaa.add(employeename);

                }


            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                dialog.cancel();
            }
        });


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
            System.out.println ("Dates  : "+new Date(from));
            System.out.println ("Dates1  : "+from);

            String fDate = new SimpleDateFormat("E dd-MMM-yyyy").format(new Date(from));
            System.out.println ("Dates33 : "+fDate);
            from += ONE_DAY;
                                                       //   30/1/2023
            String fDate11 = new SimpleDateFormat("dd/MM/yyyy").format(new Date(fDate));
            String daysname = new SimpleDateFormat("EEEE").format(new Date(fDate));


               // 05/06/2023
//            if (holidaysData.contains(fDate11)){
//
//                Log.e("hjgzhzg1111111111", fDate11);
//
//            }else {


                if (daysname.equals("Saturday") || daysname.equals("Sunday")) {



                } else {
                    Log.e("hjgzhzg", daysname);
                    LeaveData addleavedata = new LeaveData();
                    addleavedata.setDatename(fDate);
                    addleavedata.setHour("0");
                    leaveData.add(addleavedata);

                    x = x + 1;

                }

         //   }


        }
        System.out.println ("No of Dates  :"+ x);

        if (x==0){

            Toast.makeText(LeaveActivity.this, "Please select a date which is neither weekend nor holiday", Toast.LENGTH_SHORT).show();

        }



        binding.featureproduct.setAdapter( new LeaveDateAdapter(this,leaveData ) );

        if (leaveData.size()!=0){

            binding.lineworkhour.setVisibility(View.VISIBLE);


            int finalX = x;
            binding.saveworkhour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Gson gson=new Gson();

                    String data= gson.toJson(leaveData);

                    sevefirebasedata(data,String.valueOf(finalX));

                    Log.e("vcvcvc",data);

                }
            });




        }


    }




    private void repaymentenddata(String data, String s) {

        String userid="";

        userid=user.getUserid();


        long  from= Date.parse(startdate);

        long to=Date.parse(enddate);

        int x=0;

        int totalhour=0;


        while(from <= to) {
            System.out.println ("Dates  : "+new Date(from));
            System.out.println ("Dates1  : "+from);

            String fDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(from));
            String daysname = new SimpleDateFormat("EEEE").format(new Date(from));

            String fDate11 = new SimpleDateFormat("dd/MM/yyyy").format(new Date(fDate));

            System.out.println ("Dates33 : "+daysname);
            from += ONE_DAY;



//            if (holidaysData.contains(fDate11)){
//
//                Log.e("hjgzhzg1111111111", fDate11);
//
//            }else {


                if (daysname.equals("Saturday") || daysname.equals("Sunday")) {



                } else {

                    Log.e("hjgzhzg", daysname);
                    Useremploymentleaveschedulepojo addleavedata=new Useremploymentleaveschedulepojo();


                    addleavedata.Userid=userid;
                    addleavedata.Employmentid=employeenamid;
                    addleavedata.Employmentstartdate=employeenamstartdate;
                    addleavedata.Employmentenddate=employeenamenddate;
                    addleavedata.Leave_start_date=startdate;
                    addleavedata.Leave_end_date=enddate;
                    addleavedata.Leavedate=leaveData.get(x).getDatename();
                    addleavedata.Leavehours=leaveData.get(x).getHour();
                    addleavedata.Isdeleted="";

                    useremploymentleaveschedulepojos.add(addleavedata);

                    totalhour= Integer.parseInt(totalhour + leaveData.get(x).getHour());



                    x = x + 1;



                }

          //  }







        }
        System.out.println ("No of Dates  :"+ x);


        new Handler(Looper.getMainLooper()).post(() -> {

            //UI THREAD CODE HERE

          //  callstorefirebase();

            sevefirebasedata(data,String.valueOf(s));

        });




    }



    private void sevefirebasedata(String data,String totaldays) {

        int totalhour=0;
        for (int i=0; i<leaveData.size();i++){
            totalhour= totalhour + Integer.valueOf(leaveData.get(i).getHour());

        }


        String userid="";


        userid=user.getUserid();

      //  Date currentTime = Calendar.getInstance().getTime();

        HashMap<String, Object> room = new HashMap<>();
      //  room.put("leavejson", data);
        room.put("employeename", employeenametextstring);
        room.put("startdate", startdate);
        room.put("enddate", enddate);
        room.put("total_days", totaldays);
        room.put("userid", userid);
        room.put("total_hours",totalhour );

        room.put("EmploymentID",employeenamid );
        room.put("json_message",data );
//        room.put("processingTime", currentTime );
 //       room.put("action", "1" );


        dialog.show();

        apiServise.leave_days(room)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(LeaveActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(LeaveActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
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

        String typechild="";

        if (user.getRegistertype().equals("google")){

            typechild=user.getUserid();

        }else {

            typechild=user.getPhone_number();

        }


        Log.e("typechild",typechild);
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
                            if (response.body().success.equals( "true" )) {

                               opendailogemployee(response.body());

                            } else {
                                Toast.makeText(LeaveActivity.this, response.body().success, Toast.LENGTH_SHORT).show();
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

    private void opendailogemployee(EmployeeDetailsMainOne body) {




        List<Modal> fcmUsers = new ArrayList<>();

        fcmUsers.addAll(body.result);

//        while (dataSnapshots.hasNext()) {
//            DataSnapshot dataSnapshotChild = dataSnapshots.next();
//            Modal fcmUser = dataSnapshotChild.getValue(Modal.class);
//            fcmUsers.add(fcmUser);
//
////                   String employeename= fcmUser.employeename;
////
////                    daaaa.add(employeename);
//
//        }

        // Check your arraylist size and pass to list view like
        if(fcmUsers.size()>0)
        {

            // binding.featureproduct.setAdapter( new AdapterEmployeedetail( AddUpdateFragment.this,fcmUsers ) );


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LeaveActivity.this);

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



//                    Runnable runnable = new Runnable(){
//                        public void run() {
//                            //some code here
//                            updateworkschedule();
//
//                        }
//                    };
//
//                    Thread thread = new Thread(runnable);
//                    thread.start();



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

            Toast.makeText(LeaveActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();

        }





    }


    private void calldataworkschede() {

//        HashMap<String, Object> room11 = new HashMap<>();
//        room11.put("Useremploymentworkschedule", leaveData);



        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
                .child("Useremploymentleaveschedule")
                .child(key)
                .setValue(useremploymentleaveschedulepojos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

                dialog.cancel();

                if(task.isSuccessful()){



                    // getActivity().getFragmentManager().beginTransaction().remove(getParentFragment()).commit();
                    Toast.makeText(LeaveActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    onBackPressed();



                } else {
                    Toast.makeText(LeaveActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }



}