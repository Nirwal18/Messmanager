package com.nirwal.messmanager;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nirwal.messmanager.constants.ConstantSharedPrefrence;


public class MyApp extends Application {
    private FirebaseFirestore _db;
    private FirebaseAuth _firebaseAuth;


    @Override
    public void onCreate() {
        super.onCreate();
        _firebaseAuth = FirebaseAuth.getInstance();
         _db = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth get_firebaseAuth() {
        return _firebaseAuth;
    }


    public FirebaseFirestore getFirebaseFireStoreDB(){
        return _db;
    }

    public String getCurrentUser(){
        return getSettings(ConstantSharedPrefrence.KEY_CURRENT_USER);
    }

    public void saveSettings(String key, String value){
        getSharedPreferences(ConstantSharedPrefrence.SETTINGS, MODE_PRIVATE).edit().putString(key,value).apply();
    }

    String getSettings(String key){
        return getSharedPreferences(ConstantSharedPrefrence.SETTINGS, MODE_PRIVATE).getString(key, "");
    }

    public void signOutFromFirebase(){
        _firebaseAuth.signOut();
    }




}
