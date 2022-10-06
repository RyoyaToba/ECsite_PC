package com.example.demo.repository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class KeyRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * DBに発行したkeyとメールアドレスと現在の時刻を納入するメソッド
	 * 
	 * @param key
	 * @param email
	 */
	public void insert(String key, String email) {
		String sql = "insert into keys (key,email,time_stamp) values (:key,:email,CURRENT_TIMESTAMP)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("key", key).addValue("email", email);
		template.update(sql, param);
	}

	/**
	 * keyと時刻の条件でメールアドレスを取得するメソッド
	 * 
	 * @param key
	 * @param before24Hour
	 * @return なければnull
	 */
	public String findByKeyAndTime(String key, LocalDateTime before30minutes) {
		System.out.println("before24Hour:" + before30minutes);
		String sql = "select email from keys where key=:key and time_stamp>=:before24Hour";
		SqlParameterSource param = new MapSqlParameterSource().addValue("key", key).addValue("before24Hour",
				before30minutes);
		String email = null;
		try {
			email = template.queryForObject(sql, param, String.class);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			// e.printStackTrace();
			email = null;
		}
		System.out.println(email);
		return email;
	}

}
