package com.monstarlab.demo.data.register.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterErrorResponse(
    @SerializedName("error") var error: Error
) {
    data class ErrorResponse(
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String
    )
}