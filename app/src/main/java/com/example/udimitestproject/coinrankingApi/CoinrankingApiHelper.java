package com.example.udimitestproject.coinrankingApi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.udimitestproject.data.Data;
import com.example.udimitestproject.data.ResponseService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinrankingApiHelper implements Callback<ResponseService> {
    public static String BaseUrl = "https://api.coinranking.com/v2/";
    public CoinrankingApi mApi;
    private CoinsApiResult mCallbackLoaded;

    public interface CoinsApiResult {
        void loaded(Data data);
        void failed(String message);
    }

    public void loadCoins(CoinsApiResult callback) {
        mCallbackLoaded = callback;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoinrankingApiHelper.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApi = retrofit.create(CoinrankingApi.class);
        Call<ResponseService> call = mApi.getCoins();

        call.enqueue(this);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    public void onResponse(Call<ResponseService> call, Response<ResponseService> response) {
        if(response.isSuccessful()) {
            mCallbackLoaded.loaded(response.body().getData());
        } else {
            mCallbackLoaded.failed(response.message());
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ResponseService> call, Throwable t) {
        mCallbackLoaded.failed("");
    }
}
