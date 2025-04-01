package com.example.gplusin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gplusin.databinding.ActivityMainBinding;
import com.example.gplusin.fragment.addupdate.AddViewFragment;
import com.example.gplusin.fragment.employment_details.EmployeeDetailsHistory;
import com.example.gplusin.fragment.leave.LeaveFragment;
import com.example.gplusin.fragment.yeprojection.YeProjectionFragment;
import com.example.gplusin.interface111111.Clickemployedetails;
import com.example.gplusin.interface111111.Updateemployeedetail;
import com.example.gplusin.pojo.EmployeeDetailsMainOne;
import com.example.gplusin.profile.Profile;
import com.example.gplusin.utils.DataUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Clickemployedetails, Updateemployeedetail {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    ProgressDialog dialog1111;
    HashMap<String, Object> room;
    User user;
    boolean datachanege =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.changeColor));

        dialog1111 = new ProgressDialog(MainActivity.this);
        dialog1111.setMessage("Please Wait...");

        user=new User(MainActivity.this);




try {

    if (user.getName().contains(" ")) {
        String[] getdata = user.getName().split(" ");

//        TextDrawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .textColor(Color.BLACK)
//                .fontSize(36) /* size in px */
//                .bold()
//                .toUpperCase()
//                .endConfig()
//                .buildRound(getdata[0].substring(0, 1) + getdata[1].substring(0, 1), Color.WHITE);
//
//        binding.appbar.profile.setImageDrawable(drawable);

//            MaterialTextDrawable.Companion.with(MainActivity.this)
//                    .text("AK")
//                    .into(binding.appbar.profile);


    } else {


//        TextDrawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .textColor(Color.BLACK)
//                .fontSize(36) /* size in px */
//                .bold()
//                .toUpperCase()
//                .endConfig()
//                .buildRound(String.valueOf(user.getName().substring(0, 1)), Color.WHITE);
//
//
//        binding.appbar.profile.setImageDrawable(drawable);
    }
}catch (Exception e){


}






//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
//
//        Map<String, Object> update = new HashMap<>();
//        update.put("users/user1/pets/pet4", true);
//        update.put("pets/pet4/owner", "user4");
//
//        dbRef.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful())
//                    Toast.makeText(MainActivity.this, "sfaghabsvbvs", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(MainActivity.this, "aaaaaaa", Toast.LENGTH_SHORT).show();
//
//            }
//        });



//        String dataget = "{\n" +
//                "  \"name\":\"Belal Khan\",\n" +
//                "  \"address\":\"Ranchi\",\n" +
//                "  \"college\":\"St. Xavier's College\",\n" +
//                "  \"age\":24,\n" +
//                "  \"dob\":\"30-04-1993\"\n" +
//                "}";
//        JSONObject jObj = null;
//        try {
//            jObj = new JSONObject(dataget);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            String date = jObj.getString("name");
//
//            Toast.makeText(MainActivity.this, ""+date, Toast.LENGTH_SHORT).show();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        YeProjectionFragment newFragment = new YeProjectionFragment();
        FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
        transactionhome.replace(R.id.fragment_containerhome, newFragment);
        transactionhome.addToBackStack(null);
        transactionhome.commit();

        binding.content.comingtext.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));
        binding.content.comming.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
        binding.content.imgCommingimage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));


        binding.content.txtTabHome.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
        binding.content.linTabHome.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
        binding.content.imgTabHome.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));



        binding.appbar.nois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendailog();
            }
        });

        binding.content.comming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.content.comingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.comming.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.imgCommingimage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));

//                binding.content.texthistorical.setTextColor(Color.parseColor("#02272C"));
//                binding.content.historical.setBackgroundColor(Color.parseColor("#ECC13D"));
//                binding.content.imghistorical.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));



                binding.content.txtTabHome.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.linTabHome.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.imgTabHome.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));


                binding.content.txtTableave.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.linleave.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.imgTableave.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));



//                AddUpdateFragment newFragment = new AddUpdateFragment();

                AddViewFragment newFragment = new AddViewFragment();
                FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
                transactionhome.replace(R.id.fragment_containerhome, newFragment);
                transactionhome.addToBackStack(null);
                transactionhome.commit();
            }
        });

        binding.content.linleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.content.txtTabHome.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.linTabHome.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.imgTabHome.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));


                binding.content.comingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.comming.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.imgCommingimage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));

                binding.content.txtTableave.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.linleave.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.imgTableave.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));


                LeaveFragment newFragment = new LeaveFragment();
                FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
                transactionhome.replace(R.id.fragment_containerhome, newFragment);
                transactionhome.addToBackStack(null);
                transactionhome.commit();




            }
        });


        binding.content.linTabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YeProjectionFragment newFragment = new YeProjectionFragment();
                FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
                transactionhome.replace(R.id.fragment_containerhome, newFragment);
                transactionhome.addToBackStack(null);
                transactionhome.commit();

                binding.content.comingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.comming.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.imgCommingimage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));

//                binding.content.texthistorical.setTextColor(Color.parseColor("#02272C"));
//                binding.content.historical.setBackgroundColor(Color.parseColor("#ECC13D"));
//                binding.content.imghistorical.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));



                binding.content.txtTabHome.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.linTabHome.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.imgTabHome.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));


                binding.content.txtTableave.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.changeColor));
                binding.content.linleave.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                binding.content.imgTableave.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.changeColor)));

            }
        });



        binding.appbar.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Profile.class));
                overridePendingTransition(0,0);
            }
        });

    }

//    private void callhistoricaldata() {
//
//        String typechild="";
//
//        if (user.getRegistertype().equals("google")){
//
//            typechild=user.getUserid();
//
//        }else {
//
//            typechild=user.getPhone_number();
//
//        }
//
//
//        Log.e("typechild",typechild);
//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(typechild).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//             //   for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {
//
//                    if(snapshot.child("historical_data").exists()) {
//
//                        //  String name = snapshot.child("name").getValue(String.class);
//
//                        //  Toast.makeText(MainActivity.this, "true", Toast.LENGTH_SHORT).show();
//                    }else {
//
//
//                        //  Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
//
//                        if (datachanege==true) {
//                            showhistoricaldata();
//                            datachanege=false;
//                        }
//
//                    }
//             //   }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }

//    private void showhistoricaldata() {
//
//
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogTheme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.activity_historical_add);
//
//
//        MaterialRadioButton yes=dialog.findViewById(R.id.yes);
//        MaterialRadioButton no=dialog.findViewById(R.id.no);
//
//        MaterialButton submit=dialog.findViewById(R.id.submit);
//
//
//       yes.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                no.setChecked( false );
//               yes.setChecked( true );
//            }
//        } );
//
//        //bb
//        no.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                no.setChecked( true );
//                yes.setChecked( false );
//
//
//
//            }
//        } );
//
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (yes.isChecked() == true){
//
//                  //  addextraemi();
//
//                    dialog.dismiss();
//                    openfeilddialog();
//
//                }else if (no.isChecked() == true){
//
//                    dialog.dismiss();
//                    noselect();
//
//
//                }
//
//
//            }
//        });
//
//
//        try {
//            dialog.show();
//        }
//        catch (WindowManager.BadTokenException e) {
//            //use a log message
//        }
//
//    }

//    private void noselect() {
//
//
//        HashMap<String, Object> room = new HashMap<>();
//        room.put("Historical_income", "0");
//        room.put("Historical_tax", "0");
//        room.put("Historical_aCC", "0");
//        room.put("Historical_gST_collection", "0");
//        room.put("Historical_gST_claimed", "0");
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
//        dialog1111.show();
//        Log.e("aaaa",userid);
//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(userid)
//                .child("historical_data").updateChildren(room).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//                dialog1111.cancel();
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                datachanege=false;
//            }
//        });
//
//
//
//    }

//    private void openfeilddialog() {
//
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogTheme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.activity_historical_add_detaile);
//
//
//        MaterialButton submit=dialog.findViewById(R.id.submit);
//
//
//
//        AppCompatEditText Historical_income=dialog.findViewById(R.id.Historical_income);
//        AppCompatEditText Historical_tax=dialog.findViewById(R.id.Historical_tax);
//        AppCompatEditText Historical_aCC=dialog.findViewById(R.id.Historical_aCC);
//        AppCompatEditText Historical_gST_collection=dialog.findViewById(R.id.Historical_gST_collection);
//
//        AppCompatEditText Historical_gST_claimed=dialog.findViewById(R.id.Historical_gST_claimed); //Historical_gST_collection
//
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(Historical_income.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter Historical Income", Toast.LENGTH_SHORT ).show();
//                    return;
//                }
//
//                if(Historical_tax.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter Historical Tax", Toast.LENGTH_SHORT ).show();
//                    return;
//                }
//
//                if(Historical_aCC.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter Historical ACC", Toast.LENGTH_SHORT ).show();
//                    return;
//                }
//
//                if(Historical_gST_collection.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter Historical GST Collection", Toast.LENGTH_SHORT ).show();
//                    return;
//                }
//
//                if(Historical_gST_claimed.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter Historical GST Claimed", Toast.LENGTH_SHORT ).show();
//                    return;
//                }
//
//
//              //  setvalue();
//
//                HashMap<String, Object> room = new HashMap<>();
//                room.put("Historical_income", Historical_income.getText().toString());
//                room.put("Historical_tax", Historical_tax.getText().toString());
//                room.put("Historical_aCC", Historical_aCC.getText().toString());
//                room.put("Historical_gST_collection", Historical_gST_collection.getText().toString());
//                room.put("Historical_gST_claimed", Historical_gST_claimed.getText().toString());
//
//
//
//                String userid="";
//
//                if (user.getRegistertype().equals("register")){
//
//                    userid=user.getPhone_number();
//                }else {
//
//                    userid=user.getUserid();
//
//                }
//
//
//
//
//
//                dialog.dismiss();
//                dialog1111.show();
//                FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                        .child("profiles")
//                        .child(userid)
//                        .child("historical_data").updateChildren(room).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//
//                        dialog1111.cancel();
//                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        dialog1111.cancel();
//                        datachanege=false;
//                    }
//                });
//
//
//
//
//
//
////                FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
////                        .child("profiles")
////                        .child(userid).addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
////                        if(snapshot.child("status").exists()) {
////                            if(snapshot.child("status").getValue(Integer.class) == 1) {
////
////
////                            }
////                        }
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
////
////                    }
////                });
//
//
//
//
//
//
//            }
//        });
//
//
//
//        try {
//
//            dialog.show();
//
//        }catch (Exception e){
//
//        }
//
//
//
//
//    }

    @Override
    public void clickbuton(String no) {

//        if (no.equals("1")) {
//            AddDetaileFragment newFragment = new AddDetaileFragment();
//            FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
//            transactionhome.replace(R.id.fragment_containerhome, newFragment);
//            transactionhome.addToBackStack(null);
//            transactionhome.commit();
//         }else if (no.equals("2")){
//
//            AddUpdateFragment newFragment = new AddUpdateFragment();
//            FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
//            transactionhome.replace(R.id.fragment_containerhome, newFragment);
//            transactionhome.addToBackStack(null);
//            transactionhome.commit();
//
//        }
    }

    @Override
    public void updatedetaileonclick() {

//        AddDetaileUpdateFragment newFragment = new AddDetaileUpdateFragment();
//        FragmentTransaction transactionhome = getSupportFragmentManager().beginTransaction();
//        transactionhome.replace(R.id.fragment_containerhome, newFragment);
//        transactionhome.addToBackStack(null);
//        transactionhome.commit();

    }

//    private void setvalue() {
//
//       // register
//
//        String userid="";
//
//        if (user.getRegistertype().equals("register")){
//
//           userid=user.getPhone_number();
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
//
//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(userid).updateChildren(room).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//
//
//
//
//
//
//        FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                .child("profiles")
//                .child(userid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.child("status").exists()) {
//                    if(snapshot.child("status").getValue(Integer.class) == 1) {
//
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }


//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(MainActivity.this, "onRestart", Toast.LENGTH_SHORT).show();
//        Log.e("aaaaa","onRestart");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_SHORT).show();
//        Log.e("aaaaa","onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(MainActivity.this, "onResume", Toast.LENGTH_SHORT).show();
//        Log.e("aaaaa","onResume");
//    }


    @Override
    protected void onStop() {
        super.onStop();

        Log.e("checkcall","onStopActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("checkcall","onPauseActivity");
    }


    private void opendailog() {

        final Dialog dialog2222 = new Dialog(this);

        //, R.style.CustomDialog

        //dialog2222.startAnimation(animFadeIn);
        dialog2222.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog2222.setCancelable(false);
        // dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;

//        ((ViewGroup)dialog.getWindow().getDecorView())
//                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
//                context,android.R.anim.slide_in_left));


        dialog2222.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater li = (LayoutInflater) getSystemService( LAYOUT_INFLATER_SERVICE);
        View v1 = li.inflate(R.layout.employeeitem_2, null, false);
        dialog2222.setContentView(v1);


        AppCompatTextView txtTitle112=v1.findViewById( R.id.txtTitle112 );

        AppCompatTextView txtMessage2=v1.findViewById(R.id.txtMessage2);
        AppCompatTextView txtMessage3=v1.findViewById(R.id.txtMessage3);

        AppCompatTextView txtTitle114=v1.findViewById( R.id.txtMessage4 );

        AppCompatTextView txtMessage5=v1.findViewById(R.id.txtMessage5);
        AppCompatTextView txtMessage6=v1.findViewById(R.id.txtMessage6);

        AppCompatTextView txtMessage7=v1.findViewById(R.id.txtMessage7);

        AppCompatTextView txtMessage8=v1.findViewById(R.id.txtMessage8);
        AppCompatTextView txtMessage9=v1.findViewById(R.id.txtMessage9);
        AppCompatTextView txtMessage10=v1.findViewById(R.id.txtMessage10);



        txtTitle112.setText("");
        txtMessage2.setText("Start Date: ");

        txtMessage3.setText("End Date:  ");

        txtTitle114.setText("Daily Hour:  ");
        txtMessage5.setText("Payment Rate:  ");
        txtMessage7.setText("Without Tax Rate:  ");

        txtMessage8.setText("First Payment Date:  ");
        txtMessage9.setText("Payment Frequency:  ");

        txtMessage10.setText("Employment Region:  ");

        txtMessage6.setText("GST Registered:  "+"No");

        txtMessage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2222.dismiss();
            }
        });


        dialog2222.show();
        dialog2222.closeOptionsMenu();
        dialog2222.setCanceledOnTouchOutside( false );
        Window window = dialog2222.getWindow();
        window.setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);




    }
}