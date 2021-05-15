package com.example.udimitestproject.data;

import com.google.gson.annotations.SerializedName;

public class Stats{

	@SerializedName("total")
	private int total;

	@SerializedName("totalExchanges")
	private int totalExchanges;

	@SerializedName("totalMarkets")
	private int totalMarkets;

	@SerializedName("totalMarketCap")
	private String totalMarketCap;

	@SerializedName("total24hVolume")
	private String total24hVolume;

	public int getTotal(){
		return total;
	}

	public int getTotalExchanges(){
		return totalExchanges;
	}

	public int getTotalMarkets(){
		return totalMarkets;
	}

	public String getTotalMarketCap(){
		return totalMarketCap;
	}

	public String getTotal24hVolume(){
		return total24hVolume;
	}
}