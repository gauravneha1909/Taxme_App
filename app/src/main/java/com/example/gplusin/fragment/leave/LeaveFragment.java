package com.example.gplusin.fragment.leave;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gplusin.R;
import com.example.gplusin.databinding.FragmentAddDetaileUpdateBinding;
import com.example.gplusin.databinding.FragmentLeaveBinding;
import com.example.gplusin.utils.DatePickerFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentLeaveBinding binding;

    String startdate="",enddate="";

    BottomSheetBehavior sheetBehavior, sheetBehaviorRooms;


    public LeaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaveFragment newInstance(String param1, String param2) {
        LeaveFragment fragment = new LeaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        binding = FragmentLeaveBinding.inflate(inflater, container, false);
       // return inflater.inflate(R.layout.fragment_leave, container, false);



        sheetBehavior = BottomSheetBehavior.from(binding.bottonsheet11.bottomSheet);

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);



        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, 0);
        lastYear.add(Calendar.MONTH, 0);
        lastYear.add(Calendar.DAY_OF_MONTH, 0);
        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);


      //  binding.bottonsheet11.calendarView.deactivateDates(list);
//        bottom_sheet.setOnClickListener(null);
//        bottom_sheet_rooms.setOnClickListener(null);

//        binding.bottonsheet11.calendarView.init(lastYear.getTime(), nextYear.getTime()) //
//                .inMode(CalendarPickerView.SelectionMode.RANGE)
//                .withSelectedDate(new Date())
//// deactivates given dates, non selectable
//                .withDeactivateDates(list);
//// highlight dates in red color, mean they are aleady used.
////                .withHighlightedDates(arrayList)
//// add subtitles to the dates pass a arrayList of SubTitle objects with date and text
////                .withSubTitles(getSubTitles());
//
//        binding.bottonsheet11.calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(Date date) {
//                Log.d("DATE", date.getTime() + "");
//
////                sheetTitle.setText("Select Check-Out Date");
//            }
//
//            @Override
//            public void onDateUnselected(Date date) {
////                sheetTitle.setText("Select Check-In Date");
//
//            }
//        });
//
//
//
//
//        binding.bottonsheet11.calendarView.scrollToDate(new Date());
//
//
//        if (binding.bottonsheet11.calendarView.getSelectedDates().size() > 1) {
//
//
//
//
//        } else {
//            Calendar c = Calendar.getInstance();
//            c.setTime(binding.bottonsheet11.calendarView.getSelectedDates().get(0));
//            c.add(Calendar.DATE, 1);
//
//        }


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
                    }
                });

                newFragment.show(getParentFragmentManager(), "datePicker");



            }
        });



        binding.employeenddateline.setOnClickListener(new View.OnClickListener() {
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
                        binding.txtendDate.setText(date);
                        enddate=date;
                    }
                });

                newFragment.show(getParentFragmentManager(), "datePicker");



            }
        });


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleBottomSheet();
            }
        });


        return binding.getRoot();
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState( BottomSheetBehavior.STATE_EXPANDED );
//            btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState( BottomSheetBehavior.STATE_COLLAPSED );
//            btnBottomSheet.setText("Expand sheet");
        }
    }
}