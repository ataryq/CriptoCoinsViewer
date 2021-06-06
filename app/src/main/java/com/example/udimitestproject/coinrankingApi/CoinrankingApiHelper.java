package com.example.udimitestproject.coinrankingApi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinrankingApiHelper
{
    public enum SortDirection {
        Descending("desc"),
        Ascending("asc");

        private final String text;

        SortDirection(String text) {
            this.text = text;
        }

        public String getOrderDirectionTag() {
            return text;
        }
    }

    public enum SortField {
        MarketCap("marketCap"),
        DayVolume("24hVolume"),
        Price("price");

        private final String text;

        SortField(String text) {
            this.text = text;
        }

        public String getSortFieldTag() {
            return text;
        }
    }

    public static String BaseUrl = "https://api.coinranking.com/v2/";
    public CoinrankingApi mApi;

    public CoinrankingApiHelper() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoinrankingApiHelper.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApi = retrofit.create(CoinrankingApi.class);
    }

    public CoinrankingApi getApi() {
        return mApi;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
