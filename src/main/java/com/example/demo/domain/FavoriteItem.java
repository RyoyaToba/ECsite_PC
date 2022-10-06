package com.example.demo.domain;

public class FavoriteItem extends Item {

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FavoriteItem [userId=" + userId + "]";
	}

}
