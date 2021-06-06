package com.example.udimitestproject.views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.udimitestproject.R;
import com.example.udimitestproject.databinding.ActivityMainBinding;

import static com.example.udimitestproject.model.CoinsDataSource.*;

public class MainActivity extends AppCompatActivity {

    CoinsListAdapter mAdapter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoinListViewModel mViewData;
    SortHeaderListCoins mSortHeaderListCoins;
    ActivityMainBinding mBinding;

    //@TODO replace with dagger
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        instance = this;
        mViewData = new ViewModelProvider(this).get(CoinListViewModel.class);
        mViewData.initialize(this);
        initLoadingHandler(mViewData);

        initRecycleView();
        initRefreshLayout();
        mSortHeaderListCoins = new SortHeaderListCoins(this, (sortField, sortDir) -> {
            mViewData.setOrder(sortField, sortDir);
            updateCoinsList();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initLoadingHandler(CoinListViewModel viewData) {
        viewData.getDataLoadingHandler().observe(this, responseCode -> {
            if(responseCode == RespondCode.Loading) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            else if(responseCode == RespondCode.NotLoading) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            else if(responseCode == RespondCode.Failed) {
                mSwipeRefreshLayout.setRefreshing(false);
                showNoInternetAlert();
            }
        });
    }

    private void initRecycleView() {
        mRecyclerView = findViewById(R.id.coins_list_recycler_view);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CoinsListAdapter();
        mAdapter.setPagedList(mViewData.getCoinsList(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    //Animated loading icon at the top
    private void initRefreshLayout() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this::updateCoinsList);
    }

    private void updateCoinsList() {
        mViewData.updateCoinsList();
    }

    void showNoInternetAlert() {
        new AlertDialog.Builder(this)
                .setView(R.layout.no_internet_alert_dialog)
                .setPositiveButton("CLOSE", null)
                .show();
    }
}