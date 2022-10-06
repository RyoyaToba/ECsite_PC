package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Review;
import com.example.demo.domain.Score;

@Repository
public class ReviewRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Review> REVIEW_ROW_MAPPER = (rs, i) -> {

		Review review = new Review();
		review.setId(rs.getInt("id"));
		review.setItemId(rs.getInt("item_id"));
		review.setName(rs.getString("name"));
		review.setScore(rs.getFloat("score"));
		review.setComment(rs.getString("comment"));
		return review;
	};
	
	private static final RowMapper<Score> SCORE_ROW_MAPPER = (rs, i) -> {

		Score score = new Score();
		score.setItemId(rs.getInt("item_id"));
		score.setScoreAvg(rs.getFloat("total_score"));
		score.setReviewCount(rs.getInt("review_count"));
		return score;
	};

	/**
	 * レビュー投稿
	 * 
	 * @param review
	 */
	public void insert(Review review) {
		String sql = "INSERT INTO reviews (item_id, name, score, comment) "
				+ " values(:itemId, :name, :score, :comment)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(review);
		template.update(sql, param);
	}

	/**
	 * レビュー表示
	 * 
	 * @param itemId
	 * @return
	 */
	public List<Review> load(Integer itemId) {
		String sql = "SELECT id, item_id, name, score, comment FROM reviews WHERE item_id = :itemId ORDER BY id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Review> reviewList = template.query(sql, param, REVIEW_ROW_MAPPER);
		System.out.println(reviewList);
		return reviewList;
	}

	public List<Review> loadForScore(Integer itemId, float score) {
		String sql = "SELECT id, item_id, name, score, comment FROM reviews WHERE item_id = :itemId AND score = :score ORDER BY id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId).addValue("score", score);
		List<Review> reviewList = template.query(sql, param, REVIEW_ROW_MAPPER);
		System.out.println(reviewList);
		return reviewList;
	}

	/**
	 * レビューの総数、平均値を商品ごとに取得
	 * 
	 * @return
	 */
	public Score loadReviewScore(Integer itemId) {

		String sql = "SELECT item_id, avg(score) as total_score, count(item_id) as review_count" + " FROM reviews "
				+ " WHERE item_id = :itemId " + " GROUP BY item_id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Score> scoreList = template.query(sql, param, SCORE_ROW_MAPPER);
		if (scoreList.size() == 0) {
			return null;
		} else {
			return scoreList.get(0);
		}
	}
}
