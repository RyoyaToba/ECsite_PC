package com.example.demo.domain;

import java.util.Map;

public class OrderItem {
	private Integer id;
	private Integer itemId;
	private Integer orderId;
	private Integer quantity;
	private Object item;
	private Map<String, Integer> orderOptionMap;
	private Integer subTotalWithoutTax;
	private Integer TaxAmount;
	private Integer subTotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}

	public Integer getSubTotal() {
		return subTotal;
	}

	public Integer getSubTotalWithoutTax() {
		return subTotalWithoutTax;
	}

	public void setSubTotalWithoutTax(Integer subTotalWithoutTax) {
		this.subTotalWithoutTax = subTotalWithoutTax;
	}

	public Integer getTaxAmount() {
		return TaxAmount;
	}

	public void setTaxAmount(Integer taxAmount) {
		TaxAmount = taxAmount;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public Map<String, Integer> getOrderOptionMap() {
		return orderOptionMap;
	}

	public void setOrderOptionMap(Map<String, Integer> orderOptionMap) {
		this.orderOptionMap = orderOptionMap;
	}


	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", item=" + item + ", orderOptionMap=" + orderOptionMap + ", subTotalWithoutTax=" + subTotalWithoutTax
				+ ", TaxAmount=" + TaxAmount + ", subTotal=" + subTotal + "]";
	}

}

