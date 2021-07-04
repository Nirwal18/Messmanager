package com.nirwal.messmanager.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.databinding.FragmentManageUserBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ManageUserFragment extends Fragment {
    private static String TAG = "ManageUserFragment";

    private MyApp _app;
    private FragmentManageUserBinding _binding;

    public ManageUserFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        _binding = FragmentManageUserBinding.inflate(inflater,container,false);
        _app = (MyApp) Objects.requireNonNull(getActivity()).getApplicationContext();

        _binding.btnAddUser.setOnClickListener(view -> {
            addUserToDB(_binding.editTextUserAdd.getText().toString());
            Log.d(TAG, _binding.editTextUserAdd.getText().toString());
        });

        return _binding.getRoot();
    }

    private void addUserToDB(String userName){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", userName);

        // Add a new document with a generated ID
        _app.getFirebaseFireStoreDB().collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "User added with ID: " + documentReference.getId());
                        Toast.makeText(getActivity(), "User added with ID: " + documentReference.getId(),Toast.LENGTH_LONG)
                                .show();;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding User", e);
                        Toast.makeText(getActivity(), "Error adding User" + e,Toast.LENGTH_LONG)
                                .show();;
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        _binding = null;
    }
}
