package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Cpu;

@Repository
public class CpuRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Cpu> CPU_ROW_MAPPER = (rs, i) -> {

		Cpu cpu = new Cpu();
		cpu.setId(rs.getInt("id"));
		cpu.setName(rs.getString("name"));
		cpu.setPrice(rs.getInt("price"));
		return cpu;
	};

	/**
	 * 全件取得
	 * 
	 * @return
	 */
	public List<Cpu> findAll() {
		String sql = " SELECT id, name, price FROM cpus ";
		SqlParameterSource param = new MapSqlParameterSource();
		return template.query(sql, param, CPU_ROW_MAPPER);
	}

	/**
	 * idによって検索
	 * 
	 * @param id
	 * @return
	 */
	public Cpu findById(Integer id) {
		String sql = "SELECT id, name, price FROM cpus WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, CPU_ROW_MAPPER);
	}
}
