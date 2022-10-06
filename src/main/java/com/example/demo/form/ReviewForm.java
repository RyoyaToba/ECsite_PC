package com.example.demo.form;

public class ReviewForm {

	private String itemId;
	private String name;
	private String score;
	private String comment;


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ReviewForm [itemId=" + itemId + ", name=" + name + ", score=" + score + ", comment="
				+ comment + "]";
	}

}
