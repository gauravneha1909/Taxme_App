package com.example.gplusin.fragment.addupdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gplusin.activity.Historical_add_update;
import com.example.gplusin.databinding.FragmentAddViewBinding;
import com.example.gplusin.fragment.Holidays;
import com.example.gplusin.fragment.adhoc_income.AdhocIncomeHistory;
import com.example.gplusin.fragment.adhocworkhour.AdhocWorkDetaileHistory;
import com.example.gplusin.fragment.business_expense.Business_expense_History;
import com.example.gplusin.fragment.employment_details.EmployeeDetailsHistory;
import com.example.gplusin.fragment.leave.LeaveDetaileHistory;
import com.example.gplusin.interface111111.Clickemployedetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentAddViewBinding binding;

    Clickemployedetails context1111;

    public AddViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddViewFragment newInstance(String param1, String param2) {
        AddViewFragment fragment = new AddViewFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        context1111=(Clickemployedetails) context;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAddViewBinding.inflate(inflater, container, false);

       // return inflater.inflate(R.layout.fragment_add_view, container, false);

        binding.employmentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  context1111.clickbuton("2");

                startActivity(new Intent(getActivity(), EmployeeDetailsHistory.class));

            }
        });

        binding.bussinessExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Business_expense_History.class));
            }
        });

        binding.viewHolydays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Holidays.class));
            }
        });

        binding.adhocIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdhocIncomeHistory.class));
            }
        });


        binding.extraWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdhocWorkDetaileHistory.class));
            }
        });


        binding.historicalBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Historical_add_update.class));
            }
        });


        binding.leavesdetaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LeaveDetaileHistory.class));
            }
        });

        return binding.getRoot();
    }
}