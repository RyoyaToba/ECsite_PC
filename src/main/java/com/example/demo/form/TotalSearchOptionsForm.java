package com.example.demo.form;

public class TotalSearchOptionsForm {

	private String totalMakerNum;
	private String totalPriceNum;
	private String totalColorNum;
	private String totalMoniterSizeNum;

	public String getTotalMakerNum() {
		return totalMakerNum;
	}

	public void setTotalMakerNum(String totalMakerNum) {
		this.totalMakerNum = totalMakerNum;
	}

	public String getTotalPriceNum() {
		return totalPriceNum;
	}

	public void setTotalPriceNum(String totalPriceNum) {
		this.totalPriceNum = totalPriceNum;
	}

	public String getTotalColorNum() {
		return totalColorNum;
	}

	public void setTotalColorNum(String totalColorNum) {
		this.totalColorNum = totalColorNum;
	}

	public String getTotalMoniterSizeNum() {
		return totalMoniterSizeNum;
	}

	public void setTotalMoniterSizeNum(String totalMoniterSizeNum) {
		this.totalMoniterSizeNum = totalMoniterSizeNum;
	}

	@Override
	public String toString() {
		return "TotalSearchOptionsForm [totalMakerNum=" + totalMakerNum + ", totalPriceNum=" + totalPriceNum
				+ ", totalColorNum=" + totalColorNum + ", totalMoniterSizeNum=" + totalMoniterSizeNum + "]";
	}

}
