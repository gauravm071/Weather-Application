package com.example.internproject;

import android.util.Log;

import com.example.internproject.Interface.ApiInterface;
import com.example.internproject.Interface.WeatherInterface;
import com.example.internproject.WeatherInfoWithCurrentLocation.WeatherData;
import com.example.internproject.WeatherInfoWithCity.WeatherInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {
    public static void getCurrentLocationWeatherData(String lat, String lon, String unit, WeatherInterface weatherInterface){
        ApiInterface apiInterface=getRetrofit.getInstance(ApiInterface.currentLocationBaseUrl).create(ApiInterface.class);
        Call<WeatherData> call= apiInterface.getCurrentLocationWeatherData(lat,lon,unit, sfName.apiKey);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                weatherInterface.currentLocationWeatherInfo(response);
                Log.v("Res",response.toString());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.v("FAILURE",t.getMessage());
            }
        });
    }

    public static void getWeatherWithCity(String city,String unit, WeatherInterface weatherInterface){
        ApiInterface apiInterface=getRetrofit.getInstance(ApiInterface.currentLocationBaseUrl).create(ApiInterface.class);
        Call<WeatherInfo> call= apiInterface.getWeatherWithCity(city,unit, sfName.apiKey);
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                weatherInterface.currentCityWeatherInfo(response);
                Log.v("RES",response.toString());
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Log.v("FAILURE",t.getMessage());
            }
        });
    }


}
