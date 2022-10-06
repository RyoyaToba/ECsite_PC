package com.example.demo.form;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;

public class OrderForm {
	private String id;
	private String userId;
	private String status;
	private String totalPrice;
	private Date orderDate;
	@NotBlank(message = "名前を入力して下さい")
	private String destinationName;
	@NotBlank(message = "メールアドレスを入力して下さい")
	@Email(message = "メールアドレスの形式が不正です")
	private String destinationEmail;
	@NotBlank(message = "郵便番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力して下さい")
	private String destinationZipcode;
	@NotBlank(message = "住所を入力して下さい")
	private String destinationAddress;
	@NotBlank(message = "電話番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{2,4}-[0-9]{2,4}-[0-9]{4}$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力して下さい")
	private String destinationTel;
	private Date preferredDeliveryDate;
	private Integer preferredDeliveryTime;
	private Date deliveryDate;
	private Integer paymentMethod;
	private User user;
	private List<OrderItem> orderItemList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTell) {
		this.destinationTel = destinationTell;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Date getPreferredDeliveryDate() {
		return preferredDeliveryDate;
	}

	public void setPreferredDeliveryDate(Date preferredDeliveryDate) {
		this.preferredDeliveryDate = preferredDeliveryDate;
	}

	public Integer getPreferredDeliveryTime() {
		return preferredDeliveryTime;
	}

	public void setPreferredDeliveryTime(Integer preferredDeliveryTime) {
		this.preferredDeliveryTime = preferredDeliveryTime;
	}

	@Override
	public String toString() {
		return "OrderForm [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", preferredDeliveryDate="
				+ preferredDeliveryDate + ", preferredDeliveryTime=" + preferredDeliveryTime + ", deliveryDate="
				+ deliveryDate + ", paymentMethod=" + paymentMethod + ", user="
				+ user + ", orderItemList=" + orderItemList + "]";
	}
}
