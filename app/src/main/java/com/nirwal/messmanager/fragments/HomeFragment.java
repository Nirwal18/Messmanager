package com.nirwal.messmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nirwal.messmanager.activities.MainActivity;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.activities.ManageGroupActivity;
import com.nirwal.messmanager.databinding.FragmentHomeBinding;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private WeakReference<MainActivity> _context;
    private FragmentHomeBinding _binding;
    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        _binding = FragmentHomeBinding.inflate(inflater,container,false);
        _context = new WeakReference<>((MainActivity) getActivity());


        _binding.btnOrderMealFrag.setOnClickListener(view -> {
            _context.get().setFragment(_context.get()._mealOrderFragment, "Order a meal", false);
        });

        _binding.btnManageUsrFrag.setOnClickListener(view -> {
            _context.get().setFragment(_context.get()._manageUserFragment, "Manage user", false);
        });

        _binding.btnOrderDetailsFrag.setOnClickListener(view ->{
            _context.get().setFragment(_context.get()._orderDetailFragment, "Order details", false);
        });

        _binding.btnNotificationfrag.setOnClickListener(view -> {
            _context.get().setFragment(_context.get()._notificationCreatorFragment, "Notification creator", false);

        });

        _binding.btnCreateGroupFrag.setOnClickListener(view ->{
            _context.get().startActivity(new Intent(_context.get(),ManageGroupActivity.class));
        } );

        return _binding.getRoot();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        _binding= null;
    }

}
