package com.papb.prima.jogingkuy.model


import retrofit2.Call
import retrofit2.http.GET

interface PostServices {

    @GET("bins/196w72")
    fun getPosts(): Call<UserResponse>
}