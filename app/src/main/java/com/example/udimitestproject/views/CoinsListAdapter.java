package com.example.udimitestproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udimitestproject.R;
import com.example.udimitestproject.data.CoinsItem;
import com.example.udimitestproject.databinding.ListOfCoinsItemBinding;

import java.util.ArrayList;
import java.util.List;

public class CoinsListAdapter extends RecyclerView.Adapter<CoinsListAdapterItem> {

    private LiveData<List<CoinsItem>> mCoinsList;

    public CoinsListAdapter(LiveData<List<CoinsItem>> coinsList) {
        mCoinsList = coinsList;
        mCoinsList.observeForever((List<CoinsItem> coins) -> {
            notifyDataSetChanged();
        });
    }

    @Override
    public CoinsListAdapterItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        ListOfCoinsItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.list_of_coins_item, parent, false);

        return new CoinsListAdapterItem(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsListAdapterItem holder, int position) {
        holder.bind(mCoinsList.getValue().get(position), position);
    }

    @Override
    public int getItemCount() {
        return mCoinsList.getValue().size();
    }
}
