package com.nirwal.messmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.adaptor.OrderListAdaptor;
import com.nirwal.messmanager.models.Meal;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailFragment extends Fragment {
    private static String TAG = "OrderDetailFragment";
    private WeakReference<MyApp> _app;
    private TextView _breakfastCountTxt, _riceOnLunchCountTxt,_riceOnDinnerCountTxt, _rotiOnLunchCountTxt, _rotiOnDinnerCount;
    private int breakfastCount, lunchRiceCount, dinnerRiceCount, lunchRotiCount, dinnerRotiCount;
    private RecyclerView _orderListView;
    private WeakReference<Context> _context;
    private ArrayList<Meal> _list;


    public OrderDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_details,container,false);

        init(v); //object initialize
        updatePage();

        return v;
    }


    void init(View v){
        _app= new WeakReference<>((MyApp) Objects.requireNonNull(getActivity()).getApplication());
        _context = new WeakReference<>(getContext());
        _list = new ArrayList<>();
        _breakfastCountTxt = v.findViewById(R.id.text_breakfastCount);
        _riceOnLunchCountTxt = v.findViewById(R.id.text_rice_on_lunch_count);
        _riceOnDinnerCountTxt = v.findViewById(R.id.text_rice_on_dinner_count);
        _rotiOnLunchCountTxt = v.findViewById(R.id.text_lunch_roti_count);
        _rotiOnDinnerCount = v.findViewById(R.id.text_dinner_roti_count);
        _orderListView = v.findViewById(R.id.recicyler_order_list_container);
        _orderListView.setLayoutManager(new LinearLayoutManager(_context.get()));
        _orderListView.setAdapter(new OrderListAdaptor(_list,_context.get())); //list adaptor
    }




    void updatePage(){

         breakfastCount = 0;
         lunchRiceCount = 0;
         dinnerRiceCount = 0;
         lunchRotiCount = 0;
         dinnerRotiCount = 0;


        _app.get().getFirebaseFireStoreDB().collection("Meal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                    String requestorName = document.getString("requestorName");
                                    boolean breakfastReq = document.getBoolean("breakFastRequired");
                                    boolean lunchRiceReq = document.getBoolean("lunchRiceRequired");
                                    boolean dinnerRiceReq = document.getBoolean("dinnerRiceRequired");
                                    int lunchRoti = document.getLong("lunchRotiCount").intValue();
                                    int dinnerRoti = document.getLong("dinnerRotiCount").intValue();

                                    _list.add(new Meal(requestorName, breakfastReq, lunchRiceReq, dinnerRiceReq, lunchRoti, dinnerRoti));

                                    if(breakfastReq) breakfastCount++;

                                    if(lunchRiceReq) lunchRiceCount++;

                                    if(dinnerRiceReq) dinnerRiceCount++;

                                    lunchRotiCount+=lunchRoti;

                                    dinnerRotiCount+=dinnerRoti;


                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            //fetchUserAlertDialog.dismiss();
                            updateUI();

                        } else {
                            Log.w(TAG, "Error getting Users list.", task.getException());
                            //fetchUserAlertDialog.dismiss();
                        }
                    }
                });
    }



    private void updateUI(){

        Log.d(TAG,breakfastCount + ":" + lunchRiceCount + ":" +dinnerRiceCount + ":" +lunchRotiCount+ ":" +dinnerRotiCount);


        _breakfastCountTxt.setText(String.valueOf(breakfastCount));
        _riceOnLunchCountTxt.setText(String.valueOf(lunchRiceCount));
        _riceOnDinnerCountTxt.setText(String.valueOf(dinnerRiceCount));
        _rotiOnLunchCountTxt.setText(String.valueOf(lunchRotiCount));
        _rotiOnDinnerCount.setText(String.valueOf(dinnerRotiCount));
       Objects.requireNonNull( _orderListView.getAdapter()).notifyDataSetChanged();

    }




}
