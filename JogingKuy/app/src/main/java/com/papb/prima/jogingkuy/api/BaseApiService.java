package com.papb.prima.jogingkuy.api;

import com.papb.prima.jogingkuy.model.EventReadResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface BaseApiService {

    //To get all event
    @GET("getAll/")
    @FormUrlEncoded
    Call<EventReadResponse> getEvent();
}
