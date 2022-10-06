package com.example.demo.domain;

public class Score {
	private Integer itemId;
	private float scoreAvg;
	private Integer reviewCount;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public float getScoreAvg() {
		return scoreAvg;
	}

	public void setScoreAvg(float scoreAvg) {
		this.scoreAvg = scoreAvg;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	@Override
	public String toString() {
		return "Score [itemId=" + itemId + ", scoreAvg=" + scoreAvg + ", reviewCount=" + reviewCount + "]";
	}
}
