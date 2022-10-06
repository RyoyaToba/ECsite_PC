package com.example.demo.domain;

public class Rom {
	private Integer id;
	private Integer size;
	private Integer price;

	public Integer getId() {
		return id;
	}

	public Integer getSize() {
		return size;
	}

	public Integer getPrice() {
		return price;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Rom [id=" + id + ", size=" + size + ", price=" + price + "]";
	}
}
