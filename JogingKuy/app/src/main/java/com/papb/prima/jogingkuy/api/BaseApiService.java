package com.papb.prima.jogingkuy.api;

import com.papb.prima.jogingkuy.model.EventReadResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {

    //To get all event
    @GET("klxee")
    Call<EventReadResponse> getEvent();
}
