package com.dimitar.chatapp.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    fun signUp(
        @Body request: AuthRequest
    )

    @POST("signin")
    fun signIn(
        @Body request: AuthRequest
    ): TokenResponse

    @GET("authenticate")
    fun authenticate(
        @Header("Authorization") token: String
    )
}