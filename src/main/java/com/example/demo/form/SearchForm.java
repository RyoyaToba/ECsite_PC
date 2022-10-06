package com.example.demo.form;

public class SearchForm {
	private String itemName;
	private Integer categoryId;
	private Integer colorId;
	private Integer monitorSize;

	public String getItemName() {
		return itemName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public Integer getColorId() {
		return colorId;
	}

	public Integer getMonitorSize() {
		return monitorSize;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public void setMonitorSize(Integer monitorSize) {
		this.monitorSize = monitorSize;
	}

	@Override
	public String toString() {
		return "SearchForm [itemName=" + itemName + ", categoryId=" + categoryId + ", colorId=" + colorId
				+ ", monitorSize=" + monitorSize + "]";
	}
}
