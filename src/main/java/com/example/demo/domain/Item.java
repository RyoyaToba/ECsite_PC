package com.example.demo.domain;

public class Item {
	private Integer id;
	private String name;
	private Integer price;
	private Integer categoryId;

	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", categoryId=" + categoryId + "]";
	}

}
