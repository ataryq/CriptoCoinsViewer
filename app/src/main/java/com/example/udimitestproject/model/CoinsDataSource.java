package com.example.udimitestproject.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.udimitestproject.coinrankingApi.CoinrankingApi;
import com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper;
import com.example.udimitestproject.coinsData.CoinsItem;
import com.example.udimitestproject.coinsData.ResponseService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper.*;

public class CoinsDataSource extends PageKeyedDataSource<Integer, CoinsItem> {

    interface OnResultCallback {
        void onResponded(List<CoinsItem> list);
    }

    public enum RespondCode {Loading, NotLoading, Failed};

    private CoinrankingApi mApi;
    private String mFieldOrder;
    private String mOrderDirection;
    private MutableLiveData<RespondCode> mLoadingHandler;

    CoinsDataSource(MutableLiveData<RespondCode> loadingHandler) {
        mApi = new CoinrankingApiHelper().getApi();
        mLoadingHandler = loadingHandler;
        mFieldOrder = SortField.MarketCap.getSortFieldTag();
        mOrderDirection = SortDirection.Ascending.getOrderDirectionTag();
    }

    void setSortOrder(SortField sortOrder,
                      SortDirection sortDirection) {
        mFieldOrder = sortOrder.getSortFieldTag();
        mOrderDirection = sortDirection.getOrderDirectionTag();
    }

    @Override
    public void loadAfter(@NotNull LoadParams<Integer> loadParams,
                          @NotNull LoadCallback<Integer, CoinsItem> loadCallback) {
        mLoadingHandler.postValue(RespondCode.Loading);

        loadData(loadParams.key, loadParams.requestedLoadSize, list -> {
            Integer nextKey = loadParams.key + 1;

            if(list.isEmpty() || list.size() < loadParams.requestedLoadSize) {
                nextKey = null;
            }

            loadCallback.onResult(list, nextKey);
            mLoadingHandler.postValue(RespondCode.NotLoading);
        });
    }

    @Override
    public void loadBefore(@NotNull LoadParams<Integer> loadParams,
                           @NotNull LoadCallback<Integer, CoinsItem> loadCallback) {

    }

    @Override
    public void loadInitial(@NotNull LoadInitialParams<Integer> loadInitialParams,
                            @NotNull LoadInitialCallback<Integer, CoinsItem> loadInitialCallback) {
        loadData(0, loadInitialParams.requestedLoadSize, list -> {
            loadInitialCallback.onResult(list, null, 1);
        });
    }

    private void loadData(int offsetPage, int limit, OnResultCallback callback) {
        int offset = offsetPage * limit;

        Call<ResponseService> call = mApi.getCoins(offset, limit, mFieldOrder, mOrderDirection);
        Log.d("Retrofit", "request url: " + call.request().url());
        call.enqueue(new Callback<ResponseService>() {
            @Override
            public void onResponse(Call<ResponseService> call, Response<ResponseService> response) {
                if(response.isSuccessful()) {
                    List<CoinsItem> list = response.body().getData().getCoins();
                    callback.onResponded(list);
                }
                else {
                    callback.onResponded(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ResponseService> call, Throwable t) {
                mLoadingHandler.postValue(RespondCode.Failed);
            }
        });
    }
}
