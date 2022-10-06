package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Rom;

@Repository
public class RomRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Rom> ROM_ROW_MAPPER = (rs, i) -> {
		Rom rom = new Rom();
		rom.setId(rs.getInt("id"));
		rom.setSize(rs.getInt("size"));
		rom.setPrice(rs.getInt("price"));
		return rom;
	};

	/**
	 * 全件取得
	 * 
	 * @return
	 */
	public List<Rom> findAll() {
		String sql = "SELECT id, size, price from roms";
		SqlParameterSource param = new MapSqlParameterSource();
		return template.query(sql, param, ROM_ROW_MAPPER);
	}

	/**
	 * idで検索
	 * 
	 * @param id
	 * @return
	 */
	public Rom findById(Integer id) {
		String sql = "SELECT id, size, price FROM roms WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, ROM_ROW_MAPPER);
	}

}
