package com.hyunsu.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("/s/460oyzrb1p59rlk/data.json")
    Call<RetrofitData> getData();
}
