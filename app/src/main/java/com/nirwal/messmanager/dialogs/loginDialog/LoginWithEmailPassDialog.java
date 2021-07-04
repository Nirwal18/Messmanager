package com.nirwal.messmanager.dialogs.loginDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nirwal.messmanager.activities.AuthorizationActivity;
import com.nirwal.messmanager.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;



public class LoginWithEmailPassDialog extends AlertDialog {

    private static final String TAG = "LoginDialogWithEmailPas";
    private OnLoginEventListener listener;
    private Button loginBtn, registerBtn, forgotPassBtn;
    private EditText userEditTxt, passEditTxt;
    private CheckBox loginPermanentlyCheckbox;
    private ImageButton imageBtnClose;
    private int dismissButtonVisibility;
    private Context _context;


    public LoginWithEmailPassDialog(@NonNull Context context) {
        super(context);
        this._context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_with_emailpass);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        init();

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_group_join:{
                    String user = userEditTxt.getText().toString();
                    String pass = passEditTxt.getText().toString();
                    if(user.contains(" ")){
                        Snackbar.make(loginBtn.getRootView(),"Password can't be empty.", BaseTransientBottomBar.LENGTH_LONG)
                                .setDuration(2000).setBackgroundTint(Color.rgb(153,0,0)).show();
                        break;
                    }
                    if(pass.isEmpty()){
                        Snackbar.make(loginBtn.getRootView(),"Password can't be empty.", BaseTransientBottomBar.LENGTH_LONG)
                                .setDuration(2000).setBackgroundTint(Color.rgb(153,0,0)).show();
                        break;
                    }

                    login(user,pass);
                    break;
                }
                case R.id.btn_login_register:{break;}
                case R.id.btn_login_forgot_pass:{break;}
                case R.id.imageBtn_login_close:{ dismiss();  break;}
            }
        }
    };

    private void init(){
        loginPermanentlyCheckbox = findViewById(R.id.checkBox_login_loginpermanently);
        userEditTxt = findViewById(R.id.editText_login_email);
        passEditTxt = findViewById(R.id.editText_login_pass);
        loginBtn = findViewById(R.id.btn_login_login);
        registerBtn = findViewById(R.id.btn_login_register);
        forgotPassBtn = findViewById(R.id.btn_login_forgot_pass);
        imageBtnClose = findViewById(R.id.imageBtn_login_close);

        imageBtnClose.setVisibility(this.dismissButtonVisibility);

        loginBtn.setOnClickListener(onClickListener);
        registerBtn.setOnClickListener(onClickListener);
        forgotPassBtn.setOnClickListener(onClickListener);
        imageBtnClose.setOnClickListener(onClickListener);
    }

    private void login(String email, String password){
        loginBtn.setEnabled(false);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(  (Activity) _context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //update listener


                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            notifyLoginResult(task.getResult().getUser());
                            dismiss();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(_context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            Snackbar.make(loginBtn.getRootView(),"Login failed.", BaseTransientBottomBar.LENGTH_LONG)
                                    .setDuration(2000).setBackgroundTint(Color.rgb(153,0,0)).show();

                            loginBtn.setEnabled(true);

                        }

                        // ...
                    }
                });

    }

    private void startAuthorizationWithFragment(){
        Intent intent = new Intent(getContext(), AuthorizationActivity.class);
        getContext().startActivity(intent);

    }


    /***
     *
     * @param visible View.Visible, View.INVISIBLE, View.GONE
     */
    public void setDismissButtonVisibility(int visible){
       this.dismissButtonVisibility = visible;
    }


    private void notifyLoginResult(FirebaseUser user){
        if(listener!=null){
            listener.onSuccess(user);
        }
    }

    public void setOnLoginSuccessListener(OnLoginEventListener listener){
        this.listener = listener;
    }



}
