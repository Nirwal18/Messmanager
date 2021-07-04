package com.nirwal.messmanager.activities;

import android.os.Bundle;

import com.nirwal.messmanager.R;
import com.nirwal.messmanager.activities.AbountAndLicence.AboutFragment;
import com.nirwal.messmanager.activities.AbountAndLicence.PrivacyPolicyFragment;
import com.nirwal.messmanager.activities.Authorization.LoginFragment;
import com.nirwal.messmanager.adaptor.TabAdaptor;
import com.nirwal.messmanager.databinding.ActivityAboutAndLicenceBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutAndLicenceActivity extends AppCompatActivity {


    private TabAdaptor _tabAdaptor;
    private Toolbar _toolbar;

    private ActivityAboutAndLicenceBinding _binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityAboutAndLicenceBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());



        _toolbar = findViewById(R.id.toolbar);
        _toolbar.setTitle("Information");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        _toolbar.setNavigationOnClickListener(view->{
            finish();
        });


        _tabAdaptor = new TabAdaptor(this, getSupportFragmentManager());
        _tabAdaptor.addFragment("About us", new AboutFragment());
        _tabAdaptor.addFragment("Privacy policy",new PrivacyPolicyFragment());
        _tabAdaptor.addFragment("Credit",new LoginFragment());

        _binding.aboutLicViewPager.setAdapter(_tabAdaptor);
        _binding.aboutLicTabLayout.setupWithViewPager(_binding.aboutLicViewPager);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _binding = null;
        _tabAdaptor = null;
    }
}
