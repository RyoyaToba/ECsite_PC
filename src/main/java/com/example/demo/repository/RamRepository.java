package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Ram;

@Repository
public class RamRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Ram> RAM_ROW_MAPPER = (rs, i) -> {

		Ram ram = new Ram();
		ram.setId(rs.getInt("id"));
		ram.setSize(rs.getInt("size"));
		ram.setPrice(rs.getInt("price"));
		return ram;

	};
	
	/**
	 * 全件取得
	 * 
	 * @return
	 */
	public List<Ram> findAll() {
		String sql = " SELECT id, size, price FROM rams ";
		SqlParameterSource param = new MapSqlParameterSource();
		return template.query(sql, param, RAM_ROW_MAPPER);
	}

	/**
	 * idで検索
	 * 
	 * @param id
	 * @return
	 */
	public Ram findById(Integer id) {
		String sql = "SELECT id, size, price FROM rams WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, RAM_ROW_MAPPER);
	}

}

