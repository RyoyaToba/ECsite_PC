package com.example.demo.domain;

public class OrderOption {

	private Integer id;
	private String tableName;
	private Integer idInTheTable;
	private Integer orderItemId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getIdInTheTable() {
		return idInTheTable;
	}

	public void setIdInTheTable(Integer idInTheTable) {
		this.idInTheTable = idInTheTable;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

}
