package com.papb.prima.jogingkuy.model

import com.google.gson.annotations.SerializedName

class UserResponse{
    @SerializedName("user")
    var user = ArrayList<User>()
}

class User{
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("username")
    var username: String = ""
    @SerializedName("password")
    var password: String = ""
}