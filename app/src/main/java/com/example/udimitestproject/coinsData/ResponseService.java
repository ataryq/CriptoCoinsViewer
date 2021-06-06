package com.example.udimitestproject.coinsData;

import com.google.gson.annotations.SerializedName;

public class ResponseService {

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private String status;

	public Data getData(){
		return data;
	}

	public String getStatus(){
		return status;
	}
}