package com.nirwal.messmanager.dialogs.loginDialog;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.annotations.NotNull;
import com.nirwal.messmanager.R;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginWithPhoneDialog extends AlertDialog {
    public static String TAG = "LoginWithPhoneDialog";
    private WeakReference<AppCompatActivity> _context;
    private OnLoginEventListener _listener;
    private ConstraintLayout layout1,layout2;
    private EditText phoneEditText, OTPEditText;
    private Button btnSendOTP, btnVerifyOTP;
    private String _verificationId;


    public LoginWithPhoneDialog(@NotNull Context context){
        super(context);
        this._context = new WeakReference<>((AppCompatActivity) context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_with_phone);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        layout1 = findViewById(R.id.login_with_phone_layout_enterNumber);
        layout2 = findViewById(R.id.login_with_phone_layout_submitOTP);
        phoneEditText = findViewById(R.id .editText_login_with_phone_mobileNumber);
        OTPEditText = findViewById(R.id.editText_login_with_phone_OTPNumber);
        btnSendOTP = findViewById(R.id.btn_login_with_phone_sendOTP);

        btnSendOTP.setOnClickListener(v -> {
            String n ="+91"+phoneEditText.getText().toString();
            Log.v(TAG,"r:"+n);
            verifyPhoneNumber(n);
        });

        btnVerifyOTP = findViewById(R.id.btn_login_with_phone_verifyOTP);
        btnVerifyOTP.setOnClickListener(v->{

            verifyOTPAndLogin(OTPEditText.getText().toString());
        });

        findViewById(R.id.btn_login_with_phone_backTOlayout1).setOnClickListener(v->{
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
            btnSendOTP.setEnabled(true);
            btnVerifyOTP.setEnabled(true);
        });

    }



    private void verifyPhoneNumber(String phoneNumber){
        btnSendOTP.setEnabled(false);

        Log.v(TAG,"Clicked on send OTP");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                _context.get(),               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.v(TAG,"Verification complete");
                        Toast.makeText(_context.get(),"Phone verification completed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.v(TAG,"Verification failed");
                        Toast.makeText(_context.get(),"Phone verification failed" + e.toString(), Toast.LENGTH_LONG).show();
                        btnSendOTP.setEnabled(true); // enable button on failure
                    }


                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        Log.v(TAG,"OTP Sent");
                        // hide layout 1 and show layout 2 for entering OTP
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);

                        _verificationId =verificationId;

                    }
                });
    }

    private void verifyOTPAndLogin(String OTP){
        if(OTP.isEmpty()){
            Snackbar.make(layout2.getRootView(),"OTP can't be empty.", BaseTransientBottomBar.LENGTH_LONG)
                    .setDuration(2000).setBackgroundTint(Color.rgb(153,0,0)).show();
            return;
        }
        btnVerifyOTP.setEnabled(false);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(_verificationId,OTP );

        FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(_context.get(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                            if(task.isSuccessful()){

                                notifyLoginResult(task.getResult().getUser()); //notify event

                                Snackbar.make(layout2.getRootView(),"Login Successful", BaseTransientBottomBar.LENGTH_LONG)
                                        .setDuration(2000).setBackgroundTint(Color.rgb(0,153,0)).show();

                                dismiss();

                            }
                            else {
                                Log.w(TAG, "signInWithCredential" + task.getException().getMessage());

                                task.getException().printStackTrace();
                                Snackbar.make(layout2.getRootView(),"Wrong OTP, Login failed", BaseTransientBottomBar.LENGTH_LONG)
                                        .setDuration(2000).setBackgroundTint(Color.rgb(153,0,0)).show();

                                btnVerifyOTP.setEnabled(true);

                            }

                        }
                    });

    }

    private void notifyLoginResult(FirebaseUser user){
        if(this._listener!=null){
            this._listener.onSuccess(user);
        }
    }

    public void setOnLoginSuccessListener(OnLoginEventListener listener){
        this._listener = listener;
    }


}
