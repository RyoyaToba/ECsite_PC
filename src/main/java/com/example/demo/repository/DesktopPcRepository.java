package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Color;
import com.example.demo.domain.Cpu;
import com.example.demo.domain.NotePc;
import com.example.demo.domain.Ram;
import com.example.demo.domain.Rom;
import com.example.demo.form.SearchForm;

@Repository
public class DesktopPcRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<NotePc> NOTE_PC_ROW_MAPPER = (rs, i) -> {
		NotePc notePc = new NotePc();
		/** Itemクラス（親）のフィールド(price以外) */
		notePc.setId(rs.getInt("id"));
		notePc.setName(rs.getString("name"));
		notePc.setCategoryId(rs.getInt("category_id"));

		/** NotePcクラスのフィールド(optionList以外) */
		notePc.setBasePrice(rs.getInt("base_price"));
		notePc.setImagePath(rs.getString("image_path"));
		notePc.setMakerId(rs.getInt("maker_id"));
		notePc.setOs(rs.getString("os"));
		notePc.setDescription(rs.getString("description"));
		notePc.setDeleted(rs.getBoolean("deleted"));

		/** NotePcクラスのフィールド(optionList) */
		Map<String, Object> optionMap = new HashMap<>();
		notePc.setOptionMap(optionMap);
		/** Color */
		Color color = new Color();
		color.setId(rs.getInt("color_id"));
		color.setName(rs.getString("color_name"));
		optionMap.put("color", color);
		/** Cpu */
		Cpu cpu = new Cpu();
		cpu.setId(rs.getInt("cpu_id"));
		cpu.setName(rs.getString("cpu_name"));
		cpu.setPrice(rs.getInt("cpu_price"));
		optionMap.put("cpu", cpu);
		/** Ram */
		Ram ram = new Ram();
		ram.setId(rs.getInt("ram_id"));
		ram.setSize(rs.getInt("ram_size"));
		ram.setPrice(rs.getInt("ram_price"));
		optionMap.put("ram", ram);
		/** Rom */
		Rom rom = new Rom();
		rom.setId(rs.getInt("rom_id"));
		rom.setSize(rs.getInt("rom_size"));
		rom.setPrice(rs.getInt("rom_price"));
		optionMap.put("rom", rom);

		/** Itemクラス（親）のフィールド(price) */
		Integer price = notePc.getBasePrice() + ((Cpu) (optionMap.get("cpu"))).getPrice()
				+ ((Ram) (optionMap.get("ram"))).getPrice() + ((Rom) (optionMap.get("rom"))).getPrice();
		notePc.setPrice(price);

		return notePc;
	};

	String sql = """
			select it.id as id, it.name as name, it.category_id as category_id,
			base_price, image_path, maker_id, ro.id as rom_id, ro.size as rom_size, ro.price as rom_price,
			ra.id as ram_id, ra.size as ram_size, ra.price as ram_price, co.id as color_id, co.name as color_name,
			os, cp.id as cpu_id, cp.name as cpu_name, cp.price as cpu_price, description, deleted
			from items as it
			LEFT JOIN notepcs as no ON it.id = no.id
			LEFT JOIN roms as ro ON no.rom = ro.id
			LEFT JOIN rams as ra ON no.rom = ra.id
			LEFT JOIN colors as co ON no.color = co.id
			LEFT JOIN makers as ma ON no.maker_id = ma.id
			LEFT JOIN cpus as cp ON no.cpu = cp.id
			WHERE it.category_id = 1
			:sb
			ORDER BY it.id
			""";

	public List<NotePc> filteredSearch(SearchForm form, StringBuilder sb) {
		filter1(form, sb);
		filter2(form, sb);
		System.out.println("sb:" + sb);
		SqlParameterSource param = new MapSqlParameterSource().addValue("sb", sb);
		List<NotePc> list = template.query(sql, param, NOTE_PC_ROW_MAPPER);
		System.out.println("xxx");
		System.out.println(list);
		return list;
	}

	/**
	 * @param form
	 * @param sb
	 */
	public void filter1(SearchForm form, StringBuilder sb) {
		if (form.getItemName() != null) {
			sb.append(" AND name = '%" + form.getItemName() + "%'");
		}
	}

	public void filter2(SearchForm form, StringBuilder sb) {

	}


}
