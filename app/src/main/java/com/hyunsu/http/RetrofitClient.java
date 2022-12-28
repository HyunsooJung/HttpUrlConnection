package com.hyunsu.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static RetrofitAPI retrofitAPI;
    private static String url = "https://dl.dropboxusercontent.com";

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitClient getInstance(){
        if( instance ==null ){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static RetrofitAPI getRetrofitApi(){
        return retrofitAPI;
    }

}
