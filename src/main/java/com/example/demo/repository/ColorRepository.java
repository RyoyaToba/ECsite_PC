package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Color;

@Repository
public class ColorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Color> COLOR_ROW_MAPPER = (rs, i) -> {

		Color color = new Color();
		color.setId(rs.getInt("id"));
		color.setName(rs.getString("name"));
		return color;

	};

	/**
	 * @return Color全件取得
	 */
	public List<Color> findAll() {
		String sql = " SELECT id, name FROM colors ";
		SqlParameterSource param = new MapSqlParameterSource();
		return template.query(sql, param, COLOR_ROW_MAPPER);
	}

	/**
	 * idで検索
	 * 
	 * @param id
	 * @return
	 */
	public Color findById(Integer id) {
		String sql = "SELECT id, name FROM colors WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Color> list = template.query(sql, param, COLOR_ROW_MAPPER);
		return list.get(0);
	}

}
