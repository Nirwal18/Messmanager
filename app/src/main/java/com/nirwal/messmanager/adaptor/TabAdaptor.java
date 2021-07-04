package com.nirwal.messmanager.adaptor;

import android.content.Context;

import java.util.ArrayList;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdaptor extends FragmentStatePagerAdapter {
    private Context _context;
    private ArrayList<Fragment> _fragmentList;
    private ArrayList<String> _titleList;

    public TabAdaptor(Context context, FragmentManager fm){
        super(fm);
        this._context = context;
        _fragmentList = new ArrayList<>();
        _titleList = new ArrayList<>();
    }

    public void addFragment(String title, Fragment fragment){
        _titleList.add(title);
        _fragmentList.add(fragment);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return _fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return _fragmentList.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return _titleList.get(position);
    }
}
