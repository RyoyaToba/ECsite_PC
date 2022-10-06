package com.example.demo.domain;

import java.util.Date;
import java.util.List;

public class Order {
	private Integer id;
	private Integer userId;
	private List<OrderItem> orderItemList;
	private Integer totalPriceWithoutTax;
	private Integer totalTaxAmount;
	private Integer totalPrice;
	private Date orderDate;
	private String destinationName;
	private String destinationEmail;
	private String destinationZipcode;
	private String destinationAddress;
	private String destinationTel;
	/** 希望配達日 */
	private Date preferredDeliveryDate;
	private Integer preferredDeliveryTime;
	/** 実際の配達日 */
	private Date deliveryDate;
	private Integer paymentMethod;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getTotalPriceWithoutTax() {
		return totalPriceWithoutTax;
	}

	public Integer getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public Date getPreferredDeliveryDate() {
		return preferredDeliveryDate;
	}

	public Integer getPreferredDeliveryTime() {
		return preferredDeliveryTime;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTotalPriceWithoutTax(Integer totalPriceWithoutTax) {
		this.totalPriceWithoutTax = totalPriceWithoutTax;
	}

	public void setTotalTaxAmount(Integer totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public void setPreferredDeliveryDate(Date preferredDeliveryDate) {
		this.preferredDeliveryDate = preferredDeliveryDate;
	}

	public void setPreferredDeliveryTime(Integer preferredDeliveryTime) {
		this.preferredDeliveryTime = preferredDeliveryTime;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPriceWithoutTax="
				+ totalPriceWithoutTax + ", totalTaxAmount=" + totalTaxAmount + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", preferredDeliveryDate="
				+ preferredDeliveryDate + ", preferredDeliveryTime=" + preferredDeliveryTime + ", deliveryDate="
				+ deliveryDate + ", paymentMethod=" + paymentMethod + ", orderItemList=" + orderItemList + "]";
	}

}
