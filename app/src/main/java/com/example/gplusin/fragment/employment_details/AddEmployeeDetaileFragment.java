package com.example.gplusin.fragment.employment_details;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gplusin.MainActivity;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.activity.UserLogin;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.FragmentAddDetaileBinding;
import com.example.gplusin.pojo.UserEmploymentpaycheckpojo;
import com.example.gplusin.pojo.UserFinancialBalancespojo;
import com.example.gplusin.pojo.UserPaycheckDatespojo;
import com.example.gplusin.pojo.Useremploymentworkschedulepojo;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DatePickerFragment;
import com.example.gplusin.utils.DatePickerFragmentEndDate;
import com.example.gplusin.utils.DatePickerFragmentFirstDate;
import com.example.gplusin.utils.FiscalDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEmployeeDetaileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEmployeeDetaileFragment extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentAddDetaileBinding binding;

    static final long ONE_DAY = 24 * 60 * 60 * 1000L;

    String startdate="",enddate="",paymentratestring="Hourly",gstregisterstring="",firstpaymentdate="",paymentfrequency="",employeeregionString="",paymentratedata="";

    String addpaymentdate="";
    ProgressDialog dialog;

    User user;
    private DatePicker datePicker;
    ArrayList<String> values;


    int monthupdate;
    int YEAR;
    int DAY_OF_MONTH;

    String userid="";

    List<Useremploymentworkschedulepojo> leaveData= new ArrayList<>();

    List<UserPaycheckDatespojo> paycheckDatespojos= new ArrayList<>();
    List<UserEmploymentpaycheckpojo> userEmploymentpaycheckpojos = new ArrayList<>();

    String key;

    List<String> fcmUsers11111 = new ArrayList<>();

    ApiServise apiServise;

    public AddEmployeeDetaileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDetaileFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static AddDetaileFragment newInstance(String param1, String param2) {
//        AddDetaileFragment fragment = new AddDetaileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        binding = FragmentAddDetaileBinding.inflate(inflater, container, false);
       //return inflater.inflate(R.layout.fragment_add_detaile, container, false);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_detaile);

        binding = FragmentAddDetaileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServise= Common.getAPI();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        dialog = new ProgressDialog(AddEmployeeDetaileFragment.this);
        dialog.setMessage("Please Wait...");
        user=new User(AddEmployeeDetaileFragment.this);




        if (user.getRegistertype().equals("register")){

            userid=user.getPhone_number();
        }else {

            userid=user.getUserid();

        }


//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//        LocalDate start = LocalDate.parse("2/3/2017",formatter);
//        LocalDate end = LocalDate.parse("3/3/2017",formatter);
//
//      //  System.out.println(ChronoUnit.WEEKS.between(start, end)); // 28
//        Log.e("cjdkjnd", String.valueOf(ChronoUnit.WEEKS.between(start, end)));




        FirebaseDatabase database = FirebaseDatabase.getInstance();
         key = database.getReference().push().getKey();
        Log.e("bvxbvx",key);


        binding.startdateline.setOnClickListener(new View.OnClickListener() {
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

                            binding.txtstartdate.setText(date);
                            startdate = date;


                            binding.txtpaymentdatefirst.setText("");
                            firstpaymentdate = "";

                    }
                });



                newFragment.show(AddEmployeeDetaileFragment.this.getSupportFragmentManager(), "datePicker");



            }
        });



        binding.employeenddateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  showDialog(Date_id);

                if (startdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

                DatePickerFragmentEndDate newFragment = new DatePickerFragmentEndDate();

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                b.putString("startdate", startdate);
                newFragment.setArguments(b);

               // newFragment.setStartdate(startdate);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {

//                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
//
//                        try {
//
//
//                            Date date111 = sdf.parse(date);
//                            Date date211 = sdf.parse("31/03/2023");
//
//                            if (date111.compareTo(date211) > 0) {
                                // Log.i("app", "Date1 is after Date2");


                                binding.txtendDate.setText(date);
                                enddate=date;

                                binding.txtpaymentdatefirst.setText("");
                                firstpaymentdate="";



                        //  System.out.println(ChronoUnit.WEEKS.between(start, end)); // 28
                       // Log.e("cjdkjnd", String.valueOf(ChronoUnit.WEEKS.between(start, end)));


                        if (!paymentfrequency.equals("") && !firstpaymentdate.equals("") && !enddate.equals("") ) {
                            Runnable runnable = new Runnable() {
                                public void run() {
                                    //some code here

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                                    LocalDate start = LocalDate.parse(startdate, formatter);
                                    LocalDate end = LocalDate.parse(enddate, formatter);

                                    if (paymentfrequency.equals("Monthly")) {

                                        paymentratedata = String.valueOf(ChronoUnit.MONTHS.between(start, end));

                                    } else if (paymentfrequency.equals("Weekly")) {

                                        paymentratedata = String.valueOf(ChronoUnit.WEEKS.between(start, end));

                                    } else if (paymentfrequency.equals("Fortnightly")) {

                                        int countdata= (int) ChronoUnit.WEEKS.between(start, end) / 2;

                                        paymentratedata = String.valueOf(countdata);

                                    }


                                    repaymentenddata111111();
                                }
                            };

                            Thread thread = new Thread(runnable);
                            thread.start();

                        }






//                            } else if (date111.compareTo(date211) < 0) {
//                                // Log.i("app", "Date1 is before Date2");
//
//                                Toast.makeText(AddEmployeeDetaileFragment.this, " Please provide employment which is from current Financial Year", Toast.LENGTH_SHORT).show();
//
//
//                            } else if (date111.compareTo(date211) == 0) {
//                                Toast.makeText(AddEmployeeDetaileFragment.this, " Please provide employment which is from current Financial Year", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//



                    }
                });

                newFragment.show(AddEmployeeDetaileFragment.this.getSupportFragmentManager(), "datePicker");



            }
        });


        binding.linFrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callpaymentrate();
            }
        });

        binding.gstregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callgstregister();
            }
        });

        binding.firstpaymentdateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (enddate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select End Date", Toast.LENGTH_SHORT ).show();
                    return;
                }






                DatePickerFragmentFirstDate newFragment = new DatePickerFragmentFirstDate();

//                String aaaaaa= getDatesBetween(startdate,enddate);
//                Toast.makeText(AddEmployeeDetaileFragment.this, ""+aaaaaa, Toast.LENGTH_SHORT).show();
//                getDatesBetween(startdate,enddate)

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                b.putString("startdate", startdate);
                b.putString("enddate",enddate );
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {


                        String daysname = new SimpleDateFormat("EEEE").format(new Date(date));

                        if (daysname.equals("Saturday") || daysname.equals("Sunday")) {


                            Toast.makeText(AddEmployeeDetaileFragment.this, "First Payment Date cannot be on weekend", Toast.LENGTH_SHORT).show();


                        }else {


                            binding.txtpaymentdatefirst.setText(date);
                            firstpaymentdate = date;


                            if (!paymentfrequency.equals("") && !firstpaymentdate.equals("") && !enddate.equals("")) {
                                Runnable runnable = new Runnable() {
                                    public void run() {
                                        //some code here

                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                                        LocalDate start = LocalDate.parse(startdate, formatter);
                                        LocalDate end = LocalDate.parse(enddate, formatter);

                                        if (paymentfrequency.equals("Monthly")) {

                                            paymentratedata = String.valueOf(ChronoUnit.MONTHS.between(start, end));

                                        } else if (paymentfrequency.equals("Weekly")) {

                                            paymentratedata = String.valueOf(ChronoUnit.WEEKS.between(start, end));

                                        } else if (paymentfrequency.equals("Fortnightly")) {

                                            int countdata = (int) ChronoUnit.WEEKS.between(start, end) / 2;

                                            paymentratedata = String.valueOf(countdata);

                                        }


                                        repaymentenddata111111();
                                    }
                                };

                                Thread thread = new Thread(runnable);
                                thread.start();

                            }

                        }



                    }
                });

                newFragment.show(AddEmployeeDetaileFragment.this.getSupportFragmentManager(), "datePicker");


            }
        });

        binding.paymentfrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentfrequency11111111();
            }
        });


        binding.daikyhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailyhour();
            }
        });


        binding.employeeregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeregion();
            }
        });

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.employeename.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Enter Employer Name", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (startdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

//               if (agreed.equals("")) {
//                   Toast.makeText(GiveActivity.this, "Agreed Empty", Toast.LENGTH_SHORT ).show();
//                   return;
//               }

                if (enddate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select End Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.daikyhour.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Enter Standard Daily Hour", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.paymentrateeditedit.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Enter Payment Rate", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (paymentratestring.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select Payment Rate", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (gstregisterstring.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Please confirm GST registration.", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.withoutTaxRate.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Please provide Witholding tax rate.", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (firstpaymentdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select First Payment Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (paymentfrequency.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select Payment Frequency", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (employeeregionString.equals("")) {
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Select Employment Region", Toast.LENGTH_SHORT ).show();
                    return;
                }


                callstorefirebase();


            }
        });



      //  return binding.getRoot();
    }

    private void employeeregion() {


        final String[] mStrings = {"Auckland","Taranaki","Hawke's Bay","Wellington","Marlborough","Nelson","Canterbury",
        "Canterbury (South),Westland","Otago","Southland","Chatham Islands"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileFragment.this );
        builder.setTitle( "Select Employment Region" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                employeeregionString=mStrings[which];
                dialog.dismiss();
                binding.employeeregion.setText( mStrings[which] );
            }
        } );
        builder.show();


    }

    private void dailyhour() {


        final String[] mStrings = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileFragment.this );
        builder.setTitle( "Select Standard Daily Hours" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                dialog.dismiss();
                binding.daikyhour.setText( mStrings[which] );
            }
        } );
        builder.show();


    }

    private void paymentfrequency11111111() {

        final String[] mStrings = {"Weekly","Fortnightly","Monthly"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileFragment.this );
        builder.setTitle( "Select Payment Frequency" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                paymentfrequency=mStrings[which];
                dialog.dismiss();
                binding.paymentfrequency.setText( mStrings[which] );



                if (!paymentfrequency.equals("") && !firstpaymentdate.equals("") && !enddate.equals("") ) {
                    Runnable runnable = new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void run() {
                            //some code here

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate start = LocalDate.parse(startdate, formatter);
                            LocalDate end = LocalDate.parse(enddate, formatter);

                            if (paymentfrequency.equals("Monthly")) {

                                paymentratedata = String.valueOf(ChronoUnit.MONTHS.between(start, end));



                            } else if (paymentfrequency.equals("Weekly")) {

                                paymentratedata = String.valueOf(ChronoUnit.WEEKS.between(start, end));

                            } else if (paymentfrequency.equals("Fortnightly")) {

                                int countdata= (int) ChronoUnit.WEEKS.between(start, end) / 2;

                                paymentratedata = String.valueOf(countdata);

                            }


                            repaymentenddata111111();
                        }
                    };

                    Thread thread = new Thread(runnable);
                    thread.start();

                }






            }
        } );
        builder.show();
    }

    private void callgstregister() {


        final String[] mStrings = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileFragment.this );
        builder.setTitle( "Select GST Registered" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
               gstregisterstring =mStrings[which];
                dialog.dismiss();
                binding.gstregister.setText( mStrings[which] );
            }
        } );
        builder.show();


    }

    private void callpaymentrate() {

        final String[] mStrings = {"Hourly", "Daily","Weekly","Fortnightly","Monthly"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileFragment.this );
        builder.setTitle( "Select Payment Rate" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                paymentratestring=mStrings[which];
                dialog.dismiss();
                binding.paymentrate.setText( mStrings[which] );
            }
        } );
        builder.show();




    }


    public String getDatesBetween(String startDate,String endDate) {


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


        }
        System.out.println ("No of Dates  :"+ x);


        return String.valueOf(x);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void repaymentenddata() {

//        if (frequency!="" && repaymentamount!=""){
//
//            double percentage= Double.parseDouble(binding.loanform.repaymentamount.getText().toString());
//
//            double qqqqq= Double.parseDouble(frequency);
//
//            double disPercent = (percentage / qqqqq);
//
//            binding.loanform.emailamount.setText(String.valueOf(String.format("%.2f",disPercent)));
//
//        }

        String userid="";

        if (user.getRegistertype().equals("register")){

            userid=user.getPhone_number();
        }else {

            userid=user.getUserid();

        }


        double billpayment= Double.valueOf(binding.daikyhour.getText().toString().trim()) *  Double.valueOf(binding.paymentrateeditedit.getText().toString().trim());




        long  from= Date.parse(startdate);

        long to=Date.parse(enddate);

        int x=0;

        while(from <= to) {
            x = x + 1;
            System.out.println("Dates  : " + new Date(from));
            System.out.println("Dates1  : " + from);

            String fDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(from));
            String daysname = new SimpleDateFormat("EEEE").format(new Date(from));
            System.out.println("Dates33 : " + daysname);
            from += ONE_DAY;


            Useremploymentworkschedulepojo addleavedata = new Useremploymentworkschedulepojo();
            addleavedata.UserID = userid;


            addleavedata.EmploymentID = key;
            addleavedata.Workdate = fDate;
            addleavedata.StandardDailyHours = binding.daikyhour.getText().toString().trim();
            addleavedata.Ispaid = "N";
            // addleavedata.Isactive="Y";
            addleavedata.EmploymentStartDate = startdate;
            addleavedata.EmploymentEndDate = enddate;

            addleavedata.EmploymentRegion = employeeregionString;
            addleavedata.FirstPaymentDate = firstpaymentdate;
            addleavedata.PaymentFrequency = paymentfrequency;
            addleavedata.WitholdingTaxRate = binding.withoutTaxRate.getText().toString();
            addleavedata.IsGSTregistered = gstregisterstring;
            addleavedata.BilledAmount = String.valueOf(billpayment);
            addleavedata.EmployerName = binding.employeename.getText().toString();

            addleavedata.Paymentrateamount = binding.paymentrateeditedit.getText().toString().trim();
            addleavedata.Paymentratefrequency = paymentratestring;


            if (daysname.equals("Saturday") || daysname.equals("Sunday")) {

                addleavedata.IsmarkedLeave = "Y";
                addleavedata.IsmarkedHoliday = "Y";
                addleavedata.Isweekend = "Y";


            } else {
                addleavedata.IsmarkedLeave = "N";
                addleavedata.IsmarkedHoliday = "N";
                addleavedata.Isweekend = "N";

            }


            addleavedata.IsadhocWorkHouradded = "N";
            addleavedata.DayName = daysname;

            addleavedata.Ispaycheckupdate = "N";
            addleavedata.GSTPaidonexpense = "";
            addleavedata.GSTcollectedonbilled = "";

            addleavedata.Isdeleted = "N";


            String gsfhsghshsa = FiscalDate.displayFinancialDate(FiscalDate.setDate(fDate));

            addleavedata.FinancialYear = gsfhsghshsa;


            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

//                Date c = Calendar.getInstance().getTime();
//                String formattedDate = sdf.format(c);

                Date date1 = sdf.parse(fDate);
                Date date2 = sdf.parse("31-Mar-2024");

                if (date1.compareTo(date2) > 0) {
                    // Log.i("app", "Date1 is after Date2");
                    addleavedata.Ishitorical = "Y";
                } else if (date1.compareTo(date2) < 0) {


                    try {


                        Date date111 = sdf.parse(fDate);
                        Date date211 = sdf.parse("31-Mar-2023");

                        if (date111.compareTo(date211) > 0) {
                            // Log.i("app", "Date1 is after Date2");
                            addleavedata.Ishitorical = "N";
                        } else if (date111.compareTo(date211) < 0) {
                            // Log.i("app", "Date1 is before Date2");

                            addleavedata.Ishitorical = "Y";
                        } else if (date111.compareTo(date211) == 0) {
                            addleavedata.Ishitorical = "Y";
                            // Log.i("app", "Date1 is equal to Date2");
                        } else {

                            addleavedata.Ishitorical = "N";
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (date1.compareTo(date2) == 0) {
                    addleavedata.Ishitorical = "Y";
                    // Log.i("app", "Date1 is equal to Date2");
                } else {

                    addleavedata.Ishitorical = "N";
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//            LocalDate start = LocalDate.parse(firstpaymentdate, formatter);
//            LocalDate end = LocalDate.parse(enddate, formatter);

//       if (paymentfrequency.equals("Weekly")) {

            // paymentratedata = String.valueOf(ChronoUnit.WEEKS.between(start, end));

//
//            String check = "0";
//            for (int i = 0; i < paycheckDatespojos.size(); i++) {
//
//                if (check == "0") {
//
//                try {
//
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
//
//                    String fDate11 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(fDate));
//
//
//                    // 2023-05-19
//
//                    // 25-May-2023
//                    //  26-05-2023
//
//                    String[] data11 = paycheckDatespojos.get(i).PaycheckEndDate.split("-");
//
//
//                    Calendar cal = Calendar.getInstance();
//                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
//                    int monthnum = Integer.parseInt(data11[1])-1;
//                    cal.set(Calendar.MONTH, monthnum);
//                    String month_name = month_date.format(cal.getTime());
//
//
//                    String finaldate = String.valueOf(new Date(data11[2].toString() + "-" + month_name.toString() + "-" + data11[0].toString()));
//
//
//                    String fDate22 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(finaldate));
//                    Date date111 = sdf.parse(fDate11);
//                    Date date211 = sdf.parse(fDate22);
//
//
//                    if (date111.compareTo(date211) > 0) {
//                        // Log.i("app", "Date1 is after Date2");
//
//                        try {
//                            check="11";
//                            addleavedata.Userpaycheckdate = paycheckDatespojos.get(i + 1).PaycheckDate;
//                            addleavedata.Userpaycheckid = paycheckDatespojos.get(i + 1).PaycheckID;
//
//                        } catch (Exception exception) {
//
//
//                        }
//
//
//                    } else if (date111.compareTo(date211) < 0) {
//                        // Log.i("app", "Date1 is before Date2");
//                        check="11";
//                        addleavedata.Userpaycheckdate = paycheckDatespojos.get(i).PaycheckDate;
//
//                        addleavedata.Userpaycheckid = paycheckDatespojos.get(i).PaycheckID;
//
//
//                    } else if (date111.compareTo(date211) == 0) {
//
//                        check="11";
//                        addleavedata.Userpaycheckdate = paycheckDatespojos.get(i).PaycheckDate;
//
//                        addleavedata.Userpaycheckid = paycheckDatespojos.get(i).PaycheckID;
//
//                        // Log.i("app", "Date1 is equal to Date2");
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//
//
//            }


            //  }

                leaveData.add(addleavedata);


        //}



        }
     //   System.out.println ("No of Dates  :"+ x);


        values=new ArrayList<String>();
        for (int i=0;i<leaveData.size();i++){


            values.add(leaveData.get(i).FinancialYear);

        }


        if (paymentfrequency.equals("Monthly")) {


            SimpleDateFormat ffff = new SimpleDateFormat("MM/dd/yyyy");
            String datafirstpaymentformat = ffff.format(new Date(firstpaymentdate));




            Calendar aCalendar = Calendar.getInstance();
            String fDate111 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(datafirstpaymentformat));
            aCalendar.setTime(new Date(fDate111));

            aCalendar.set(Calendar.DATE, 1);
            aCalendar.add(Calendar.MONTH,1);
            aCalendar.add(Calendar.DAY_OF_MONTH, -1);
            Date lastDateOfPreviousMonth = aCalendar.getTime();
            aCalendar.set(Calendar.DATE, 1);
            Date firstDateOfPreviousMonth = aCalendar.getTime();

            String fDate11122 = new SimpleDateFormat("dd-MMM-yyyy").format(lastDateOfPreviousMonth);
            Log.e("snbnsdb",""+firstDateOfPreviousMonth+"  "+lastDateOfPreviousMonth);

//            String fist = new SimpleDateFormat("MM/dd/yyyy").format(firstDateOfPreviousMonth);
//            String end11 = new SimpleDateFormat("MM/dd/yyyy").format(lastDateOfPreviousMonth);
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//            minusdays11 = LocalDate.parse(fist,formatter);
//            minusdaysenddate11 = LocalDate.parse(end11,formatter);












            String nextmonthdate="";
          //  Calendar aCalendar = Calendar.getInstance();
          //  String fDate111 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(firstpaymentdate));
//            aCalendar.setTime(new Date(fDate111));
//
//
//            String[] getdate=fDate111.split("-");
//
//
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
//            int monthnum = Integer.parseInt(getdate[0])-1;
//            cal.set(Calendar.MONTH, monthnum);
//            String month_name = month_date.format(cal.getTime());
//
//            Log.e("snbnsdb1",""+month_name);
//
//            String finaldate =getdate[0].toString() + "-" + month_name.toString() + "-" + getdate[2].toString();
             nextmonthdate= String.valueOf(fDate11122);


            int setdiffucultcondition=2;




            String dtqqqqq = firstpaymentdate;
            SimpleDateFormat sdfqqqqq = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cqqq = Calendar.getInstance();
            try {
                cqqq.setTime(sdfqqqqq.parse(dtqqqqq));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE


            int aaaaqqqq = Integer.parseInt("1");
            cqqq.add(Calendar.MONTH, aaaaqqqq);

            SimpleDateFormat sdf1qqqqq = new SimpleDateFormat("MM/dd/yyyy");
            String outputqqqqq = sdf1qqqqq.format(cqqq.getTime());

            String addpaycheckdate=outputqqqqq;

            String datapaycheckdeficult=firstpaymentdate;
            for (int i=0;i<leaveData.size();i++){

                if (i==1){

                    leaveData.get(i).Userpaycheckdate= addpaycheckdate;
                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);

                }else if (leaveData.get(i).Workdate.equals(nextmonthdate)){


                    setdiffucultcondition=setdiffucultcondition+1;

                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);








//                    SimpleDateFormat ffffqqq = new SimpleDateFormat("MM/dd/yyyy");
//                    String datafirstpaymentformatqqqqqq = ffff.format(new Date(firstpaymentdate));
//
//
//
//
//                    Calendar aCalendarqqqqqqq = Calendar.getInstance();
//                    String fDate111qqqqqq = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(datafirstpaymentformat));
//                    aCalendarqqqqqqq.setTime(new Date(fDate111));
//
//                    aCalendarqqqqqqq.set(Calendar.DATE, 1);
//                    aCalendarqqqqqqq.add(Calendar.MONTH,1);
//                    aCalendarqqqqqqq.add(Calendar.DAY_OF_MONTH, -1);
//                    Date lastDateOfPreviousMonthqq = aCalendar.getTime();
//                    aCalendarqqqqqqq.set(Calendar.DATE, 1);
//                    Date firstDateOfPreviousMonthq = aCalendar.getTime();
//
//                    String fDate11122qq = new SimpleDateFormat("dd-MMM-yyyy").format(lastDateOfPreviousMonthqq);
//                    Log.e("snbnsdb",""+firstDateOfPreviousMonthq+"  "+lastDateOfPreviousMonthqq);








                    String datafirstpaymentformatnext = ffff.format(new Date(addpaycheckdate));





                    Calendar aCalendarnext = Calendar.getInstance();
                    String fDate111next = new SimpleDateFormat("MM/dd/yyyy").format(new Date(datafirstpaymentformatnext));
                    aCalendarnext.setTime(new Date(fDate111next));

                    aCalendarnext.set(Calendar.DATE, 1);
                    aCalendarnext.add(Calendar.MONTH,1);
                    aCalendarnext.add(Calendar.DAY_OF_MONTH, -1);
                    Date lastDateOfPreviousMonthnext = aCalendarnext.getTime();
                    aCalendarnext.set(Calendar.DATE, 1);
                    Date firstDateOfPreviousMonthnext = aCalendarnext.getTime();

                    String fDate11122next = new SimpleDateFormat("dd-MMM-yyyy").format(lastDateOfPreviousMonthnext);
                    Log.e("snbnsdb",""+firstDateOfPreviousMonthnext+"  "+lastDateOfPreviousMonthnext);


                    nextmonthdate= String.valueOf(fDate11122next);
//
//
//                    datapaycheckdeficult=nextmonthdate;



                    String dt = addpaycheckdate;
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(dt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE


                        int aaaa = Integer.parseInt("1");
                        c.add(Calendar.MONTH, aaaa);

                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                    String output = sdf1.format(c.getTime());


                    addpaycheckdate=output;

//                    String fDate11122next = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(output));
//                    nextmonthdate= fDate11122next;

                    leaveData.get(i).Userpaycheckdate= addpaycheckdate;



                }else {

                    leaveData.get(i).Userpaycheckdate= addpaycheckdate;
                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
                }

            }









        } else if (paymentfrequency.equals("Weekly")) {

            int setdiffucultcondition=1;
            String datapaycheckdeficult=firstpaymentdate;
            for (int i=0;i<leaveData.size();i++){
//
//                if (i==1){
//
//                    leaveData.get(i).Userpaycheckdate= datapaycheckdeficult;
//                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
//
//                }else


                int plusone=i+1;

                int aaaaa=plusone/7;


                int postion=aaaaa+3;

                try {

                    leaveData.get(i).Userpaycheckdate = paycheckDatespojos.get(postion - 1).PaycheckDate;
                    leaveData.get(i).Userpaycheckid = paycheckDatespojos.get(postion - 1).PaycheckID;
                }catch (Exception ee){

                    Log.e("sdjmdsnb",ee.getLocalizedMessage());


                }




//                 if (i+1 % 7==1){
//
//
//
//                    datapaycheckdeficult=leaveData.get(i).Workdate;
//
//                    leaveData.get(i).Userpaycheckdate= datapaycheckdeficult;
//                    setdiffucultcondition=setdiffucultcondition+1;
//
//                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
//
//                }else {
//
//                    leaveData.get(i).Userpaycheckdate= datapaycheckdeficult;
//                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
//                }

            }


        } else if (paymentfrequency.equals("Fortnightly")) {


            int setdiffucultcondition=1;
            String datapaycheckdeficult=firstpaymentdate;
            for (int i=0;i<leaveData.size();i++){



                int plusone=i+1;

                int aaaaa=plusone/14;


                int postion=aaaaa+2;

                try {

                    leaveData.get(i).Userpaycheckdate = paycheckDatespojos.get(postion - 1).PaycheckDate;
                    leaveData.get(i).Userpaycheckid = paycheckDatespojos.get(postion - 1).PaycheckID;
                }catch (Exception ee){

                    Log.e("sdjmdsnb",ee.getLocalizedMessage());


                }





//
//                if (i==1){
//
//                    leaveData.get(i).Userpaycheckdate= datapaycheckdeficult;
//                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
//
//                }else if (i % 14==1){
//
//                    datapaycheckdeficult=leaveData.get(i).Workdate;
//
//                    leaveData.get(i).Userpaycheckdate= datapaycheckdeficult;
//                    setdiffucultcondition=setdiffucultcondition+1;
//
//                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
//
//                }else {
//
//                    leaveData.get(i).Userpaycheckdate= datapaycheckdeficult;
//                    leaveData.get(i).Userpaycheckid=String.valueOf(setdiffucultcondition);
//                }

            }


        }



        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(values);
        values.clear();
        values.addAll(hashSet);



        new Handler(Looper.getMainLooper()).post(() -> {

            //UI THREAD CODE HERE

            callstorefirebase();

        });




    }

    private void callstorefirebase() {

        String IsGSTregistered = "0";

        if(gstregisterstring.equals("Yes")){

            IsGSTregistered = "1";
        }else {

            IsGSTregistered = "0";
        }


        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("ssMs");
        String datetime = ft.format(dNow);
        HashMap<String, Object> room = new HashMap<>();


        room.put("EmployerName", binding.employeename.getText().toString());
        room.put("EmploymentStartDate", startdate);
        room.put("EmploymentEndDate", enddate);
        room.put("StandardDailyHours", binding.daikyhour.getText().toString().trim());
        room.put("Paymentrateamount", binding.paymentrateeditedit.getText().toString().trim());
        room.put("Paymentratefrequency", paymentratestring);
        room.put("IsGSTregistered", IsGSTregistered);
        room.put("WitholdingTaxRate",binding.withoutTaxRate.getText().toString() );
        room.put("UserID", user.getUserid());
        room.put("FirstPaymentDate", firstpaymentdate);
        room.put("PaymentFrequency", paymentfrequency);
        room.put("EmploymentID", 0);
        room.put("EmploymentRegion", employeeregionString);
        room.put("ActionType", "1");

//        room.put("EffectiveBeginDate", "");
//        room.put("EffectiveEndDate", "");
//        room.put("Isdeleted", "N");






//        binding.employeename.getText().toString(),startdate,enddate, binding.daikyhour.getText().toString().trim(),
//                binding.paymentrateeditedit.getText().toString().trim(), paymentratestring,IsGSTregistered,binding.withoutTaxRate.getText().toString() ,
//                user.getUserid(),firstpaymentdate,paymentfrequency,datetime,employeeregionString


        dialog.show();
        apiServise.UseremploymentMain(room)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(AddEmployeeDetaileFragment.this, "Success", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(AddEmployeeDetaileFragment.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            dialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterData> call, Throwable t) {
                        dialog.cancel();
                        // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();
                    }
                } );




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void repaymentenddata111111() {

//        if (frequency!="" && repaymentamount!=""){
//
//            double percentage= Double.parseDouble(binding.loanform.repaymentamount.getText().toString());
//
//            double qqqqq= Double.parseDouble(frequency);
//
//            double disPercent = (percentage / qqqqq);
//
//            binding.loanform.emailamount.setText(String.valueOf(String.format("%.2f",disPercent)));
//
//        }

      //  financialBalancespojos.
        paycheckDatespojos.clear();
        userEmploymentpaycheckpojos.clear();


        // "Weekly","Fortnightly","Monthly"

       // temp.add(startdate);

        SimpleDateFormat ffff = new SimpleDateFormat("MM/dd/yyyy");
        String datafirstpaymentformat = ffff.format(new Date(firstpaymentdate));

        addpaymentdate = datafirstpaymentformat;

        LocalDate minusdays11 = null;
        LocalDate minusdaysenddate11 = null;
        if (paymentfrequency.equals("Monthly")) {

//            int aaaa = Integer.parseInt("1");
//            c.add(Calendar.MONTH, aaaa);


            Calendar aCalendar = Calendar.getInstance();
            String fDate111 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(addpaymentdate));
            aCalendar.setTime(new Date(fDate111));

            aCalendar.set(Calendar.DATE, 1);
            aCalendar.add(Calendar.DAY_OF_MONTH, -1);
            Date lastDateOfPreviousMonth = aCalendar.getTime();
            aCalendar.set(Calendar.DATE, 1);
            Date firstDateOfPreviousMonth = aCalendar.getTime();


            Log.e("snbnsdb",""+firstDateOfPreviousMonth+"  "+lastDateOfPreviousMonth);

            String fist = new SimpleDateFormat("MM/dd/yyyy").format(firstDateOfPreviousMonth);
            String end11 = new SimpleDateFormat("MM/dd/yyyy").format(lastDateOfPreviousMonth);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            minusdays11 = LocalDate.parse(fist,formatter);
            minusdaysenddate11 = LocalDate.parse(end11,formatter);




        } else if (paymentfrequency.equals("Weekly")) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate today = LocalDate.parse(addpaymentdate, formatter);

          //  LocalDate today = LocalDate.parse("addpaymentdate");
            LocalDate lastMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
            LocalDate lastFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));


             minusdays11 = lastMonday.minusDays( 9 ) ;
             minusdaysenddate11 = lastMonday.minusDays( 3 ) ;

            Log.e("gdvdbvdb",minusdays11.toString());

        } else if (paymentfrequency.equals("Fortnightly")) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate today = LocalDate.parse(addpaymentdate, formatter);

            //  LocalDate today = LocalDate.parse("addpaymentdate");
            LocalDate lastMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));


            minusdays11 = lastMonday.minusDays( 16 ) ;
            minusdaysenddate11 = lastMonday.minusDays( 3 ) ;

            Log.e("gdvdbvdb",minusdays11.toString());

        }

        UserPaycheckDatespojo userPaycheckDatespojo11= new UserPaycheckDatespojo();
        userPaycheckDatespojo11.PaycheckStartDate=minusdays11.toString();
        userPaycheckDatespojo11.PaycheckEndDate=minusdaysenddate11.toString();
        userPaycheckDatespojo11.EmploymentID=key;
        userPaycheckDatespojo11.PaycheckDate=addpaymentdate;
        userPaycheckDatespojo11.Userid=userid;
        userPaycheckDatespojo11.PaycheckFrequency=paymentfrequency;

        userPaycheckDatespojo11.PaycheckID=String.valueOf(1);

        String fDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(addpaymentdate));
        String gsfhsghshsa11=  FiscalDate.displayFinancialDate(FiscalDate.setDate(fDate));
        userPaycheckDatespojo11.PaycheckFy=gsfhsghshsa11;

        paycheckDatespojos.add(userPaycheckDatespojo11);


       // if (paymentfrequency.equals("Fortnightly")){

//        2023-05-29   09/29/2023  4
//
//        2023-05-29 2023-06-29 4    5,12,19,22

            for (int i = 0; i <= Integer.valueOf(paymentratedata); i++) {
                addfornightryworkweklymethod(i);
            }

//        }else {
//
//            for (int i = 0; i <= Integer.valueOf(paymentratedata); i++) {
//                addfornightryworkweklymethod(i);
//            }
//
      //  }


//        if (paymentfrequency.equals("Monthly")) {
//
////            int aaaa = Integer.parseInt("1");
////            c.add(Calendar.MONTH, aaaa);
//
//
//            int postionlastdata =paycheckDatespojos.size()-1;
//
//            String checkdatelesser=paycheckDatespojos.get(postionlastdata).PaycheckDate;
//
//
//            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
//
//
//            String fDate111 = new SimpleDateFormat("MM/dd/yyyy").format(new Date(checkdatelesser));
//
//            String fDateendemployeedate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(enddate));
//
//
////            try {
////
////
////                Date date111 = sdf.parse(fDate111);
////                Date date211 = sdf.parse(fDateendemployeedate);
////
////                if (date111.compareTo(date211) > 0) {
////                    // Log.i("app", "Date1 is after Date2");
////
////                } else if (date111.compareTo(date211) < 0) {
//                    // Log.i("app", "Date1 is before Date2");
//
//                  //  addleavedata.Ishitorical = "Y";
//
//
//
////
////            String dt = checkdatelesser;
////            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
////            Calendar c = Calendar.getInstance();
////            try {
////                c.setTime(sdf.parse(dt));
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
////
////            int aaaa = Integer.parseInt("1");
////            c.add(Calendar.MONTH, aaaa);
////            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
////            String output = sdf1.format(c.getTime());
////
//
//
//                    String dt = checkdatelesser;
//                    SimpleDateFormat sdf1010 = new SimpleDateFormat("MM/dd/yyyy");
//                    Calendar c = Calendar.getInstance();
//                    try {
//                        c.setTime(sdf1010.parse(dt));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    int aaaa = Integer.parseInt("1");
//                    c.add(Calendar.MONTH, aaaa);
//
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
//                    String output = sdf1.format(c.getTime());
//
//
//
//
//                    LocalDate minusdays = null;
//                    LocalDate minusdaysenddate = null;
//                    Calendar aCalendar = Calendar.getInstance();
//
//                    String fDate111qqqqqqq = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(output));
//
//                    aCalendar.setTime(new Date(fDate111qqqqqqq));
//
//                    aCalendar.set(Calendar.DATE, 1);
//                    aCalendar.add(Calendar.DAY_OF_MONTH, -1);
//                    Date lastDateOfPreviousMonth = aCalendar.getTime();
//                    aCalendar.set(Calendar.DATE, 1);
//                    Date firstDateOfPreviousMonth = aCalendar.getTime();
//
//
//                    Log.e("snbnsdb",""+firstDateOfPreviousMonth+"  "+lastDateOfPreviousMonth);
//
//                    String fist = new SimpleDateFormat("MM/dd/yyyy").format(firstDateOfPreviousMonth);
//                    String end11 = new SimpleDateFormat("MM/dd/yyyy").format(lastDateOfPreviousMonth);
//
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//                    minusdays = LocalDate.parse(fist,formatter);
//                    minusdaysenddate = LocalDate.parse(end11,formatter);
//
//
//
//
//
//
//
//
//                    UserPaycheckDatespojo userPaycheckDatespojo= new UserPaycheckDatespojo();
//                    userPaycheckDatespojo.PaycheckStartDate=minusdays.toString();
//                    userPaycheckDatespojo.PaycheckEndDate=minusdaysenddate.toString();
//                    userPaycheckDatespojo.EmploymentID=key;
//                    userPaycheckDatespojo.PaycheckDate=output;
//                    userPaycheckDatespojo.Userid=userid;
//                    userPaycheckDatespojo.PaycheckFrequency=paymentfrequency;
//
//                    userPaycheckDatespojo.PaycheckID=String.valueOf(paycheckDatespojos.size()+1);
//
//
//                    String gsfhsghshsa=  FiscalDate.displayFinancialDate(FiscalDate.setDate(output));
//                    userPaycheckDatespojo.PaycheckFy=gsfhsghshsa;
//
//                    paycheckDatespojos.add(userPaycheckDatespojo);
//
//
//
//
//
////                } else if (date111.compareTo(date211) == 0) {
////                   // addleavedata.Ishitorical = "Y";
////                    // Log.i("app", "Date1 is equal to Date2");
////                }
//
//
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//
//
//        }




       // UserFinancialBalancespojo userFinancialBalancespojo= new UserFinancialBalancespojo();
      //  financialBalancespojos.add(userFinancialBalancespojo);


//        Log.e("aa",);



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addfornightryworkweklymethod(int i) {






            //  if (frequency != "" && startdate != "") {
            //  Log.e("freq", frequency);
//        String dt = "09/24/2018";  // Start date
            String dt = addpaymentdate;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dt));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

            if (paymentfrequency.equals("Monthly")) {

                int aaaa = Integer.parseInt("1");
                c.add(Calendar.MONTH, aaaa);

            } else if (paymentfrequency.equals("Weekly")) {
                int aaaa = Integer.parseInt("1");
                int multi = aaaa * 7;

                c.add(Calendar.DAY_OF_WEEK, multi);





            } else if (paymentfrequency.equals("Fortnightly")) {
                int aaaa = Integer.parseInt("1");
                int multi = aaaa * 14;

                c.add(Calendar.DAY_OF_WEEK, multi);

            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
            String output = sdf1.format(c.getTime());

            addpaymentdate=output;

            Log.e("aa",output);




            LocalDate minusdays = null;
            LocalDate minusdaysenddate = null;
            if (paymentfrequency.equals("Monthly")) {


                Calendar aCalendar = Calendar.getInstance();
                String fDate111 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(addpaymentdate));
                aCalendar.setTime(new Date(fDate111));

                aCalendar.set(Calendar.DATE, 1);
                aCalendar.add(Calendar.DAY_OF_MONTH, -1);
                Date lastDateOfPreviousMonth = aCalendar.getTime();
                aCalendar.set(Calendar.DATE, 1);
                Date firstDateOfPreviousMonth = aCalendar.getTime();


                Log.e("snbnsdb",""+firstDateOfPreviousMonth+"  "+lastDateOfPreviousMonth);

                String fist = new SimpleDateFormat("MM/dd/yyyy").format(firstDateOfPreviousMonth);
                String end11 = new SimpleDateFormat("MM/dd/yyyy").format(lastDateOfPreviousMonth);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                minusdays = LocalDate.parse(fist,formatter);
                minusdaysenddate = LocalDate.parse(end11,formatter);



//            int aaaa = Integer.parseInt("1");
//            c.add(Calendar.MONTH, aaaa);

            } else if (paymentfrequency.equals("Weekly")) {
//            int aaaa = Integer.parseInt("1");
//            int multi = aaaa * 7;
//
//            c.add(Calendar.DAY_OF_WEEK, multi);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate today = LocalDate.parse(addpaymentdate, formatter);

                //  LocalDate today = LocalDate.parse("addpaymentdate");
                LocalDate lastMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
                LocalDate lastFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));


                minusdays = lastMonday.minusDays( 9 ) ;
                minusdaysenddate = lastMonday.minusDays( 3 ) ;

                Log.e("gdvdbvdb",minusdays.toString());

            } else if (paymentfrequency.equals("Fortnightly")) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate today = LocalDate.parse(addpaymentdate, formatter);

                //  LocalDate today = LocalDate.parse("addpaymentdate");
                LocalDate lastMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));


                minusdays = lastMonday.minusDays( 16 ) ;
                minusdaysenddate = lastMonday.minusDays( 3 ) ;

                Log.e("gdvdbvdb",minusdays.toString());

            }







//                temp.add(output);
//                // binding.loanform.repaymentenddate.setText(output);
//                startdate=output;

            UserPaycheckDatespojo userPaycheckDatespojo= new UserPaycheckDatespojo();
            userPaycheckDatespojo.PaycheckStartDate=minusdays.toString();
            userPaycheckDatespojo.PaycheckEndDate=minusdaysenddate.toString();
            userPaycheckDatespojo.EmploymentID=key;
            userPaycheckDatespojo.PaycheckDate=output;
            userPaycheckDatespojo.Userid=userid;
            userPaycheckDatespojo.PaycheckFrequency=paymentfrequency;

            userPaycheckDatespojo.PaycheckID=String.valueOf(i+2);


            String gsfhsghshsa=  FiscalDate.displayFinancialDate(FiscalDate.setDate(output));
            userPaycheckDatespojo.PaycheckFy=gsfhsghshsa;

            paycheckDatespojos.add(userPaycheckDatespojo);


            UserEmploymentpaycheckpojo employmentpaycheckpojo= new UserEmploymentpaycheckpojo();
            employmentpaycheckpojo.EmploymentID=key;
            employmentpaycheckpojo.PaycheckDate=output;
            employmentpaycheckpojo.UserID=userid;
            userEmploymentpaycheckpojos.add(employmentpaycheckpojo);


            //   }






    }


    private void calldataworkschede() {

        // https://stackoverflow.com/questions/53152785/find-if-child-inside-child-data-is-exist-or-not-android-firebase





        String typechild="";

        if (user.getRegistertype().equals("google")){

            typechild=user.getUserid();

        }else {

            typechild=user.getPhone_number();

        }

        List<UserFinancialBalancespojo> fcmUsers = new ArrayList<>();

        DatabaseReference zonesRef = FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference().child("UserFinancialBalances");

        Query zonesQuery = zonesRef.orderByChild("UserID").equalTo(typechild);
        zonesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    //bus number exists in Database



                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();

                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        UserFinancialBalancespojo fcmUser = dataSnapshotChild.getValue(UserFinancialBalancespojo.class);
                        String Historical_aCC = dataSnapshotChild.child("FinancialYear").getValue(String.class);
                        fcmUsers.add(fcmUser);
                        fcmUsers11111.add(Historical_aCC);

                    }


                    callcheckexistfinancealyear();


                    // Check your arraylist size and pass to list view like
//                    if(fcmUsers.size()>0)
//                    {
//
//                        Log.e("kdmdnmd","dnmnsdb ");
//
//                    }else
//                    {
//
//                    }


                } else {
                    //bus number doesn't exists.
                   // binding.financeuseridnotfound.setVisibility(View.VISIBLE);

                    callcheckexistfinancealyear();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.cancel();
                Log.w("mxnk", "onCancelled", databaseError.toException());
            }
        });

    }

    private void callcheckexistfinancealyear() {



        HashMap<String, Object> update = new HashMap<>();

        if (values.size()==1) {

            if (!fcmUsers11111.contains(values.get(0))) {
                UserFinancialBalancespojo financialBalancespojos = new UserFinancialBalancespojo();
                financialBalancespojos.UserID = userid;
                financialBalancespojos.FinancialYear = values.get(0);
                update.put("UserFinancialBalances/" + key, financialBalancespojos);  // +key
            }
        }else if (values.size()==2){

            if (!fcmUsers11111.contains(values.get(0))) {
                UserFinancialBalancespojo financialBalancespojos = new UserFinancialBalancespojo();
                financialBalancespojos.UserID = userid;
                financialBalancespojos.FinancialYear = values.get(0);
                update.put("UserFinancialBalances/" + key+"1", financialBalancespojos);
            }

            if (!fcmUsers11111.contains(  values.get(1))) {
                UserFinancialBalancespojo financialBalancespo22222 = new UserFinancialBalancespojo();
                financialBalancespo22222.UserID = userid;
                financialBalancespo22222.FinancialYear = values.get(1);
                update.put("UserFinancialBalances/" + key+"2", financialBalancespo22222);
            }




        }else if (values.size()==3){
            if (!fcmUsers11111.contains(values.get(0))) {
                UserFinancialBalancespojo financialBalancespojos = new UserFinancialBalancespojo();
                financialBalancespojos.UserID = userid;
                financialBalancespojos.FinancialYear = values.get(0);
                update.put("UserFinancialBalances/" + key+"1", financialBalancespojos);
            }

            if (!fcmUsers11111.contains(  values.get(1))) {
                UserFinancialBalancespojo financialBalancespo22222 = new UserFinancialBalancespojo();
                financialBalancespo22222.UserID = userid;
                financialBalancespo22222.FinancialYear = values.get(1);
                update.put("UserFinancialBalances/" + key+"2", financialBalancespo22222);
            }

            if (!fcmUsers11111.contains(  values.get(2))) {
                UserFinancialBalancespojo financialBalancespo33333 = new UserFinancialBalancespojo();
                financialBalancespo33333.UserID = userid;
                financialBalancespo33333.FinancialYear = values.get(2);
                update.put("UserFinancialBalances/" + key+"3", financialBalancespo33333);
            }



        }else if(values.size()==4){
            if (!fcmUsers11111.contains(values.get(0))) {
                UserFinancialBalancespojo financialBalancespojos = new UserFinancialBalancespojo();
                financialBalancespojos.UserID = userid;
                financialBalancespojos.FinancialYear = values.get(0);
                update.put("UserFinancialBalances/" + key+"1", financialBalancespojos);
            }

            if (!fcmUsers11111.contains(  values.get(1))) {
                UserFinancialBalancespojo financialBalancespo22222 = new UserFinancialBalancespojo();
                financialBalancespo22222.UserID = userid;
                financialBalancespo22222.FinancialYear = values.get(1);

                update.put("UserFinancialBalances/" + key+"2", financialBalancespo22222);
            }

            if (!fcmUsers11111.contains(  values.get(2))) {
                UserFinancialBalancespojo financialBalancespo33333 = new UserFinancialBalancespojo();
                financialBalancespo33333.UserID = userid;
                financialBalancespo33333.FinancialYear = values.get(2);
                update.put("UserFinancialBalances/" + key+"3", financialBalancespo33333);
            }


            if (!fcmUsers11111.contains(  values.get(3))) {
                UserFinancialBalancespojo financialBalancespo44444 = new UserFinancialBalancespojo();
                financialBalancespo44444.UserID = userid;
                financialBalancespo44444.FinancialYear = values.get(3);

                update.put("UserFinancialBalances/" + key+"4", financialBalancespo44444);
            }


        }



        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("Useremploymentleaveschedule")
//                .child(key)
                .updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

                dialog.cancel();

                if(task.isSuccessful()){



                    // getActivity().getFragmentManager().beginTransaction().remove(getParentFragment()).commit();
                    Toast.makeText(AddEmployeeDetaileFragment.this, "Success", Toast.LENGTH_SHORT).show();
                    onBackPressed();



                } else {

                    dialog.cancel();
                    Toast.makeText(AddEmployeeDetaileFragment.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}