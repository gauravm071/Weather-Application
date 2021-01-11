package com.example.internproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getRetrofit {
    private static Retrofit retrofit =null;
    public static Retrofit getInstance(String url){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
