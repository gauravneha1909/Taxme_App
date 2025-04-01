package com.example.gplusin.fragment.yeprojection

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gplusin.R
import com.example.gplusin.User
import com.example.gplusin.databinding.FragmentYeProjectionBinding
import com.example.gplusin.pojo.EmployeeDetailsMainOne
import com.example.gplusin.pojo.UserFinancialBalancespojo
import com.example.gplusin.retrofit.ApiServise
import com.example.gplusin.utils.Common
import ir.mahozad.android.PieChart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [YeProjectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YeProjectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var binding: FragmentYeProjectionBinding? = null
    val checkedItem = intArrayOf(-1)
    var user: User? = null
    var dialog: ProgressDialog? = null
    var typechild = ""
    var fcmUsers: List<UserFinancialBalancespojo> = ArrayList()
    var apiServise: ApiServise? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYeProjectionBinding.inflate(inflater, container, false)
        //        return inflater.inflate(R.layout.fragment_ye_projection, container, false);
        user = User(context)
        apiServise = Common.getAPI()
        try {
            binding!!.usename.text = "Hi " + user!!.name + ","
        } catch (e: Exception) {
        }
        dialog = ProgressDialog(context)
        dialog!!.setMessage("Please Wait...")


        val pieChart = binding!!.pieChart
//        pieChart.slices = listOf(
//            PieChart.Slice(0.3f, Color.rgb(120, 181, 0), Color.rgb(149, 224, 0), legend = "Legend A"),
//            PieChart.Slice(0.2f, Color.rgb(204, 168, 0), Color.rgb(249, 228, 0), legend = "Legend B"),
//            PieChart.Slice(0.2f, Color.rgb(0, 162, 216), Color.rgb(31, 199, 255), legend = "Legend C"),
//            PieChart.Slice(0.17f, Color.rgb(255, 4, 4), Color.rgb(255, 72, 86), legend = "Legend D"),
//            PieChart.Slice(0.13f, Color.rgb(160, 165, 170), Color.rgb(175, 180, 185), legend = "Legend E")
//        )
        pieChart.slices = listOf(
            PieChart.Slice(0.3f, Color.parseColor(getString(R.color.BLUE))),
            PieChart.Slice(0.2f, Color.parseColor(getString(R.color.MAGENTA))),
            PieChart.Slice(0.2f, Color.parseColor(getString(R.color.black))),
            PieChart.Slice(0.17f, Color.parseColor(getString(R.color.RED))),
            PieChart.Slice(0.13f, Color.parseColor(getString(R.color.Green)))
        )


        pieChart.gradientType = PieChart.GradientType.RADIAL
        pieChart.legendsIcon = PieChart.DefaultIcons.SQUARE

     //   callemployementmain()

        binding!!.userfinanceavailable.visibility=View.VISIBLE
        binding!!.financeuseridnotfound.visibility = View.GONE


//        if (user.getRegistertype().equals("google")){
//
//            typechild=user.getUserid();
//
//        }else {
//
//            typechild=user.getPhone_number();
//
//        }


//      //  int aaaaa=2/7;
//        int aaaaa=6/7;
//
//        Toast.makeText(getContext(), ""+aaaaa, Toast.LENGTH_SHORT).show();


//
//         if (28 % 7==1){
//
//             int
//
//         }


        //  String fDate11 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(fDate));


        // 2023-05-19

        // 25-May-2023
        //  26-05-2023

        //  String[] data11=paycheckDatespojos.get(i).PaycheckEndDate.split("-");

//        String finaldate = "26-May-2023";
//
//        String fDate22 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(finaldate));
//
//
//        Calendar cal=Calendar.getInstance();
//        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
//        int monthnum=5;
//        cal.set(Calendar.MONTH,monthnum);
//        String month_name = month_date.format(cal.getTime());
//
//        Log.e("dnmndmbdn",""+month_name);
//
//
//        Log.e("dnmndmbdn",fDate22);


        //   dialog.show();


//        DatabaseReference zonesRef = FirebaseDatabase.getInstance("https://sample-86bb2-default-rtdb.firebaseio.com/").getReference().child("UserFinancialBalances");
//
//        Query zonesQuery = zonesRef.orderByChild("UserID").equalTo(typechild);
//        zonesQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                dialog.cancel();
////                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
////                    Log.i("mxnk", zoneSnapshot.child("UserID").getValue(String.class));
////                }
//
//                if(dataSnapshot.exists()){
//                    //bus number exists in Database
//
//                    binding.userfinanceavailable.setVisibility(View.VISIBLE);
//
//
//                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
//
//                    while (dataSnapshots.hasNext()) {
//                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
//                        UserFinancialBalancespojo fcmUser = dataSnapshotChild.getValue(UserFinancialBalancespojo.class);
//                        fcmUsers.add(fcmUser);
//
//                    }
//
//                    // Check your arraylist size and pass to list view like
//                    if(fcmUsers.size()>0)
//                    {
//
//                      Log.e("kdmdnmd","dnmnsdb ");
//
//                    }else
//                    {
//
//                    }
//
//
//                } else {
//                    //bus number doesn't exists.
//                    binding.financeuseridnotfound.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                dialog.cancel();
//                Log.w("mxnk", "onCancelled", databaseError.toException());
//            }
//        });
        binding!!.financialyear.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)

            // set the custom icon to the alert dialog
            // alertDialog.setIcon(R.drawable.loadingpage);

            // title of the alert dialog
            alertDialog.setTitle("Financial Year")

            // list of the items to be displayed to the user in the
            // form of list so that user can select the item from


//                 Gson gson=new Gson();
//
//                 String aaaaaa=gson.toJson(daaaa);
//
//                 String datafinal= aaaaaa.replaceAll("^\\[", "").replaceAll("\\]$", "");
//
//                 Log.e("mnxmnx",datafinal);

            // String[] listItems = new String[] {"Android Development", "Web Development", "Machine Learning"};
            val listItems = arrayOfNulls<String>(fcmUsers.size)
            for (i in fcmUsers.indices) {
                listItems[i] = fcmUsers[i].FinancialYear
            }


            // the function setSingleChoiceItems is the function which
            // builds the alert dialog with the single item selection
            alertDialog.setSingleChoiceItems(
                listItems,
                checkedItem[0]
            ) { dialog: DialogInterface, which: Int ->
                // update the selected item which is selected by the user so that it should be selected
                // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which
                binding!!.financialyear.setText(listItems[0])


                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss()
            }

            // set the negative button if the user is not interested to select or change already selected item
            alertDialog.setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int -> }

            // create and build the AlertDialog instance with the AlertDialog builder instance
            val customAlertDialog = alertDialog.create()

            // show the alert dialog when the button is clicked
            customAlertDialog.show()
        }
        return binding!!.root
    }

    private fun callemployementmain() {
        dialog!!.show()
        val room = HashMap<String, Any>()


        val user = User(context)
        room["EmployerName"] = ""
        room["EmploymentStartDate"] = ""
        room["EmploymentEndDate"] = ""
        room["StandardDailyHours"] = ""
        room["Paymentrateamount"] = 0
        room["Paymentratefrequency"] = ""
        room["IsGSTregistered"] = ""
        room["WitholdingTaxRate"] = 0
        room["UserID"] = user.userid
        room["FirstPaymentDate"] = ""
        room["PaymentFrequency"] = ""
        room["EmploymentID"] = ""
        room["EmploymentRegion"] = ""
        room["ActionType"] = "2"



        apiServise!!.useremploymentmainfromid(room)
            .enqueue(object : Callback<EmployeeDetailsMainOne> {
                override fun onResponse(
                    call: Call<EmployeeDetailsMainOne>,
                    response: Response<EmployeeDetailsMainOne>
                ) {
                    dialog!!.cancel()
                    if (response.isSuccessful) {
                        if (response.body()!!.result.size > 0) {
                            binding!!.userfinanceavailable.visibility=View.VISIBLE
                            binding!!.financeuseridnotfound.visibility = View.GONE
                        } else {
                            binding!!.userfinanceavailable.visibility=View.GONE
                            binding!!.financeuseridnotfound.visibility = View.VISIBLE
                        }
                    } else {
                    }
                }

                override fun onFailure(call: Call<EmployeeDetailsMainOne>, t: Throwable) {
                    dialog!!.cancel()
                    // Snackbar.make(getContext(), t.getMessage(), 5000 ).show();


                }
            })
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YeProjectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): YeProjectionFragment {
            val fragment = YeProjectionFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStop() {
        super.onStop()
        Log.e("checkcall","onStopfragment");
    }

    override fun onPause() {
        super.onPause()
        Log.e("checkcall","onPausefragment");
    }
}