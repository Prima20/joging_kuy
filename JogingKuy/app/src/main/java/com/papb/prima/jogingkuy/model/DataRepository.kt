package com.papb.prima.jogingkuy.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRepository {

    fun create(): PostServices {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.myjson.com/")
                .build()
        return retrofit.create(PostServices::class.java)
    }
}