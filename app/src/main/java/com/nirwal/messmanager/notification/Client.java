package com.nirwal.messmanager.notification;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
private static Retrofit retrofit;

    public static Retrofit getRetroFit(String url){
        if(retrofit==null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
