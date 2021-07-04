package com.nirwal.messmanager.notification;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServices {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA6NnUep8:APA91bH5NskEgM64GiKHmFbZLxQXRFJSVAEB7eaYvjHEfsIzCj3P0izvo_sLr96QFcbOFeSNL8YGssLVBvMY1r4pqyiy3jEuKtVQ3ODPKesuZed50DONhxFhQ7nVV2YqUp61trKCN6S1"
    })
    @POST("fcm/send")
    Call<String> sendNotification(@Body Sender body);

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA6NnUep8:APA91bH5NskEgM64GiKHmFbZLxQXRFJSVAEB7eaYvjHEfsIzCj3P0izvo_sLr96QFcbOFeSNL8YGssLVBvMY1r4pqyiy3jEuKtVQ3ODPKesuZed50DONhxFhQ7nVV2YqUp61trKCN6S1"
    })
    @POST("iid/v1:batchAdd") // full address for https://iid.googleapis.com/iid/v1:batchAdd <payload format> = {to:'/topics/<your topics>',registration_token:["a","b"]}
    Call<String> subscribeForFCMTopic(@Body Subscribers registration_token);

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA6NnUep8:APA91bH5NskEgM64GiKHmFbZLxQXRFJSVAEB7eaYvjHEfsIzCj3P0izvo_sLr96QFcbOFeSNL8YGssLVBvMY1r4pqyiy3jEuKtVQ3ODPKesuZed50DONhxFhQ7nVV2YqUp61trKCN6S1"
    })
    @POST("iid/v1:batchRemove")
    Call<String> unSubscribedFromTopic(@Body Subscribers registration_token);
}
