package com.example.gplusin.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.gplusin.R;
import com.example.gplusin.adapter.AdapterHolidays;
import com.example.gplusin.databinding.ActivityAdhocIncomeAddBinding;
import com.example.gplusin.databinding.ActivityHolidaysBinding;
import com.example.gplusin.pojo.HolidaysData;

import java.util.ArrayList;
import java.util.List;

public class Holidays extends AppCompatActivity {
    ActivityHolidaysBinding binding;

    boolean chekdate=false;

    boolean chekdate23=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        binding = ActivityHolidaysBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.changeColor));

        List<HolidaysData> holidaysData=new ArrayList<>();

        HolidaysData holidays=new HolidaysData("New Year's Day","1/3/2023","All","");
        HolidaysData holidays2=new HolidaysData("Day after New Year's Day","2/1/2023","All","");
        HolidaysData holidays3=new HolidaysData("Waitangi Day","6/2/2023","All","");
        HolidaysData holidays4=new HolidaysData("Good Friday","7/4/2023","All","");
        HolidaysData holidays5=new HolidaysData("Easter Monday","10/4/2023","All","");
        HolidaysData holidays6=new HolidaysData("ANZAC Day","25/4/2023","All","");
        HolidaysData holidays7=new HolidaysData("King's Birthday","5/6/2023","All","");
        HolidaysData holidays8=new HolidaysData("Matariki","14/7/2023","All","");
        HolidaysData holidays9=new HolidaysData("Labour Day","23/10/2023","All","");
        HolidaysData holidays10=new HolidaysData("Christmas Day","25/12/2023","All","");
        HolidaysData holidays11=new HolidaysData("Boxing Day","26/12/2023","All","");


        HolidaysData holidays12=new HolidaysData("Anniversary","30/1/2023","Auckland","");
        HolidaysData holidays13=new HolidaysData("Anniversary","13/3/2023","Taranaki","");
        HolidaysData holidays14=new HolidaysData("Anniversary","20/10/2023","Hawke's Bay","");
        HolidaysData holidays15=new HolidaysData("Anniversary","23/1/2023","Wellington","");
        HolidaysData holidays16=new HolidaysData("Anniversary","30/10/2023","Marlborough","");
        HolidaysData holidays17=new HolidaysData("Anniversary","30/1/2023","Nelson","");
        HolidaysData holidays18=new HolidaysData("Anniversary","17/11/2023","Canterbury","");
        HolidaysData holidays19=new HolidaysData("Anniversary","25/9/2023","Canterbury (South)","");
        HolidaysData holidays20=new HolidaysData("Anniversary","4/12/2023","Westland","");
        HolidaysData holidays21=new HolidaysData("Anniversary","20/3/2023","Otago","");
        HolidaysData holidays22=new HolidaysData("Anniversary","11/4/2023","Southland","");


        holidaysData.add(holidays);
        holidaysData.add(holidays2);
        holidaysData.add(holidays3);
        holidaysData.add(holidays4);
        holidaysData.add(holidays5);
        holidaysData.add(holidays6);
        holidaysData.add(holidays7);
        holidaysData.add(holidays8);
        holidaysData.add(holidays9);
        holidaysData.add(holidays10);
        holidaysData.add(holidays11);
        holidaysData.add(holidays12);

        holidaysData.add(holidays13);
        holidaysData.add(holidays14);
        holidaysData.add(holidays15);
        holidaysData.add(holidays16);
        holidaysData.add(holidays17);
        holidaysData.add(holidays18);
        holidaysData.add(holidays19);
        holidaysData.add(holidays20);
        holidaysData.add(holidays21);
        holidaysData.add(holidays22);


        AdapterHolidays adapterGifImage = new AdapterHolidays(holidaysData, Holidays.this);

        binding.recyclerCategory11.setAdapter(adapterGifImage);



        List<HolidaysData> holidaysData4=new ArrayList<>();




        HolidaysData holidays44=new HolidaysData("New Year's Day","1/1/2024","All","");
        HolidaysData holidays24=new HolidaysData("Day after New Year's Day","2/1/2024","All","");
        HolidaysData holidays34=new HolidaysData("Waitangi Day","6/2/2024","All","");
        HolidaysData holidays444=new HolidaysData("Good Friday","29/3/2024","All","");
        HolidaysData holidays54=new HolidaysData("Easter Monday","1/4/2024","All","");
        HolidaysData holidays64=new HolidaysData("ANZAC Day","25/4/2024","All","");
        HolidaysData holidays74=new HolidaysData("King's Birthday","3/6/2024","All","");
        HolidaysData holidays84=new HolidaysData("Matariki","28/6/2024","All","");
        HolidaysData holidays94=new HolidaysData("Labour Day","28/10/2024","All","");
        HolidaysData holidays104=new HolidaysData("Christmas Day","25/12/2024","All","");
        HolidaysData holidays114=new HolidaysData("Boxing Day","26/12/2024","All","");


        HolidaysData holidays134=new HolidaysData("Anniversary","29/1/2024","Auckland","");
        HolidaysData holidays144=new HolidaysData("Anniversary","11/3/2024","Taranaki","");
        HolidaysData holidays154=new HolidaysData("Anniversary","25/10/2024","Hawke's Bay","");
        HolidaysData holidays164=new HolidaysData("Anniversary","22/1/2024","Wellington","");
        HolidaysData holidays174=new HolidaysData("Anniversary","4/11/2024","Marlborough","");
        HolidaysData holidays184=new HolidaysData("Anniversary","29/1/2024","Nelson","");
        HolidaysData holidays194=new HolidaysData("Anniversary","15/11/2024","Canterbury","");
        HolidaysData holidays1204=new HolidaysData("Anniversary","23/9/2024","Canterbury (South)","");
        HolidaysData holidays214=new HolidaysData("Anniversary","2/12/2024","Westland","");
        HolidaysData holidays2224=new HolidaysData("Anniversary","25/3/2024","Otago","");
        HolidaysData holidays2234=new HolidaysData("Anniversary","2/4/2024","Southland","");
        HolidaysData holidays2244=new HolidaysData("Anniversary","2/12/2024","Southland","");


        holidaysData4.add(holidays44);
        holidaysData4.add(holidays24);
        holidaysData4.add(holidays34);
        holidaysData4.add(holidays444);
        holidaysData4.add(holidays54);
        holidaysData4.add(holidays64);
        holidaysData4.add(holidays74);
        holidaysData4.add(holidays84);
        holidaysData4.add(holidays94);
        holidaysData4.add(holidays104);
        holidaysData4.add(holidays114);
        holidaysData4.add(holidays134);

        holidaysData4.add(holidays144);
        holidaysData4.add(holidays154);
        holidaysData4.add(holidays164);
        holidaysData4.add(holidays174);
        holidaysData4.add(holidays184);
        holidaysData4.add(holidays194);
        holidaysData4.add(holidays1204);
        holidaysData4.add(holidays214);
        holidaysData4.add(holidays2224);
        holidaysData4.add(holidays2234);
        holidaysData4.add(holidays2244);






        AdapterHolidays adapterGifImage224 = new AdapterHolidays(holidaysData4, Holidays.this);

        binding.recyclerCategory224.setAdapter(adapterGifImage224);


        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.view2024.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekdate==false){

                    binding.arrowimage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    binding.view2024linelayout.setVisibility(View.VISIBLE);

                    chekdate=true;

                }else if (chekdate==true){

                    binding.arrowimage.setImageResource(R.drawable.ic_next);
                    binding.view2024linelayout.setVisibility(View.GONE);

                    chekdate=false;
                }


            }
        });


        binding.view2023.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekdate23==false){

                    binding.arrowimage3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    binding.view2023linelayout.setVisibility(View.VISIBLE);

                    chekdate23=true;

                }else if (chekdate23==true){

                    binding.arrowimage3.setImageResource(R.drawable.ic_next);
                    binding.view2023linelayout.setVisibility(View.GONE);

                    chekdate23=false;
                }


            }
        });

    }
}