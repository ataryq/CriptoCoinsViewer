package com.example.udimitestproject.views;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.udimitestproject.coinsData.CoinsItem;
import com.example.udimitestproject.model.CoinsDataSource;
import com.example.udimitestproject.model.CoinsDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper.*;

public class CoinListViewModel extends ViewModel {

    private MutableLiveData<PagedList<CoinsItem>> mEventHandler;
    private LiveData<PagedList<CoinsItem>> mCoinsList;
    private LifecycleOwner mLifecycleOwner;
    private MutableLiveData<CoinsDataSource.RespondCode> mLoadingHandler;
    private CoinsDataSourceFactory mDataSourceFactory;

    public CoinListViewModel() {
        super();
        mEventHandler = new MutableLiveData<>();
        mLoadingHandler = new MutableLiveData<>(CoinsDataSource.RespondCode.NotLoading);
    }

    public void initialize(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
        initEventHandler();
    }

    public void setOrder(SortField order, SortDirection direction) {
        mDataSourceFactory.setSortOrder(order, direction);
    }

    public void updateCoinsList() {
        mDataSourceFactory.refresh();
    }

    public LiveData<PagedList<CoinsItem>> getCoinsList() {
        return mEventHandler;
    }

    public LiveData<CoinsDataSource.RespondCode> getDataLoadingHandler() {
        return mLoadingHandler;
    }

    private void initEventHandler() {
        if(mCoinsList != null)
            mCoinsList.removeObservers(mLifecycleOwner);

        mCoinsList = createCoinsPagedListSource();
        mCoinsList.observe(mLifecycleOwner, list -> mEventHandler.postValue(list));
    }

    private LiveData<PagedList<CoinsItem>> createCoinsPagedListSource() {
        mDataSourceFactory = new CoinsDataSourceFactory(
                SortField.MarketCap, SortDirection.Ascending, mLoadingHandler);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(9)
                .setPageSize(9)
                .setPrefetchDistance(4)
                .build();

        Executor executor = Executors.newFixedThreadPool(4);

        return new LivePagedListBuilder<Long, CoinsItem>(mDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();
    }
}
