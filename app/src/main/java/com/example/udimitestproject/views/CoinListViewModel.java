package com.example.udimitestproject.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper;
import com.example.udimitestproject.data.CoinsItem;
import com.example.udimitestproject.data.Data;

import java.util.ArrayList;
import java.util.List;

public class CoinListViewModel extends ViewModel {
    private MutableLiveData<List<CoinsItem>> mCoinsList;
    private CoinrankingApiHelper mApiHelper;

    public CoinListViewModel() {
        mApiHelper = new CoinrankingApiHelper();
        mCoinsList = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<CoinsItem>> getCoinsList() {
        return mCoinsList;
    }

    public void update() {
        mApiHelper.loadCoins(new CoinrankingApiHelper.CoinsApiResult() {

            @Override
            public void loaded(Data data) {
                mCoinsList.postValue(data.getCoins());
            }

            @Override
            public void failed(String message) {}
        });
    }
}
