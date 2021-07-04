package com.nirwal.messmanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nirwal.messmanager.activities.MainActivity;
import com.nirwal.messmanager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminFragment extends Fragment {
    public AdminFragment(){}

    LinearLayout _fragment_admin_login;
    Button _loginBtn;
    EditText _user, _pass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin, container, false);

        _fragment_admin_login = v.findViewById(R.id.linerLayout_Admin_login_form);
        _user = v.findViewById(R.id.edit_Admin_user);
        _pass = v.findViewById(R.id.edit_Admin_pass);

        if(_user.getText().toString() =="nirwal" && _pass.getText().toString() == "nicky@2124")
        {
            _fragment_admin_login.setVisibility(View.INVISIBLE);
            ((MainActivity)this.getActivity()).setFragment(new ManageUserFragment(), null, false);
        }




        return v;
    }

}
