package com.example.udimitestproject.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("stats")
	private Stats stats;

	@SerializedName("coins")
	private List<CoinsItem> coins;

	public Stats getStats(){
		return stats;
	}

	public List<CoinsItem> getCoins(){
		return coins;
	}
}