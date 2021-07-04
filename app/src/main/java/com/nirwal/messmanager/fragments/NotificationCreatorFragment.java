package com.nirwal.messmanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.notification.APIServices;
import com.nirwal.messmanager.notification.Client;
import com.nirwal.messmanager.notification.Data;
import com.nirwal.messmanager.notification.Sender;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationCreatorFragment extends Fragment {
    private static String TAG = "NotificationCreatorFragment";
    WeakReference<MyApp> _app;

    private RadioGroup _radioGrpNotificationMode;
    private CardView _newNotificationCreateWindow, _predefinedNotificationWindow;
    private Button _sendNewNotification, _sendCustomNotification;
    private EditText _editTitle, _editMsg;

    public NotificationCreatorFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification_creator, container,false);

        _newNotificationCreateWindow = v.findViewById(R.id.cardView_create_new_notification_window);
        _predefinedNotificationWindow = v.findViewById(R.id.cardView_create_custom_notification_window);

        _radioGrpNotificationMode = v.findViewById(R.id.radioGrp_notificationMode);

        _editTitle = v.findViewById(R.id.edit_notification_title);
        _editMsg = v.findViewById(R.id.edit_notification_msg);

        _sendNewNotification = v.findViewById(R.id.btn_send_new_notification);
        _sendCustomNotification = v.findViewById(R.id.btn_send_custom_notification);


        // new notification send event
        _sendNewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification(_editTitle.getText().toString(),_editMsg.getText().toString());
            }
        });

        // custom notification send event
        _sendCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((RadioButton) v.findViewById(R.id.radio_brakfast_rdy)).isChecked()){
                    sendNotification("Breakfast ready","Please come soon.");
                }else if(((RadioButton) v.findViewById(R.id.radio_lunch_rdy)).isChecked()){
                    sendNotification("Lunch ready","Please come soon.");
                }else if(((RadioButton) v.findViewById(R.id.radio_dinner_rdy)).isChecked()) {
                    sendNotification("Dinner ready", "Please come soon.");
                }
            }
        });



        initNotificationEditorMode();

        return v;
    }



    private void initNotificationEditorMode(){

        _newNotificationCreateWindow.setVisibility(View.GONE);
        _predefinedNotificationWindow.setVisibility(View.VISIBLE);

        _radioGrpNotificationMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioBtn_CustomMode:{
                        _newNotificationCreateWindow.setVisibility(View.VISIBLE);
                        _predefinedNotificationWindow.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.radio_PredefinedMode:{
                        _newNotificationCreateWindow.setVisibility(View.GONE);
                        _predefinedNotificationWindow.setVisibility(View.VISIBLE);
                        break;
                    }

                }
            }
        });


    }

    private void sendNotification(String title, String msg){
        Data data = new Data("All users", msg,title, "Akshay", R.drawable.ic_meal_24dp);
        Client.getRetroFit("https://fcm.googleapis.com").create(APIServices.class).sendNotification(new Sender(data,"/topics/alert"))
              .enqueue(new Callback<String>() {
                  @Override
                  public void onResponse(Call<String> call, Response<String> response) {
                      Toast.makeText(getContext(),"Response: "+ response.toString(),Toast.LENGTH_LONG).show();
                  }

                  @Override
                  public void onFailure(Call<String> call, Throwable t) {
                      if(t.getCause()==null){Toast.makeText(getContext(), "Notification sent successfully.",Toast.LENGTH_LONG).show();}
                      else {Toast.makeText(getContext(), "Failure :"+ t.getCause(),Toast.LENGTH_LONG).show();}

                  }
              });
    }



}
