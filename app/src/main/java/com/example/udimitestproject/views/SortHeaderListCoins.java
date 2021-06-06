package com.example.udimitestproject.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.udimitestproject.R;

import static com.example.udimitestproject.coinrankingApi.CoinrankingApiHelper.*;

public class SortHeaderListCoins {
    MainActivity mMainActivity;
    SortOrderChanged mListener;
    SortDirection mSortDirection;
    SortField mSortField;
    TextView mNameLabel;
    TextView mPriceLabel;
    TextView mDayVolumeLabel;
    ImageView mSortIconMarketCap;
    ImageView mSortIconPrice;
    ImageView mSortIconDayVol;
    ImageView []mSortIconArray;

    private static final int DescendingIcon = R.drawable.ic_baseline_arrow_drop_down_24;
    private static final int AscendingIcon = R.drawable.ic_baseline_arrow_drop_up_24;

    public interface SortOrderChanged {
        void sortOrderChanged(SortField sortField, SortDirection sortDirection);
    }

    public SortHeaderListCoins(MainActivity mainActivity, SortOrderChanged listener) {
        this.mMainActivity = mainActivity;
        this.mListener = listener;
        mSortField = SortField.MarketCap;
        mSortDirection = SortDirection.Ascending;

        initViews();
        initClickListeners();
        setFieldSort(SortField.MarketCap);
    }

    void initViews() {
        mNameLabel = mMainActivity.findViewById(R.id.name_label);
        mPriceLabel = mMainActivity.findViewById(R.id.price_label);
        mDayVolumeLabel = mMainActivity.findViewById(R.id.day_volume_label);
        mSortIconMarketCap = mMainActivity.findViewById(R.id.sort_icon_market_cap);
        mSortIconPrice = mMainActivity.findViewById(R.id.sort_icon_price);
        mSortIconDayVol = mMainActivity.findViewById(R.id.sort_icon_dayvol);
        mSortIconArray = new ImageView[]{mSortIconMarketCap, mSortIconPrice, mSortIconDayVol};
    }

    void initClickListeners() {
        mNameLabel.setOnClickListener(v -> setFieldSort(SortField.MarketCap));
        mDayVolumeLabel.setOnClickListener(v -> setFieldSort(SortField.DayVolume));
        mPriceLabel.setOnClickListener(v -> setFieldSort(SortField.Price));
    }

    void setFieldSort(SortField sortField) {
        if(mSortField == sortField) {
            swapSortDirection();
        }
        mSortField = sortField;
        setSortIcon(sortField);
        mListener.sortOrderChanged(mSortField, mSortDirection);
    }

    void swapSortDirection() {
        if(mSortDirection == SortDirection.Descending)
            mSortDirection = SortDirection.Ascending;
        else
            mSortDirection = SortDirection.Descending;
    }

    void setSortIcon(SortField sortField) {
        for(ImageView im: mSortIconArray)
            im.setVisibility(View.INVISIBLE);

        ImageView currentIcon = mSortIconMarketCap;

        if(sortField == SortField.DayVolume)
            currentIcon = mSortIconDayVol;
        if(sortField == SortField.Price)
            currentIcon = mSortIconPrice;

        currentIcon.setVisibility(View.VISIBLE);

        if(mSortDirection == SortDirection.Descending)
            currentIcon.setImageResource(DescendingIcon);
        else
            currentIcon.setImageResource(AscendingIcon);
    }

}
