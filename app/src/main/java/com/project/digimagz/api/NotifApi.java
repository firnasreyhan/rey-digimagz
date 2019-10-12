package com.project.digimagz.api;

import com.project.digimagz.model.NotifValue;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NotifApi {
    @FormUrlEncoded
    @POST("/firebase_notif/register")
    Call<NotifValue> sendToken(@Field("token") String token);

}
