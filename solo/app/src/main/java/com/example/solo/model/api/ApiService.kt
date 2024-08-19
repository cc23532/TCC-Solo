package com.example.solo.model.api

import com.example.solo.model.data.LoginRequest
import com.example.solo.model.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
