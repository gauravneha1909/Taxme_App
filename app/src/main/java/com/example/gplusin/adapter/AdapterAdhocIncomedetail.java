package com.example.gplusin.adapter;

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gplusin.R;
import com.example.gplusin.User;
import com.example.gplusin.data11.RegisterData;
import com.example.gplusin.fragment.adhoc_income.AdhocIncomeAdd;
import com.example.gplusin.fragment.adhoc_income.AdhocIncomeHistory;
import com.example.gplusin.fragment.adhoc_income.AdhocIncomeUpdate;
import com.example.gplusin.pojo.AdhocIncomepojo;
import com.example.gplusin.retrofit.ApiServise;
import com.example.gplusin.service.DownloadService;
import com.example.gplusin.utils.Common;
import com.example.gplusin.utils.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAdhocIncomedetail extends RecyclerView.Adapter<AdapterAdhocIncomedetail.MyViewHolder> {
    Context context;

    View view;
    List<AdhocIncomepojo> amnsm=new ArrayList<>();

    // Updateemployeedetail updateemployeedetail;



    public AdapterAdhocIncomedetail(Context context, List<AdhocIncomepojo> amnsm) {
        this.context = context;
        this.amnsm = amnsm;

        // updateemployeedetail =(Updateemployeedetail) context;

    }

    @NonNull
    @Override
    public AdapterAdhocIncomedetail.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from( context ).inflate( R.layout.employeeitem,parent,false );
        return new AdapterAdhocIncomedetail.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdhocIncomedetail.MyViewHolder holder, int position) {

        holder.txtTitle112.setText(amnsm.get( position ).income_source);
        holder.txtMessage2.setText("Amount: "+amnsm.get( position ).income_amount+ " Tax: "+amnsm.get( position ).tax_reduce_amount);

     //   holder.txtMessage3.setText("Tax Reduce Amount:  "+amnsm.get( position ).tax_reduce_amount);

        holder.txtDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opendailog(amnsm.get(position));
            }
        });

        //  holder.txtMessage2.setHtml( notificationOne.data.get( position ).short_description,new HtmlHttpImageGetter( holder.txtMessage2 ) );




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder( context );
                builder.setMessage( "Deleting this Adhoc Income will remove its data from Projection. Do you want to edit instead?" );
                builder.setNegativeButton( "No",(dialog, i) -> {
                    dialog.dismiss();
                    callopendailog(amnsm.get(position).keyid);
                } ).setPositiveButton( "Yes",(dialog, i) -> {
                    dialog.dismiss();
//                    Common.updateadhocincome=amnsm.get(position);
//                    context.startActivity(new Intent(context, AdhocIncomeUpdate.class));

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

//                Common.updatedetaileemployee=amnsm.get(position);
//                //updateemployeedetail.updatedetaileonclick();
//
                Common.updateadhocincome=amnsm.get(position);
                context.startActivity(new Intent(context, AdhocIncomeUpdate.class));
            }
        });



    }

    private void deleteelement(AdhocIncomepojo adhocIncomepojo, int position) {


        User user=new User(context);
        String taxdecuted="";
//        if (adhocIncomepojo.taxreducesourceString.equals("Yes")){
//
//            taxdecuted="1";
//        }else {
//
//            taxdecuted="0";
//
//        }

        String dateincome= DataUtils.formatDatehypen(adhocIncomepojo.Adhoc_Income_Date);

        HashMap<String, Object> room = new HashMap<>();
        room.put("IncomeName", adhocIncomepojo.income_source);
        room.put("IncomeAmount", adhocIncomepojo.income_amount);
        room.put("IstaxDeducted", taxdecuted);
        room.put("TDSAmount", adhocIncomepojo.taxdeductedsource);
        room.put("UserID", user.getUserid());
        room.put("IncomeID",adhocIncomepojo.keyid);
        room.put("image", "");
        room.put("image_type", "");
        room.put("pdfname", "");
        room.put("IncomeDate", dateincome);
        room.put("action", "3");



        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait...");
        ApiServise apiServise= Common.getAPI();
        dialog.show();

        apiServise.adhoc_income(room)
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

    private void opendailog(AdhocIncomepojo modal) {

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
        View v1 = li.inflate(R.layout.activity_adhoc_income_update3, null, false);
        dialog2222.setContentView(v1);


        AppCompatEditText income_source=v1.findViewById( R.id.income_source );

        AppCompatEditText income_amount=v1.findViewById(R.id.income_amount);
        AppCompatEditText taxdeductedsource=v1.findViewById(R.id.taxdeductedsource);

        AppCompatEditText tax_reduce=v1.findViewById(R.id.tax_reduce);

        AppCompatEditText Adhoc_Income_Date=v1.findViewById(R.id.Adhoc_Income_Date);

        ImageView imgProfile=v1.findViewById(R.id.imgProfile);
        AppCompatTextView pdfname=v1.findViewById(R.id.pdfname);

        ImageView downloadimage = v1.findViewById(R.id.imgEditProfile);

        ImageView viewimage = v1.findViewById(R.id.viewimage);

        LinearLayoutCompat lineimage11 = v1.findViewById(R.id.lineimage11);



        income_source.setText(modal.income_source);
        income_amount.setText(modal.income_amount);
        tax_reduce.setText(modal.tax_reduce_amount);

        Adhoc_Income_Date.setText(DataUtils.formatDate(modal.Adhoc_Income_Date));

        if (modal.taxdeductedsource.equals("1"))
            taxdeductedsource.setText("Yes");

        else taxdeductedsource.setText("No");


        if (modal.image_type.equals("image")){

            Glide.with(context)
                    .asBitmap()
                    .load(modal.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .centerCrop()
                    .fitCenter()
                    .dontAnimate()
                    .into(imgProfile);
        }else if (modal.image_type.equals("pdf")){

            pdfname.setText(modal.pdfname);

            imgProfile.setVisibility(View.GONE);

        }


        if (modal.image.equals("")){

            lineimage11.setVisibility(View.GONE);
        }



        downloadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "gafgascbvsc", Toast.LENGTH_SHORT).show();
//                DownloadImageFromPath(modal.image);

                dialog2222.dismiss();
                Intent intent = new Intent(context, DownloadService.class);
                intent.putExtra("url", modal.image);
                intent.putExtra("type", modal.image_type);
                intent.putExtra("receiver", new AdhocIncomeHistory.DownloadReceiver(new Handler(),context));
                context.startService(intent);
            }
        });


        viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url =  modal.image;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });


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

            delete=itemView.findViewById(R.id.delete);
            update=itemView.findViewById(R.id.update);


        }
    }


    private void callopendailog(String keyid) {

        AlertDialog.Builder builder=new AlertDialog.Builder( context );
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
//
//                    FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                            .child("profiles")
//                            .child(userid)
//                            .child("employedetaile")
//                            .child(key)


//                    DatabaseReference mPostReference = FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference()
//                            .child("profiles")
//                            .child(typechild)
//                            .child("employedetaile")
//                            .child(amnsm.get(position).keyid);
//                    mPostReference.removeValue();
//
//                    dialog.dismiss();
//
//                    Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();

            Query applesQuery = ref
                    // .child("profiles").child(typechild)
                    .child("adhoc_income").child(keyid);  //.orderByChild("keyid").equalTo(amnsm.get(position).keyid)

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
