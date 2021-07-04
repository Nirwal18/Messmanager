package com.nirwal.messmanager.notification;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubscribeForAlertService {

    private static String TAG = "SubscribeForAlertService";

    private static String url = "https://iid.googleapis.com"; //server address
    private static APIServices _apiServices;


    public static void subscribeForTopicAlertServiceOnFCM( String topicName, String currentToken){

        Log.v(TAG,"Subscription service called");

        if(_apiServices==null){
        _apiServices=  new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(APIServices.class);
        }

        _apiServices.subscribeForFCMTopic(new Subscribers("/topics/"+topicName, new String[]{currentToken})).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v(TAG,"Subscribe Response: "+ response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG,"Failure to subscribe :"+ t.getCause());
            }
        });
    }


    public static void unSubscribedFromTopicAlertServiceOnFCM(String topicName, String token){
        Log.v(TAG,"UnSubscription service called");

        if(_apiServices==null){
            _apiServices=  new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(APIServices.class);
        }

        _apiServices.unSubscribedFromTopic(new Subscribers("/topics/"+topicName, new String[]{token})).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v(TAG,"UnSubscribe Response: "+ response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG,"Failure to UnSubscribe :"+ t.getCause());
            }
        });

    }
}
