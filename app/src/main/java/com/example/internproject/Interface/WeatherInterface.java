package com.example.internproject.Interface;

import com.example.internproject.WeatherInfoWithCurrentLocation.WeatherData;
import com.example.internproject.WeatherInfoWithCity.WeatherInfo;

import retrofit2.Response;

public interface WeatherInterface {
    void currentLocationWeatherInfo(Response<WeatherData> response);
    void currentCityWeatherInfo(Response<WeatherInfo> response);
}
