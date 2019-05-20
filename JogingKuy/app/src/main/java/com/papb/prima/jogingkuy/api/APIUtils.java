package com.papb.prima.jogingkuy.api;

public class APIUtils {
    private APIUtils(){}

    public static final String API_URL = "https://api.myjson.com/bins/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(API_URL).create(BaseApiService.class);
    }
}