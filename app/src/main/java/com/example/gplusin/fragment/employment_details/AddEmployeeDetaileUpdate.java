package com.example.gplusin.fragment.employment_details;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.databinding.FragmentAddDetaileUpdateBinding;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DataUtils;
import com.example.gplusin.utils.DatePickerFragment;
import com.example.gplusin.utils.DatePickerFragmentEndDate;
import com.example.gplusin.utils.DatePickerFragmentFirstDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEmployeeDetaileUpdate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEmployeeDetaileUpdate extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static final long ONE_DAY = 24 * 60 * 60 * 1000L;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentAddDetaileUpdateBinding binding;

    String startdate="",enddate="",paymentratestring="Months",gstregisterstring="",firstpaymentdate="",paymentfrequency="",employeeregionString="";

    ProgressDialog dialog;

    User user;

    ApiServise apiServise;

    public AddEmployeeDetaileUpdate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDetaileUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static AddDetaileUpdateFragment newInstance(String param1, String param2) {
//        AddDetaileUpdateFragment fragment = new AddDetaileUpdateFragment();
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
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//       // return inflater.inflate(R.layout.fragment_add_detaile_update, container, false);
//
//
//        binding = FragmentAddDetaileUpdateBinding.inflate(inflater, container, false);
//        //return inflater.inflate(R.layout.fragment_add_detaile, container, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_detaile_update);

        binding = FragmentAddDetaileUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));


        apiServise= Common.getAPI();
        dialog = new ProgressDialog(AddEmployeeDetaileUpdate.this);
        dialog.setMessage("Please Wait...");
        user=new User(AddEmployeeDetaileUpdate.this);


        try {
            binding.employeename.setText(Common.updatedetaileemployee.employeename);
            binding.daikyhour.setText(Common.updatedetaileemployee.daikyhour);


            binding.paymentfrequency.setText(Common.updatedetaileemployee.frequency);


            if (Common.updatedetaileemployee.gstregisterstring.equals("1")) {

                gstregisterstring="Yes";
                binding.gstregister.setText("Yes");

            }

            else {
                gstregisterstring="No";
                binding.gstregister.setText("No");

            }

            binding.withoutTaxRate.setText(Common.updatedetaileemployee.withoutTaxRate);

            binding.txtstartdate.setText(String.valueOf(DataUtils.formatDate(Common.updatedetaileemployee.startdate)));
            binding.txtendDate.setText( String.valueOf(DataUtils.formatDate(Common.updatedetaileemployee.enddate)));

            binding.txtpaymentdatefirst.setText(String.valueOf(DataUtils.formatDate(Common.updatedetaileemployee.firstpayment)));

            binding.employeeregion.setText(Common.updatedetaileemployee.employeeregion);

        }catch (Exception exception){

        }



        startdate= String.valueOf(DataUtils.formatDate(Common.updatedetaileemployee.startdate));
        enddate= String.valueOf(DataUtils.formatDate(Common.updatedetaileemployee.enddate));

        firstpaymentdate= String.valueOf(DataUtils.formatDate(Common.updatedetaileemployee.firstpayment));
        paymentfrequency=Common.updatedetaileemployee.frequency;
        employeeregionString=Common.updatedetaileemployee.employeeregion;


      //  String[] dada=Common.updatedetaileemployee.paymentratestring.split(" ");

        binding.paymentrateeditedit.setText(Common.updatedetaileemployee.paymentratestring);
        binding.paymentrate.setText(Common.updatedetaileemployee.Paymentratefrequency);

        paymentratestring=Common.updatedetaileemployee.Paymentratefrequency;


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
                        startdate=date;

                        binding.txtpaymentdatefirst.setText("");
                        firstpaymentdate="";
                    }
                });

                newFragment.show(AddEmployeeDetaileUpdate.this.getSupportFragmentManager(), "datePicker");



            }
        });



        binding.employeenddateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

                DatePickerFragmentEndDate newFragment = new DatePickerFragmentEndDate();

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                b.putString("startdate", startdate);
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {
                        binding.txtendDate.setText(date);
                        enddate=date;


                        binding.txtpaymentdatefirst.setText("");
                        firstpaymentdate="";
                    }
                });

                newFragment.show(AddEmployeeDetaileUpdate.this.getSupportFragmentManager(), "datePicker");



            }
        });

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        binding.daikyhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailyhour();
            }
        });

        binding.firstpaymentdateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (enddate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select End Date", Toast.LENGTH_SHORT ).show();
                    return;
                }






                DatePickerFragmentFirstDate newFragment = new DatePickerFragmentFirstDate();

                // getDatesBetween(startdate,enddate)
                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                b.putString("startdate", startdate);
                b.putString("enddate",enddate );
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {
                        binding.txtpaymentdatefirst.setText(date);
                        firstpaymentdate=date;
                    }
                });

                newFragment.show(AddEmployeeDetaileUpdate.this.getSupportFragmentManager(), "datePicker");


            }
        });


        binding.employeeregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeregion();
            }
        });

        binding.paymentfrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentfrequency11111111();
            }
        });



        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.employeename.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Enter Employer Name", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (startdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select Start Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

//               if (agreed.equals("")) {
//                   Toast.makeText(GiveActivity.this, "Agreed Empty", Toast.LENGTH_SHORT ).show();
//                   return;
//               }

                if (enddate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select End Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.daikyhour.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Enter Standard Daily Hour", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.paymentrateeditedit.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Enter Payment Rate", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (paymentratestring.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select Payment Rate", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (gstregisterstring.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Please confirm GST registration.", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (binding.withoutTaxRate.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Please provide Witholding tax rate.", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (firstpaymentdate.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select First Payment Date", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (paymentfrequency.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select Payment Frequency", Toast.LENGTH_SHORT ).show();
                    return;
                }


                if (employeeregionString.equals("")) {
                    Toast.makeText(AddEmployeeDetaileUpdate.this, "Select Employment Region", Toast.LENGTH_SHORT ).show();
                    return;
                }



                String userid="";
                userid=user.getUserid();

                HashMap<String, Object> room = new HashMap<>();

                String IsGSTregistered = "0";
                if(gstregisterstring.equals("Yes")){

                    IsGSTregistered = "1";
                }else {

                    IsGSTregistered = "0";
                }

                room.put("EmployerName", binding.employeename.getText().toString());
                room.put("EmploymentStartDate", startdate);
                room.put("EmploymentEndDate", enddate);
                room.put("StandardDailyHours", binding.daikyhour.getText().toString().trim());
                room.put("Paymentrateamount", binding.paymentrateeditedit.getText().toString().trim());
                room.put("Paymentratefrequency", paymentratestring);
                room.put("IsGSTregistered", IsGSTregistered);
                room.put("WitholdingTaxRate",binding.withoutTaxRate.getText().toString() );
                room.put("UserID", userid);
                room.put("FirstPaymentDate", firstpaymentdate);
                room.put("PaymentFrequency", paymentfrequency);
                room.put("EmploymentID", Common.updatedetaileemployee.keyid);
                room.put("EmploymentRegion", employeeregionString);
                room.put("ActionType", "3");


//                room.put("EffectiveBeginDate", "");
//                room.put("EffectiveEndDate", "");
//                room.put("Isdeleted", "N");



                dialog.show();

                apiServise.UseremploymentMainUpdate(room)
                        .enqueue( new Callback<RegisterData>() {
                            @Override
                            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                                dialog.cancel();
                                if (response.isSuccessful()) {
                                    if (response.body().getResult().equals( "Success" )) {

                                        Toast.makeText(AddEmployeeDetaileUpdate.this, "Success", Toast.LENGTH_SHORT).show();
                                        onBackPressed();

                                    } else {
                                        Toast.makeText(AddEmployeeDetaileUpdate.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
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
        });



      //  return binding.getRoot();
    }



    private void employeeregion() {


        final String[] mStrings = {"Auckland","Taranaki","Hawke's Bay","Wellington","Marlborough","Nelson","Canterbury",
                "Canterbury (South),Westland","Otago","Southland","Chatham Islands"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileUpdate.this );
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
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileUpdate.this );
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
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileUpdate.this );
        builder.setTitle( "Select Payment Frequency" );
        builder.setSingleChoiceItems( mStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  binding.linFrequencyedit.setText("");
                paymentfrequency=mStrings[which];
                dialog.dismiss();
                binding.paymentfrequency.setText( mStrings[which] );
            }
        } );
        builder.show();
    }

    private void callgstregister() {


        final String[] mStrings = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileUpdate.this );
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

        final String[] mStrings = {"Hours", "Daily","Weekly","Fortnightly","Monthly"};
        AlertDialog.Builder builder = new AlertDialog.Builder( AddEmployeeDetaileUpdate.this );
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
}