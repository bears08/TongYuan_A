package com.tongyuan.domain;

import java.io.Serializable;

public class ShopCar extends ProductBase implements Serializable{
	private int id;
	private String userId;
	private int productNum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
}
