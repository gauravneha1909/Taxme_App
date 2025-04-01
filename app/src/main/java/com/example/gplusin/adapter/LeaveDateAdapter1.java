package com.example.gplusin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gplusin.R;
import com.example.gplusin.pojo.LeaveData;

import java.util.ArrayList;
import java.util.List;

public class LeaveDateAdapter1 extends RecyclerView.Adapter<LeaveDateAdapter1.MyViewHolder> {
    Context context;

    View view;
    List<LeaveData> amnsm=new ArrayList<>();

    // Updateemployeedetail updateemployeedetail;



    public LeaveDateAdapter1(Context context, List<LeaveData> amnsm) {
        this.context = context;
        this.amnsm = amnsm;

        // updateemployeedetail =(Updateemployeedetail) context;

    }

    @NonNull
    @Override
    public LeaveDateAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from( context ).inflate( R.layout.leave_dates_item1,parent,false );
        return new LeaveDateAdapter1.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveDateAdapter1.MyViewHolder holder, int position) {

        holder.datename.setText(amnsm.get( position ).getDatename());


        //  holder.txtMessage2.setHtml( notificationOne.data.get( position ).short_description,new HtmlHttpImageGetter( holder.txtMessage2 ) );

        holder.hour_edit.setText(amnsm.get( position ).getHour());




    }


    @Override
    public int getItemCount() {
        return amnsm.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView datename;
        AppCompatTextView hour_edit;

        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            datename=itemView.findViewById( R.id.datename );

            hour_edit=itemView.findViewById(R.id.hour_edit);



        }
    }

}
