package com.nirwal.messmanager.activities.Authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.nirwal.messmanager.R;

import java.util.Objects;

public class AuthMethod {

    public static int GOOGLE_SIGN_IN_REQUEST_CODE =3333;
    public static void signInUsingGoogle(Context context , int requestCode){
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context,gso);
        Intent signInIntent =mGoogleSignInClient.getSignInIntent();
        ((Activity)context).startActivityForResult(signInIntent,requestCode); // any code

    }
}
