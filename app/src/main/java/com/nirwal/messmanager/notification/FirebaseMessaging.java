package com.nirwal.messmanager.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nirwal.messmanager.activities.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class FirebaseMessaging extends FirebaseMessagingService {

    private static String TAG ="FirebaseMessaging_Custom";
    private static String TOKEN_NAME ="token";

    // used to get refreshed token for current authorization (not use now)
    @Override
    public void onNewToken(@NonNull String newToken) {
        super.onNewToken(newToken);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // used to save refreshed token for current authorization (not use now)

        Log.v(TAG + " token:", newToken);
       // Log.v(TAG + " token:", refreshedToken);

        // subscribe for notification service
        SubscribeForAlertService.subscribeForTopicAlertServiceOnFCM("alert",newToken);

        if(user!=null) updateToken(newToken,user);

        // UnSubscribe for notification service
        SubscribeForAlertService.unSubscribedFromTopicAlertServiceOnFCM("alert",
                getSharedPreferences("Mess manager",MODE_PRIVATE).getString(TOKEN_NAME,""));

    }

    private void updateToken(String refereshedtoken, FirebaseUser user){
        getSharedPreferences("Mess manager",MODE_PRIVATE).edit().putString("token",refereshedtoken).apply();

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(refereshedtoken);
        dref.child(user.getUid()).setValue(token);

    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.v(TAG, remoteMessage.toString());
        super.onMessageReceived(remoteMessage);


        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            sendOreoAndAboveNotification(remoteMessage);
        }else {
            sendNormalNotification(remoteMessage);
        }

    }

    void sendNormalNotification(RemoteMessage msg){
        String user, title, body, icon;
        user = msg.getData().get("user");
        title = msg.getData().get("title");
        body= msg.getData().get("body");
        icon= msg.getData().get("icon");

        RemoteMessage.Notification notification = msg.getNotification();
        //int i = Integer.parseInt(user.replaceAll("[\\D]", ""));
        int i=1;
        Intent intent = new Intent(this, MainActivity.class); // call MainActivity on recieved
        Bundle bundle = new Bundle();
        bundle.putString("UID",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,i,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentText(body)
                .setContentTitle(title)
                .setSound(defSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int j=0;
        if(i>0){
            j=i;
        }
        notificationManager.notify(j,builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void sendOreoAndAboveNotification(RemoteMessage msg){
        String user, title, body, icon;
        user = msg.getData().get("user");
        title = msg.getData().get("title");
        body= msg.getData().get("body");
        icon= msg.getData().get("icon");

        RemoteMessage.Notification notification = msg.getNotification();
       // int i = Integer.parseInt(user.replaceAll("[\\D]", ""));
        int i =1;
        Intent intent = new Intent(this, MainActivity.class); // call MainActivity on recieved
        Bundle bundle = new Bundle();
        bundle.putString("UID",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,i,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoANdAboveNotification oreoANdAboveNotification = new OreoANdAboveNotification(this);
        Notification.Builder builder = oreoANdAboveNotification.getONotifications(title,body, pendingIntent,defSoundUri,icon);

        int j=0;
        if(i>0){
            j=i;
        }
        oreoANdAboveNotification.getManager().notify(j,builder.build());
    }
}
