package com.example.udimitestproject.coinrankingApi;

import com.example.udimitestproject.data.ResponseService;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinrankingApi {
    @GET("coins")
    Call<ResponseService> getCoins();
}
