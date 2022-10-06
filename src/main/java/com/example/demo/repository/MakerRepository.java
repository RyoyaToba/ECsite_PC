package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Maker;

@Repository
public class MakerRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Maker> MAKERS_ROW_MAPPER = (rs, i) -> {
		Maker maker = new Maker();
		maker.setId(rs.getInt("id"));
		maker.setName(rs.getString("name"));
		return maker;
	};

	public List<Maker> findAll() {
		String sql = " SELECT id, name FROM makers";
		return template.query(sql, MAKERS_ROW_MAPPER);
	}
}
