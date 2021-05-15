package com.example.udimitestproject.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.udimitestproject.R;
import com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper;
import com.example.udimitestproject.data.CoinsItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CoinsListAdapter mAdapter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoinListViewModel mViewData;
    ConstraintLayout mConstraintLayout;

    //@TODO replace with dagger
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        instance = this;
        mViewData = new ViewModelProvider(this).get(CoinListViewModel.class);

        mConstraintLayout = findViewById(R.id.view_group);

        initRecycleView();
        initRefreshLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void initRecycleView() {
        mRecyclerView = findViewById(R.id.coins_list_recycler_view);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CoinsListAdapter(mViewData.getCoinsList());
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
        mSwipeRefreshLayout.setOnRefreshListener(()-> {
            update();
        });
        mViewData.getCoinsList().observe(this, (List<CoinsItem> coins) -> {
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private void update() {
        if(!CoinrankingApiHelper.isConnected(this)) {
            showNoInternetAlert();
            mSwipeRefreshLayout.setRefreshing(false);
        }
        else {
            mViewData.update();
        }
    }

    void showNoInternetAlert() {
        new AlertDialog.Builder(this)
                .setView(R.layout.no_internet_alert_dialog)
                .setPositiveButton("CLOSE", null)
                .show();
    }
}