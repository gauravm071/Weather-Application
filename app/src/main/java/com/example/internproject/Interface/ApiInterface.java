package com.example.internproject.Interface;

import com.example.internproject.WeatherInfoWithCurrentLocation.WeatherData;
import com.example.internproject.WeatherInfoWithCity.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
   String currentLocationBaseUrl="https://api.openweathermap.org/";

   @GET("/data/2.5/onecall")
   Call<WeatherData> getCurrentLocationWeatherData(@Query("lat") String lat, @Query("lon") String lon,
                                                   @Query("units") String unit,
                                                   @Query("appid") String key);

   @GET("/data/2.5/weather/")
   Call<WeatherInfo> getWeatherWithCity(@Query("q") String cityName,@Query("units") String unit,@Query("appid") String key);

}
