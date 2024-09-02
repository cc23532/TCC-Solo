package com.example.solo.network;

import com.example.solo.models.SoloUser;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    Call<SoloUser> login(@Body Map<String, String> body);
}
