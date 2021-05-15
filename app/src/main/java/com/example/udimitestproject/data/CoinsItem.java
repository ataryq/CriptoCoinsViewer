package com.example.udimitestproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoinsItem{

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("marketCap")
	private String marketCap;

	@SerializedName("color")
	private String color;

	@SerializedName("change")
	private String change;

	@SerializedName("btcPrice")
	private String btcPrice;

	@SerializedName("listedAt")
	private int listedAt;

	@SerializedName("uuid")
	private String uuid;

	@SerializedName("sparkline")
	private List<String> sparkline;

	@SerializedName("24hVolume")
	private String jsonMember24hVolume;

	@SerializedName("tier")
	private int tier;

	@SerializedName("coinrankingUrl")
	private String coinrankingUrl;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("lowVolume")
	private boolean lowVolume;

	@SerializedName("rank")
	private int rank;

	@SerializedName("iconUrl")
	private String iconUrl;

	public String getSymbol(){
		return symbol;
	}

	public String getMarketCap(){
		return marketCap;
	}

	public String getColor(){
		return color;
	}

	public String getChange(){
		return change;
	}

	public String getBtcPrice(){
		return btcPrice;
	}

	public int getListedAt(){
		return listedAt;
	}

	public String getUuid(){
		return uuid;
	}

	public List<String> getSparkline(){
		return sparkline;
	}

	public String getJsonMember24hVolume(){
		return jsonMember24hVolume;
	}

	public int getTier(){
		return tier;
	}

	public String getCoinrankingUrl(){
		return coinrankingUrl;
	}

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public boolean isLowVolume(){
		return lowVolume;
	}

	public int getRank(){
		return rank;
	}

	public String getIconUrl(){
		return iconUrl;
	}
}