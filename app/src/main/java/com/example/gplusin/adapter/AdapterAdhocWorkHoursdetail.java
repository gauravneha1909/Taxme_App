package com.example.gplusin.adapter;

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.fragment.adhocworkhour.AdhocworkhourActivityUpdate;
import com.example.gplusin.pojo.LeaveData;
import com.example.gplusin.pojo.LeaveDataHistory;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAdhocWorkHoursdetail extends RecyclerView.Adapter<AdapterAdhocWorkHoursdetail.MyViewHolder> {
    Context context;

    View view;
    List<LeaveDataHistory> amnsm=new ArrayList<>();

    //  Updateemployeedetail updateemployeedetail;



    public AdapterAdhocWorkHoursdetail(Context context, List<LeaveDataHistory> amnsm) {
        this.context = context;
        this.amnsm = amnsm;

        //  updateemployeedetail =(Updateemployeedetail) context;

    }

    @NonNull
    @Override
    public AdapterAdhocWorkHoursdetail.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from( context ).inflate( R.layout.employeeitem_leave,parent,false );
        return new AdapterAdhocWorkHoursdetail.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdhocWorkHoursdetail.MyViewHolder holder, int position) {

        holder.txtTitle112.setText(DataUtils.formatDate(amnsm.get( position ).startdate) +" - "+DataUtils.formatDate(amnsm.get( position ).enddate) );
        holder.txtMessage2.setText("Days: "+amnsm.get( position ).total_days+ " Hours: "+amnsm.get( position ).total_hours);

        //  holder.txtMessage3.setText("End Date:  "+amnsm.get( position ).enddate);

        holder.txtDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<LeaveData> leaveData=new ArrayList<>();


                JSONArray jsonArr = null;
                try {
                    jsonArr = new JSONArray(amnsm.get(position).leavejson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < jsonArr.length(); i++)
                {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = jsonArr.getJSONObject(i);

                        LeaveData leaveData1=new LeaveData();
                        leaveData1.setDatename(jsonObj.getString("datename"));
                        leaveData1.setHour(jsonObj.getString("hour"));
                        leaveData.add(leaveData1);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    System.out.println(jsonObj);
                }


                opendailog(amnsm.get(position),leaveData);


            }
        });

        //  holder.txtMessage2.setHtml( notificationOne.data.get( position ).short_description,new HtmlHttpImageGetter( holder.txtMessage2 ) );




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder( context );
                builder.setMessage( "Deleting this Adhoc Work Hour will remove its data from Projection. Do you want to edit instead?" );
                builder.setNegativeButton( "No",(dialog, i) -> {
                    dialog.dismiss();
                    callopendailog(amnsm.get(position).keyid);
                } ).setPositiveButton( "Yes",(dialog, i) -> {
                    dialog.dismiss();

                    deleteelement(amnsm.get(position),position);

                } );

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( Color.RED );
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);


            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Common.updateleavedetaile=amnsm.get(position);
                context.startActivity(new Intent(context, AdhocworkhourActivityUpdate.class));
            }
        });



    }

    private void deleteelement(LeaveDataHistory leaveDataHistory, int position) {

        HashMap<String, Object> room = new HashMap<>();
        room.put("adhoc_work_hour_json_id", leaveDataHistory.adhoc_work_hour_json_id);
       // room.put("action", "3");


        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait...");
        ApiServise apiServise= Common.getAPI();
        dialog.show();

        apiServise.adhoc_work_hoursdelete(room)
                .enqueue( new Callback<RegisterData>() {
                    @Override
                    public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {

                        dialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals( "Success" )) {

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                amnsm.remove(amnsm.get(position));
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, amnsm.size()-position);

                            } else {
                                Toast.makeText(context, response.body().getResult(), Toast.LENGTH_SHORT).show();
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

    private void opendailog(LeaveDataHistory modal, List<LeaveData> leaveData) {

        final Dialog dialog2222 = new Dialog( context);

        //, R.style.CustomDialog

        //dialog2222.startAnimation(animFadeIn);
        dialog2222.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog2222.setCancelable(false);
        // dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;

//        ((ViewGroup)dialog.getWindow().getDecorView())
//                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
//                context,android.R.anim.slide_in_left));


        dialog2222.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater li = (LayoutInflater) context.getSystemService( LAYOUT_INFLATER_SERVICE);
        View v1 = li.inflate(R.layout.employeeitem_2_leave, null, false);
        dialog2222.setContentView(v1);


        AppCompatTextView txtTitle112=v1.findViewById( R.id.txtTitle112 );

        AppCompatTextView employeename11=v1.findViewById( R.id.employeename11 );

        AppCompatTextView txtMessage2=v1.findViewById(R.id.txtMessage2);
      //  AppCompatTextView txtMessage3=v1.findViewById(R.id.txtMessage3);

        RecyclerView txtMessage9=v1.findViewById(R.id.featureproduct);

        txtMessage9.setAdapter( new LeaveDateAdapter1(context,leaveData ) );


        employeename11.setText(modal.employeename);
        txtTitle112.setText("Total Days: "+modal.total_days);
        txtMessage2.setText(DataUtils.formatDate(modal.startdate)+" - "+DataUtils.formatDate(modal.enddate));

       // txtMessage3.setText("End Date:  "+modal.enddate);


        dialog2222.show();
        dialog2222.closeOptionsMenu();
        dialog2222.setCanceledOnTouchOutside( true );
        Window window = dialog2222.getWindow();
        window.setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);




    }


    @Override
    public int getItemCount() {
        return amnsm.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView txtTitle112;
        TextView txtMessage2;
        ImageView delete,update,txtDate2;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            txtTitle112=itemView.findViewById( R.id.txtTitle112 );

            txtDate2=itemView.findViewById(R.id.txtDate2);
            txtMessage2=itemView.findViewById(R.id.txtMessage2);
          //  txtMessage3=itemView.findViewById(R.id.txtMessage3);

            delete=itemView.findViewById(R.id.delete);
            update=itemView.findViewById(R.id.update);


        }
    }


    private void callopendailog(String keyid) {
        AlertDialog.Builder builder=new AlertDialog.Builder( context );
      //  builder.setTitle( "Are You Sure " );
        builder.setMessage( "Are you sure to delete?" );
        builder.setNegativeButton( "No",(dialog, i) -> {
            dialog.dismiss();
        } ).setPositiveButton( "Yes",(dialog, i) -> {


            User user=new User(context);

            String typechild="";

            if (user.getRegistertype().equals("google")){

                typechild=user.getUserid();

            }else {

                typechild=user.getPhone_number();

            }


            DatabaseReference ref = FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference();


            Query applesQuery = ref
                    // .child("profiles").child(typechild)
                    .child("adhoc_work_hours").child(keyid);  //.orderByChild("keyid").equalTo(amnsm.get(position).keyid)

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                        Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                    dialog.dismiss();
                }
            });





        } );

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( Color.RED );
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);

    }

}

