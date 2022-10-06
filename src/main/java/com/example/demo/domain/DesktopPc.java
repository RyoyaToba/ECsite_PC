package com.example.demo.domain;

import java.util.Map;

public class DesktopPc extends Item {

	/** オプション料金 */
	private Integer basePrice;
	private String imagePath;
	private Integer makerId;
	private String os;
	private String description;
	private Map<String, Object> optionMap;
	private Boolean deleted;

	public Integer getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getMakerId() {
		return makerId;
	}

	public void setMakerId(Integer makerId) {
		this.makerId = makerId;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Object> getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(Map<String, Object> optionMap) {
		this.optionMap = optionMap;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "DesktopPc [basePrice=" + basePrice + ", imagePath=" + imagePath + ", makerId=" + makerId + ", os=" + os
				+ ", description=" + description + ", optionMap=" + optionMap + ", deleted=" + deleted + "]";
	}

}
