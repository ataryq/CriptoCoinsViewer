package com.example.udimitestproject.model;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import org.jetbrains.annotations.NotNull;

import static com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper.SortDirection;
import static com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper.SortField;

public class CoinsDataSourceFactory extends DataSource.Factory {
    private SortField mSortOrder;
    private SortDirection mSortDirection;
    private MutableLiveData<CoinsDataSource.RespondCode> mLoadingHandler;
    private CoinsDataSource mDataSource;

    public CoinsDataSourceFactory(SortField sortOrder,
                                  SortDirection sortDirection,
                                  MutableLiveData<CoinsDataSource.RespondCode> loadingHandler) {
        mSortOrder = sortOrder;
        mSortDirection = sortDirection;
        mLoadingHandler = loadingHandler;
        mDataSource = null;
    }

    @NotNull
    @Override
    public DataSource create() {
        mDataSource = new CoinsDataSource(mLoadingHandler);
        mDataSource.setSortOrder(mSortOrder, mSortDirection);
        return mDataSource;
    }

    public void setSortOrder(SortField sortOrder,
                      SortDirection sortDirection) {
        mSortOrder = sortOrder;
        mSortDirection = sortDirection;
    }

    public void refresh() {
        if(mDataSource != null)
            mDataSource.invalidate();
    }
}
