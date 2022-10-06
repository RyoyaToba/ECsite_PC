package com.example.demo.domain;

import java.util.Map;

public class NotePc extends Item {
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

	public String getImagePath() {
		return imagePath;
	}

	public Integer getMakerId() {
		return makerId;
	}

	public String getOs() {
		return os;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setMakerId(Integer makerId) {
		this.makerId = makerId;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}



	public Map<String, Object> getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(Map<String, Object> optionMap) {
		this.optionMap = optionMap;
	}

	@Override
	public String toString() {
		return "NotePc [basePrice=" + basePrice + ", imagePath=" + imagePath + ", makerId=" + makerId + ", os=" + os
				+ ", description=" + description + ", optionMap=" + optionMap + ", deleted=" + deleted + ", toString()="
				+ super.toString() + "]";
	}


}
