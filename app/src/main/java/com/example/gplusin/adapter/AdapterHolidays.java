package com.example.gplusin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gplusin.databinding.LoanscheduleItemBinding;
import com.example.gplusin.pojo.HolidaysData;

import java.util.List;

public class AdapterHolidays extends RecyclerView.Adapter<AdapterHolidays.MyViewHolder> {
    Context context;
    List<HolidaysData> hasgtagdata;


    public AdapterHolidays(List<HolidaysData> hasgtagdata, Context context) {
//        this.categories = categories;
        this.hasgtagdata=hasgtagdata;
        this.context=context;


    }



    @NonNull
    @Override
    public AdapterHolidays.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new AdapterHolidays.MyViewHolder( LoanscheduleItemBinding.inflate( LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolidays.MyViewHolder holder, int position) {

        holder.binding.emino.setText(String.valueOf(position+1));
        holder.binding.emidate.setText(hasgtagdata.get(position).provinceName);
      //  holder.binding.emiamount.setText(hasgtagdata.get(position).Observed_date);

        holder.binding.holydaysname.setText(hasgtagdata.get(position).Holiday);
        holder.binding.holidaydate.setText(hasgtagdata.get(position).Observed_date);
        //  holder.binding.Principal.setText(emiAmount);

//        double amount1111= Double.parseDouble(emiAmount)*Double.parseDouble(String.valueOf(position+1));
//        double loanAmount1111=Double.parseDouble(totalamount);
//        double total=loanAmount1111 - amount1111;


        //  holder.binding.Outstanding.setText(String.valueOf(String.format("%.2f",total)));

//        if (hasgtagdata.size()-2==position){
//            lastoustanding=(String.format("%.2f",total));
//
//        }
//
//        if (hasgtagdata.size()-1==position){
//            holder.binding.emiamount.setText(String.valueOf(lastoustanding));
////            holder.binding.Principal.setText(String.valueOf(lastoustanding));
////            holder.binding.Outstanding.setText("0.00");
//        }



    }

    @Override
    public int getItemCount() {
        return hasgtagdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LoanscheduleItemBinding binding;

        public MyViewHolder(@NonNull LoanscheduleItemBinding itemView) {
            super(itemView.getRoot());
            this.binding= itemView;
        }
    }
}
