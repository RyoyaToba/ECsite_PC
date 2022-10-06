package com.example.demo.domain;

public class OrderOptionNotePc {

	private Integer id;
	private Integer orderItemId;
	private Integer cpuId;
	private Integer ramId;
	private Integer romId;
	private Integer colorId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getCpuId() {
		return cpuId;
	}

	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}

	public Integer getRamId() {
		return ramId;
	}

	public void setRamId(Integer ramId) {
		this.ramId = ramId;
	}

	public Integer getRomId() {
		return romId;
	}

	public void setRomId(Integer romId) {
		this.romId = romId;
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	@Override
	public String toString() {
		return "OrderOptionNotePc [id=" + id + ", orderItemId=" + orderItemId + ", cpuId=" + cpuId + ", ramId=" + ramId
				+ ", romId=" + romId + ", colorId=" + colorId + "]";
	}

}
