package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.domain.Item;
import com.example.demo.domain.Review;
import com.example.demo.domain.Score;
import com.example.demo.form.ReviewForm;
import com.example.demo.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ItemServise itemService;

	@ModelAttribute
	private ReviewForm setUpReviewForm() {
		return new ReviewForm();
	}

	/**
	 * レビュー投稿
	 * 
	 * @param review
	 */
	public void insert(Review review) {
		reviewRepository.insert(review);
	}

	/**
	 * レビュー検索
	 * 
	 * @param itemId
	 * @return
	 */
	public List<Review> load(Integer itemId) {
		return reviewRepository.load(itemId);
	}

	/**
	 * Scoreで絞り込んで検索する
	 * 
	 * @param itemId
	 * @param score
	 * @return
	 */
	public List<Review> loadForScore(Integer itemId, float score) {
		return reviewRepository.loadForScore(itemId, score);
	}

	/**
	 * レビュー投稿
	 * 
	 * @param reviewForm
	 * @return
	 */
	public Review makeReview(ReviewForm reviewForm) {
		Review review = new Review();
		BeanUtils.copyProperties(reviewForm, review);
		review.setItemId(Integer.parseInt(reviewForm.getItemId()));
		review.setScore(Float.parseFloat(reviewForm.getScore()));
		return review;
	}

	/*
	 * レビュー全件の総数、平均値を商品ごとに取得
	 */
	public Score loadReviewScore(Integer itemId) {
		Score score = reviewRepository.loadReviewScore(itemId);
		if (score == null) {
			return this.createEmptyScore(itemId);
		}
		return score;
	}

	public Score createEmptyScore(Integer itemId) {
		Score score = new Score();
		score.setItemId(itemId);
		score.setReviewCount(0);
		score.setScoreAvg(0);
		return score;
	}

	public Map<Integer, Score> showScoreForFindAll(Integer categoryId) {
		Map<Integer, Score> map = new HashMap<>();
		List<Object> itemList = new ArrayList<>();
		switch (categoryId) {
		case 1:
			itemList = itemService.findAll();
			break;
		case 2:
			itemList = itemService.findAllDesktopPc();
		}
		for (int i = 0; i < itemList.size(); i++) {
			Score score = this.loadReviewScore(((Item) itemList.get(i)).getId());
			map.put(((Item) itemList.get(i)).getId(), score);
		}
		return map;
	}

	public Map<Integer, Float> scoreCulc(Integer itemId) {
		Map<Integer, Float> reviewScoreMap = new HashMap<>();
		List<Review> reviewList = this.load(itemId);
		Integer reviewCount = reviewList.size();
		Integer score1 = 0;
		Integer score2 = 0;
		Integer score3 = 0;
		Integer score4 = 0;
		Integer score5 = 0;
		for (int i = 0; i < reviewCount; i++) {
			Review review = reviewList.get(i);
			switch (((int) review.getScore())) {
			case 1:
				score1++;
				break;
			case 2:
				score2++;
				break;
			case 3:
				score3++;
				break;
			case 4:
				score4++;
				break;
			case 5:
				score5++;
				break;
			}
		}

		float score1Parcent = ((float) score1 / reviewCount) * 100;
		float score2Parcent = ((float) score2 / reviewCount) * 100;
		float score3Parcent = ((float) score3 / reviewCount) * 100;
		float score4Parcent = ((float) score4 / reviewCount) * 100;
		float score5Parcent = ((float) score5 / reviewCount) * 100;

		reviewScoreMap.put(1, score1Parcent);
		reviewScoreMap.put(2, score2Parcent);
		reviewScoreMap.put(3, score3Parcent);
		reviewScoreMap.put(4, score4Parcent);
		reviewScoreMap.put(5, score5Parcent);

		return reviewScoreMap;
	}

	public List<Review> reviewSearch(Integer itemId, float score) {
		return this.loadForScore(itemId, score);
	}
}
