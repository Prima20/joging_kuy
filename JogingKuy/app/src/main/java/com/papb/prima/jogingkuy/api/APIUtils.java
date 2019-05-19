package com.papb.prima.jogingkuy.api;

public class APIUtils {
    private APIUtils(){}

    public static final String API_URL = "http://192.168.1.9/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(API_URL).create(BaseApiService.class);
    }
}
