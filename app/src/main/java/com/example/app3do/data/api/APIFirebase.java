package com.example.app3do.data.api;

import com.example.app3do.models.firebase.BodyFirebaseMessage;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIFirebase {
    @POST("/fcm/send")
    Call<BodyFirebaseMessage> sendNotification(@Body RequestBody body);
}
