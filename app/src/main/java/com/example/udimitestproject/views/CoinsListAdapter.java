package com.example.udimitestproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.udimitestproject.R;
import com.example.udimitestproject.coinsData.CoinsItem;
import com.example.udimitestproject.databinding.ListOfCoinsItemBinding;

import org.jetbrains.annotations.NotNull;

public class CoinsListAdapter extends PagedListAdapter<CoinsItem, CoinsListAdapterItem> {

    public CoinsListAdapter() {
        super(new DiffUtil.ItemCallback<CoinsItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull CoinsItem oldItem,
                                           @NonNull @NotNull CoinsItem newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull CoinsItem oldItem,
                                              @NonNull @NotNull CoinsItem newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    public void setPagedList(LiveData<PagedList<CoinsItem>> coinsList, LifecycleOwner lifecycleOwner) {
        coinsList.observe(lifecycleOwner, list -> {
            submitList(list);
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
        holder.bind(getItem(position), position);
    }
}
