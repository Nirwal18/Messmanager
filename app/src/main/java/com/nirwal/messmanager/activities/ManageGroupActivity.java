package com.nirwal.messmanager.activities;

import android.os.Bundle;

import com.nirwal.messmanager.R;
import com.nirwal.messmanager.activities.ManageGroup.ManageGroupFragment;
import com.nirwal.messmanager.fragments.ManageUserFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class ManageGroupActivity extends AppCompatActivity {

    private Toolbar _toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);
        setUpToolbar();

        setFragment(new ManageGroupFragment(this),"Manage group");

    }


    private void setUpToolbar(){
        _toolbar = findViewById(R.id.toolbar);
        _toolbar.setTitle("Authorization");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        _toolbar.setNavigationOnClickListener(view->{
            finish();
        });
    }

    public void setFragment(Fragment fragment, @javax.annotation.Nullable String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.manage_group_framelayout_container,fragment)
                .addToBackStack(tag)
                .commit();

        if(tag!=null){
            _toolbar.setTitle(tag);
        }

    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        }
        super.onBackPressed();
    }

}
