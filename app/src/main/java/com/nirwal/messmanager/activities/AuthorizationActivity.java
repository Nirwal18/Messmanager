package com.nirwal.messmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.activities.Authorization.AuthMethod;
import com.nirwal.messmanager.databinding.ActivityAuthorizationBinding;
import com.nirwal.messmanager.dialogs.loginDialog.LoginWithEmailPassDialog;
import com.nirwal.messmanager.dialogs.loginDialog.LoginWithPhoneDialog;
import com.nirwal.messmanager.models.User;
import com.nirwal.messmanager.services.ImageLoadTask;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AuthorizationActivity extends AppCompatActivity {
    private static final String TAG = "AuthorizationActivity";
    private WeakReference<MyApp> _app;
    private WeakReference<Activity> _context;

    private TextView _userNameText, _userPhoneText, _userEmailText, _userAlert;

    private FirebaseUser _user;
    private Toolbar _toolbar;
    private ImageLoadTask imageLoadTask;
    private LoginWithPhoneDialog _phoneDialog;
    private LoginWithEmailPassDialog _emailDialog;



    private ActivityAuthorizationBinding _binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityAuthorizationBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

        _app = new WeakReference<>((MyApp)getApplication());
        _context = new WeakReference<>(this);

        initToolBar();

        _userNameText = _binding.textViewAuthorizationUserName;
        _userPhoneText = _binding.textViewAuthorizationUserPhone;
        _userEmailText = _binding.textViewAuthorizationUserEmail;
        _userAlert = _binding.textViewAuthorizationUserAlert;

        // button click init
        _binding.btnAuthActGoogleSignIn.setOnClickListener(onButtonClickListener);
        _binding.btnAuthActEmailSignIn.setOnClickListener(onButtonClickListener);
        _binding.btnAuthActPhoneSignIn.setOnClickListener(onButtonClickListener);
        _binding.btnAuthActRegisterOrSignout.setOnClickListener(onButtonClickListener);

        // setup user info ui first time
        updateUserInfoUi(_app.get().get_firebaseAuth().getCurrentUser());

        // set listener for global user change listener
        _app.get().get_firebaseAuth().addAuthStateListener(firebaseAuth -> {
            _user = firebaseAuth.getCurrentUser();
            updateUserInfoUi(_user);

        });

    }

    private void initToolBar(){
        _toolbar = findViewById(R.id.toolbar);
        _toolbar.setTitle("Authorization");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        _toolbar.setNavigationOnClickListener(view->{
            onBackPressed();
        });

    }

    // button click listener
    private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_auth_act_email_sign_in:{
                    if(_emailDialog==null){
                        _emailDialog = new LoginWithEmailPassDialog(_context.get());
                        _emailDialog.setOnLoginSuccessListener(firebaseUser -> {

                            User user = new User(
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getEmail(),
                                    firebaseUser.getUid(),
                                    firebaseUser.getPhoneNumber(),
                                    firebaseUser.getPhotoUrl()==null? null: firebaseUser.getPhotoUrl().toString(),
                                    firebaseUser.isEmailVerified()
                            );
                            saveFirebaseUserInDB(user);
                        });
                    }
                    _emailDialog.show();
                    break;
                }

                case R.id.btn_auth_act_google_sign_in:{
                    AuthMethod.signInUsingGoogle(_context.get(),AuthMethod.GOOGLE_SIGN_IN_REQUEST_CODE);
                    break;
                }
                case R.id.btn_auth_act_phone_sign_in:{
                    if(_phoneDialog==null){
                        _phoneDialog = new LoginWithPhoneDialog(_context.get());
                        _phoneDialog.setOnLoginSuccessListener(firebaseUser -> {
                            User user = new User(
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getEmail(),
                                    firebaseUser.getUid(),
                                    firebaseUser.getPhoneNumber(),
                                    firebaseUser.getPhotoUrl()==null? null: firebaseUser.getPhotoUrl().toString(),
                                    firebaseUser.isEmailVerified()
                            );
                            saveFirebaseUserInDB(user);
                        });
                    }
                    _phoneDialog.show();
                    break;
                }
                case R.id.btn_auth_act_register_or_signout:{
                    _app.get().get_firebaseAuth().signOut();
                    break;
                }

            }
        }
    };


    private void updateUserInfoUi(FirebaseUser user){
       if(user==null) {
           _userAlert.setText("Please login to use app features...");
           _userNameText.setText("User name");
           _userEmailText.setText("");
           _userPhoneText.setText("");
           if(imageLoadTask!=null && imageLoadTask.getStatus()!= AsyncTask.Status.FINISHED){imageLoadTask.cancel(true);}
            _binding.imageViewAuthorizationUserPhoto.setImageResource(R.drawable.ic_account);

       }
       else {
           _userNameText.setText(user.getDisplayName());
           _userEmailText.setText(user.getEmail());
           _userPhoneText.setText(user.getPhoneNumber());
           _userAlert.setText(user.isEmailVerified()? "Email Verified" : "Please verify your email address.");
           if(user.getPhotoUrl()!=null){
               imageLoadTask = new ImageLoadTask(user.getPhotoUrl().toString(),_binding.imageViewAuthorizationUserPhoto);
               imageLoadTask.execute();
           }
       }
    }

    private void saveFirebaseUserInDB(User user){
        _app.get().getFirebaseFireStoreDB().collection("users")
                .document(user.getUuid())
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AuthorizationActivity.this, "User added successfully",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleGoogleSignInResult(GoogleSignInResult result){
        Log.v(TAG, "Login result: "+result.getStatus().toString());
        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            // you can store user data to SharedPreference
            String idToken = account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            registerUserInFirebaseAuth(credential);
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. "+result);
            Toast.makeText(_context.get(), "Login Unsuccessful "+result, Toast.LENGTH_LONG).show();
        }
    }

    public void registerUserInFirebaseAuth(AuthCredential credential){

        _app.get().get_firebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(_context.get(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){

                            FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                            saveFirebaseUserInDB(new User(
                                    user.getDisplayName(),
                                    user.getEmail(),
                                    user.getUid(),
                                    user.getPhoneNumber(),
                                    user.getPhotoUrl()==null? null: user.getPhotoUrl().toString(),
                                    user.isEmailVerified()
                            ));


                            Snackbar.make(_binding.getRoot(),"Login Successful", BaseTransientBottomBar.LENGTH_LONG)
                                    .setDuration(4000).setBackgroundTint(Color.rgb(0,153,0)).show();
                            //gotoProfile(); do something here
                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());

                            task.getException().printStackTrace();
                            Snackbar.make(_binding.getRoot(),"Login failed", BaseTransientBottomBar.LENGTH_LONG)
                                    .setDuration(4000).setBackgroundTint(Color.rgb(153,0,0)).show();

                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==AuthMethod.GOOGLE_SIGN_IN_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onButtonClickListener=null;
        _binding = null;
    }
}
