package com.example.demo.form;

/**
 * 商品をショッピングカートへ追加する際に使用する。
 * 
 * @author kentawashio
 *
 */
public class OrderItemForm {
	private Integer itemId;
	private Integer quantity;
	private Integer colorId;
	private Integer cpuId;
	private Integer ramId;
	private Integer romId;

	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getColorId() {
		return colorId;
	}

	public Integer getCpuId() {
		return cpuId;
	}

	public Integer getRamId() {
		return ramId;
	}

	public Integer getRomId() {
		return romId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}

	public void setRamId(Integer ramId) {
		this.ramId = ramId;
	}

	public void setRomId(Integer romId) {
		this.romId = romId;
	}

	@Override
	public String toString() {
		return "OrderItemForm [itemId=" + itemId + ", quantity=" + quantity + ", colorId=" + colorId + ", cpuId="
				+ cpuId + ", ramId=" + ramId + ", romId=" + romId + "]";
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
