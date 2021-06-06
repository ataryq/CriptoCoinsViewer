package com.example.udimitestproject.coinrankingApi;

import com.example.udimitestproject.coinsData.ResponseService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinrankingApi {
    @GET("coins")
    Call<ResponseService> getCoins(@Query("offset") int offset,
                                   @Query("limit") int limit,
                                   @Query("orderBy") String orderField,
                                   @Query("orderDirection") String orderDirection);
}
