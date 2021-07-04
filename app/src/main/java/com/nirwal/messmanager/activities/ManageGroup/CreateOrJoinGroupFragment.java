package com.nirwal.messmanager.activities.ManageGroup;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateOrJoinGroupFragment extends Fragment {
    public static String TAG =CreateOrJoinGroupFragment.class.getName();
    private WeakReference<MyApp> _app;
    private Button _btnCreate, _btnJoin;
    private EditText _editTextGroupName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_create_or_join_group,container,false);
        _app = new WeakReference<>((MyApp) getActivity().getApplication());

        _btnCreate = v.findViewById(R.id.btn_group_create);
        _btnJoin = v.findViewById(R.id.btn_group_join);
        _editTextGroupName = v.findViewById(R.id.editText_group_name);

        _btnCreate.setOnClickListener(view->{
            if(_editTextGroupName.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Group name can't be empty",Toast.LENGTH_LONG).show();
                return;
            }

            createGroup(_editTextGroupName.getText().toString(),
                    _app.get().get_firebaseAuth().getCurrentUser().getUid());
        });

        return v;
    }

    private void createGroup(String name, String ownerUid){
        FirebaseFirestore db = _app.get().getFirebaseFireStoreDB();
        String guid  = db.collection("groups").document().getId();

        Map<String,Object> group = new HashMap<>();
        group.put("guid",guid);
        group.put("name",name);
        group.put("ownerUid",ownerUid);
    db.collection("groups").document(guid).set(group)
            .addOnSuccessListener(aVoid -> {
                HashMap<String,Object> data = new HashMap<>();
                data.put("groups", Arrays.asList(guid));

                //update group id in user name for further tracking
                db.collection("users").document(ownerUid).update("groups", FieldValue.arrayUnion(guid));
                Log.v(TAG,"Group created sucessfully");
                Snackbar.make(getView(),"Group created successfully",6000)
                        .setBackgroundTint(Color.GREEN)
                        .show();
            });
    }



}
