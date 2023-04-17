package com.monstarlab.demo.data.register.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("token") var token: String
)