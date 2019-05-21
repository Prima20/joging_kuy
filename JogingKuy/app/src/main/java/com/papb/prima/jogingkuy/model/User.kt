package com.papb.prima.jogingkuy.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
        var username: String? = "",
        var email: String? = "",
        var password: String? = "",
        var gender: String? = "",
        var age: Int? = 0,
        var height: Int? = 0,
        var weight: Int? = 0,
        var photo: String? = ""
)