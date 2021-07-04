package com.nirwal.messmanager.dialogs.loginDialog;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

// used by dialog to notify event
public interface OnLoginEventListener{
    void onSuccess(FirebaseUser user);
}