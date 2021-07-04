package com.nirwal.messmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TableLayout;


import com.google.android.material.tabs.TabLayout;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.activities.Intro.IntroItem;
import com.nirwal.messmanager.activities.Intro.IntroViewPagerAdaptor;
import com.nirwal.messmanager.constants.ConstantSharedPrefrence;
import com.nirwal.messmanager.databinding.ActivityIntroBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity extends AppCompatActivity {
    private static String  TAG = "IntroActivity";
    private WeakReference<Context> _context;
    private ActivityIntroBinding _binding;
    //ViewPager _viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view initialize
        _binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

        _context = new WeakReference<>(this);

        // slider viewpager init
        initViewPager();

        // next button click and launch authorization activity
        _binding.btnIntroNext.setOnClickListener(v-> nextBtnClick());

    }

    private void initViewPager(){
        ArrayList<IntroItem> items= new ArrayList<>();
        items.add(new IntroItem(R.drawable.food_plate, "Meals","You can organize food and customize daily order."));
        items.add(new IntroItem(R.drawable.shopping_listx,"Shopping list", "Manage shopping list and keep track on day to day requirement"));
        items.add(new IntroItem(R.drawable.notifiy_others,"Notification","Send notification to multiple user about food ready etc..."));


        _binding.viewPagerIntro.setAdapter(new IntroViewPagerAdaptor(this,items));
        _binding.tabLayoutIntro.setupWithViewPager(_binding.viewPagerIntro);
    }

    private void nextBtnClick(){
        int currentTabPosition = _binding.tabLayoutIntro.getSelectedTabPosition();
        int tabCount = _binding.tabLayoutIntro.getTabCount();

        if(currentTabPosition < tabCount-1){
            _binding.viewPagerIntro.setCurrentItem(_binding.tabLayoutIntro.getSelectedTabPosition()+1);
        }

        if(currentTabPosition == tabCount-1){

            getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE)
                    .edit()
                    .putBoolean(ConstantSharedPrefrence.KEY_IS_FIRST_TIME,false)
                    .apply();

            // start authorization activity
            Intent authIntent = new Intent(_context.get(),AuthorizationActivity.class);
            startActivity(authIntent);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        _binding = null;
    }
}
