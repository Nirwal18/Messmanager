package com.nirwal.messmanager.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.models.Meal;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class MealOrderFragment extends Fragment {
    private static String TAG = "MealOrderFragment";
    private WeakReference<MyApp> _app;

    private String _currentUser="";
   private CheckBox _breakfastCheckbox, _lunchRiceCheckBox, _dinnerRiceCheckBox;
   private EditText _lunchRotiEditText, _dinnerRotiEditText;
   private Button _btnUpdate, _btnClear, _btnNoMeal;

    public MealOrderFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meal_order, container, false);

        _app = new WeakReference<>((MyApp) Objects.requireNonNull(getActivity()).getApplication());

        _breakfastCheckbox = v.findViewById(R.id.checkBox_BreakFastReq);
        _lunchRiceCheckBox = v.findViewById(R.id.checkBox_Lunch_rice);
        _dinnerRiceCheckBox = v.findViewById(R.id.checkBox_Dinner_rice);
        _lunchRotiEditText = v.findViewById(R.id.editText_lunch_roti_count);
        _dinnerRotiEditText = v.findViewById(R.id.editText_dinner_roti_count);

        _btnUpdate = v.findViewById(R.id.btn_meal_update);
        _btnClear = v.findViewById(R.id.btn_meal_clear);
        _btnNoMeal = v.findViewById(R.id.btn_meal_noMeal);

        _currentUser = Objects.requireNonNull(getActivity())
                .getSharedPreferences("messmanagerPref", MODE_PRIVATE)
                .getString("currentUser", "");

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateMealOnDb(new Meal(_currentUser
                        ,
                        _breakfastCheckbox.isChecked(),
                        _lunchRiceCheckBox.isChecked(),
                        _dinnerRiceCheckBox.isChecked(),
                        Integer.parseInt(_lunchRotiEditText.getText().toString().equals("") ? "0" : _lunchRotiEditText.getText().toString()),
                        Integer.parseInt(_dinnerRotiEditText.getText().toString().equals("") ? "0" : _dinnerRotiEditText.getText().toString())
                ));
            }
        });

        _btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { clearUi();}
        });

        _btnNoMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { updateMealOnDb(new Meal(_currentUser,false, false, false, 0, 0)); }
        });


        return v;
    }

    private void clearUi(){
        _breakfastCheckbox.setChecked(false);
        _lunchRiceCheckBox.setChecked(false);
        _dinnerRiceCheckBox.setChecked(false);
        _lunchRotiEditText.setText("0");
        _dinnerRotiEditText.setText("0");
    }

    private void updateMealOnDb(Meal meal){

        final AlertDialog alertDialog = alertDialogInit(getContext(),
                "Sending",
                "Meal data sending on server...",
                false);
        alertDialog.show();



        if(Objects.requireNonNull(_currentUser).isEmpty()) return;


        _app.get().getFirebaseFireStoreDB().collection("Meal").document(_currentUser)
                .set(meal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "Meal added");
                        Toast.makeText(getActivity(), "Meal added ",Toast.LENGTH_LONG)
                                .show();
                        alertDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding User", e);
                        Toast.makeText(getActivity(), "Error adding meal" + e,Toast.LENGTH_LONG)
                                .show();

                        alertDialog.dismiss();
                    }
                });

    }

    private AlertDialog alertDialogInit(Context context, String title, String msg, boolean cancelOnTouch){
        AlertDialog.Builder loadingDialogBuilder = new AlertDialog.Builder(context);
        loadingDialogBuilder.setTitle(title);
        loadingDialogBuilder.setMessage(msg);

        AlertDialog loadingDialog = loadingDialogBuilder.create();
        loadingDialog.setCanceledOnTouchOutside(cancelOnTouch);
        return loadingDialog;
    }



}
