package com.nirwal.messmanager.activities.Authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.dialogs.loginDialog.LoginWithEmailPassDialog;
import com.nirwal.messmanager.dialogs.loginDialog.LoginWithPhoneDialog;
import com.nirwal.messmanager.dialogs.loginDialog.OnLoginEventListener;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    private static String TAG = "LoginFragment";
    private WeakReference<MyApp> _app;
    private WeakReference<Activity> _context;

    private Button signInUsingEmailPass, btnSignOut, signInUsingPhone;
    private SignInButton googleSignInButton;

    private LoginWithEmailPassDialog _loginWithEmailDialog;
    private LoginWithPhoneDialog _loginWithPhoneDialog;


    public LoginFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);


        _app= new WeakReference<>((MyApp) Objects.requireNonNull(getActivity()).getApplication()) ;
        _context = new WeakReference<>((Activity) getActivity());

        googleSignInButton = v.findViewById(R.id.btn__dialog_google_signin);
        signInUsingEmailPass = v.findViewById(R.id.btn_signin_using_emailpass);
        btnSignOut = v.findViewById(R.id.btn_sign_out);
        signInUsingPhone = v.findViewById(R.id.btn_singnin_using_phone);

        init();





        return v;
    }


    private void  init(){
      // first time signin status check
        setupBtnUI(FirebaseAuth.getInstance().getCurrentUser());
        //listen for auth change
        _app.get().get_firebaseAuth().addAuthStateListener(firebaseAuth -> {
            setupBtnUI(firebaseAuth.getCurrentUser());
        });
    }


    private void setupBtnUI(FirebaseUser user){
        if(user==null){
            btnSignOut.setVisibility(View.GONE);
            googleSignInButton.setVisibility(View.VISIBLE);
            signInUsingPhone.setVisibility(View.VISIBLE);
            signInUsingEmailPass.setVisibility(View.VISIBLE);
        }
        else {
            btnSignOut.setVisibility(View.VISIBLE);
            googleSignInButton.setVisibility(View.GONE);
            signInUsingPhone.setVisibility(View.GONE);
            signInUsingEmailPass.setVisibility(View.GONE);
        }

    }




    private AlertDialog alertDialogInit(Context context, String title, String msg, boolean cancelOnTouch){
       AlertDialog.Builder loadingDialogBuilder = new AlertDialog.Builder(context);
       loadingDialogBuilder.setTitle(title);
       loadingDialogBuilder.setMessage(msg);

       AlertDialog loadingDialog = loadingDialogBuilder.create();
       loadingDialog.setCanceledOnTouchOutside(cancelOnTouch);
        return loadingDialog;
    }





    private void signOut(){
        _app.get().signOutFromFirebase();
    }

}
