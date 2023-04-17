package com.monstarlab.demo.data.register.remote.api

import com.monstarlab.demo.data.register.remote.dto.RegisterUserResponse
import retrofit2.http.*

interface RegisterApi {
    @POST("fake-register")
    @FormUrlEncoded
    suspend fun mockRegister(
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterUserResponse
}