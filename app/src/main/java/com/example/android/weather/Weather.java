package com.example.android.weather;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Weather {
    @GET("/data/2.5/weather")
    Call<JsonElement> getWeather(@Query("q") String city, @Query("appid") String key);
}
