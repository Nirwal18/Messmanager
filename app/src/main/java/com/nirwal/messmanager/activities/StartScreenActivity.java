package com.nirwal.messmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.constants.ConstantSharedPrefrence;
import com.nirwal.messmanager.dialogs.loginDialog.LoginWithEmailPassDialog;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class StartScreenActivity extends AppCompatActivity {


    private WeakReference<Context> _context;
    //private LoginWithEmailPassDialog loginDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        _context = new WeakReference<>(this);

        //first time launch checkup
        boolean isFirstTimeLaunch = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE)
                .getBoolean(ConstantSharedPrefrence.KEY_IS_FIRST_TIME,true);

        if(isFirstTimeLaunch){
            initFirstTimeStart();
        }else {
            initRegularStart();
        }

    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_container_3,fragment)
                .addToBackStack(null)
                .commit();
    }


    private void initRegularStart(){
        //check auth or launch activity
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(_context.get(), AuthorizationActivity.class));
        }else {
            startActivity(new Intent(_context.get(),MainActivity.class));
        }
    }

    private void initFirstTimeStart(){
        startActivity( new Intent(_context.get(),IntroActivity.class));
    }



}
