package com.nirwal.messmanager.activities.ManageGroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.activities.ManageGroupActivity;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ManageGroupFragment extends Fragment {
    WeakReference<Context>  _context;
    FloatingActionButton addGroupFab;

    public ManageGroupFragment(Context context) {
        this._context = new WeakReference<>(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_manage_group,container,false);
        addGroupFab = v.findViewById(R.id.fab_manage_group_add);
        addGroupFab.setOnClickListener(view->{
            ((ManageGroupActivity)getActivity()).setFragment(new CreateOrJoinGroupFragment(),"Create or join group");
        });

        return v;
    }
}
