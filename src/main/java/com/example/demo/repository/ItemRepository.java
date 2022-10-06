package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Color;
import com.example.demo.domain.Cpu;
import com.example.demo.domain.DesktopPc;
import com.example.demo.domain.Item;
import com.example.demo.domain.NotePc;
import com.example.demo.domain.Ram;
import com.example.demo.domain.Rom;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private static RomRepository romRepository;

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCategoryId(rs.getInt("category_id"));
		return item;
	};

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

	private static final RowMapper<DesktopPc> DESKTOP_PC_ROW_MAPPER = (rs, i) -> {
		DesktopPc desktopPc = new DesktopPc();
		/** Itemクラス（親）のフィールド(price以外) */
		desktopPc.setId(rs.getInt("id"));
		desktopPc.setName(rs.getString("name"));
		desktopPc.setCategoryId(rs.getInt("category_id"));

		/** NotePcクラスのフィールド(optionList以外) */
		desktopPc.setBasePrice(rs.getInt("base_price"));
		desktopPc.setImagePath(rs.getString("image_path"));
		desktopPc.setMakerId(rs.getInt("maker_id"));
		desktopPc.setOs(rs.getString("os"));
		desktopPc.setDescription(rs.getString("description"));
		desktopPc.setDeleted(rs.getBoolean("deleted"));

		/** NotePcクラスのフィールド(optionList) */
		Map<String, Object> optionMap = new HashMap<>();
		desktopPc.setOptionMap(optionMap);
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
		Integer price = desktopPc.getBasePrice() + ((Cpu) (optionMap.get("cpu"))).getPrice()
				+ ((Ram) (optionMap.get("ram"))).getPrice() + ((Rom) (optionMap.get("rom"))).getPrice();
		desktopPc.setPrice(price);

		return desktopPc;
	};

	/**
	 * Item全件検索メソッド
	 * 
	 * @return 全数検索結果
	 */
	public List<Object> findAll() {
		List<Object> itemList = new ArrayList<>();// SQLを分けて、カテゴリごとにリストに格納した場合、価格の高い順などの並び替えは表示できない。
		// カテゴリー１
		List<NotePc> notePcList = new ArrayList<>();
		String sql = "select it.id as id, it.name as name, it.category_id as category_id, \n"
				+ "base_price, image_path, maker_id, \n"
				+ "ro.id as rom_id, ro.size as rom_size, ro.price as rom_price, \n"
				+ "ra.id as ram_id, ra.size as ram_size, ra.price as ram_price, \n"
				+ "co.id as color_id, co.name as color_name, \n"
				+ "os, cp.id as cpu_id, cp.name as cpu_name, cp.price as cpu_price, description, deleted \n"
				+ "from items as it \n"
				+ "LEFT JOIN notepcs as no ON it.id = no.id \n" + "LEFT JOIN roms as ro ON no.rom = ro.id \n"
				+ "LEFT JOIN rams as ra ON no.rom = ra.id \n" + "LEFT JOIN colors as co ON no.color = co.id \n"
				+ "LEFT JOIN makers as ma ON no.maker_id = ma.id \n" + "LEFT JOIN cpus as cp ON no.cpu = cp.id \n"
				+ "WHERE it.category_id = 1\n" + "ORDER BY it.id;";
		notePcList = template.query(sql, NOTE_PC_ROW_MAPPER);
		for (NotePc notePc : notePcList) {
			itemList.add(notePc);
		}
		return itemList;
	}

	public List<Object> findAllDesktopPc() {
		List<Object> itemList = new ArrayList<>();// SQLを分けて、カテゴリごとにリストに格納した場合、価格の高い順などの並び替えは表示できない。
		// カテゴリー2
		List<DesktopPc> desktopPcList = new ArrayList<>();
		String sql = "select it.id as id, it.name as name, it.category_id as category_id, \n"
				+ "base_price, image_path, maker_id, \n"
				+ "ro.id as rom_id, ro.size as rom_size, ro.price as rom_price, \n"
				+ "ra.id as ram_id, ra.size as ram_size, ra.price as ram_price, \n"
				+ "co.id as color_id, co.name as color_name, \n"
				+ "os, cp.id as cpu_id, cp.name as cpu_name, cp.price as cpu_price, description, deleted \n"
				+ "from items as it \n" + "LEFT JOIN desktoppcs as no ON it.id = no.id \n"
				+ "LEFT JOIN roms as ro ON no.rom = ro.id \n" + "LEFT JOIN rams as ra ON no.rom = ra.id \n"
				+ "LEFT JOIN colors as co ON no.color = co.id \n" + "LEFT JOIN makers as ma ON no.maker_id = ma.id \n"
				+ "LEFT JOIN cpus as cp ON no.cpu = cp.id \n" + "WHERE it.category_id = 2\n" + "ORDER BY it.id;";
		desktopPcList = template.query(sql, DESKTOP_PC_ROW_MAPPER);
		for (DesktopPc desktopPc : desktopPcList) {
			itemList.add(desktopPc);
		}
		return itemList;
	}

	/**
	 * idによって商品を検索するメソッド
	 * 
	 * @param id
	 * @return id検索結果
	 */
	public Object load(Integer id) {

		String sql = "select category_id from items where id = ?";
		Integer categoryId = jdbcTemplate.queryForObject(sql, Integer.class, id);

		switch (categoryId) {
		// カテゴリー１
		case 1:
			List<NotePc> notePcList = new ArrayList();
			sql = "select it.id as id, it.name as name, it.category_id as category_id, \n"
					+ "base_price, image_path, maker_id, \n"
					+ "ro.id as rom_id, ro.size as rom_size, ro.price as rom_price, \n"
					+ "ra.id as ram_id, ra.size as ram_size, ra.price as ram_price, \n"
					+ "co.id as color_id, co.name as color_name, \n"
					+ "os, cp.id as cpu_id, cp.name as cpu_name, cp.price as cpu_price, description, deleted \n"
					+ "from items as it \n"
					+ "LEFT JOIN notepcs as no ON it.id = no.id \n" + "LEFT JOIN roms as ro ON no.rom = ro.id \n"
					+ "LEFT JOIN rams as ra ON no.rom = ra.id \n" + "LEFT JOIN colors as co ON no.color = co.id \n"
					+ "LEFT JOIN makers as ma ON no.maker_id = ma.id \n" + "LEFT JOIN cpus as cp ON no.cpu = cp.id \n"
					+ "WHERE it.id = :id;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			notePcList = template.query(sql, param, NOTE_PC_ROW_MAPPER);
			return notePcList.get(0);
		case 2:
			List<DesktopPc> desktopPcList = new ArrayList();
			sql = "select it.id as id, it.name as name, it.category_id as category_id, \n"
					+ "base_price, image_path, maker_id, \n"
					+ "ro.id as rom_id, ro.size as rom_size, ro.price as rom_price, \n"
					+ "ra.id as ram_id, ra.size as ram_size, ra.price as ram_price, \n"
					+ "co.id as color_id, co.name as color_name, \n"
					+ "os, cp.id as cpu_id, cp.name as cpu_name, cp.price as cpu_price, description, deleted \n"
					+ "from items as it \n" + "LEFT JOIN desktoppcs as no ON it.id = no.id \n"
					+ "LEFT JOIN roms as ro ON no.rom = ro.id \n" + "LEFT JOIN rams as ra ON no.rom = ra.id \n"
					+ "LEFT JOIN colors as co ON no.color = co.id \n"
					+ "LEFT JOIN makers as ma ON no.maker_id = ma.id \n" + "LEFT JOIN cpus as cp ON no.cpu = cp.id \n"
					+ "WHERE it.id = :id;";
			SqlParameterSource param2 = new MapSqlParameterSource().addValue("id", id);
			desktopPcList = template.query(sql, param2, DESKTOP_PC_ROW_MAPPER);
			return desktopPcList.get(0);
		}
		return null;// 便宜上必要な記述
	}

	public List<Object> searchDetailsItems(String sqlAppendParts) {

		String sql = "select it.id as id, it.name as name, it.category_id as category_id,"
				+ "base_price, image_path, maker_id,"
				+ "ro.id as rom_id, ro.size as rom_size, ro.price as rom_price, \n"
				+ "ra.id as ram_id, ra.size as ram_size, ra.price as ram_price, \n"
				+ "co.id as color_id, co.name as color_name, \n"
				+ "os, cp.id as cpu_id, cp.name as cpu_name, cp.price as cpu_price, description, deleted \n"
				+ "from items as it \n" + "LEFT JOIN notepcs as no ON it.id = no.id \n"
				+ "LEFT JOIN roms as ro ON no.rom = ro.id \n" + "LEFT JOIN rams as ra ON no.rom = ra.id \n"
				+ "LEFT JOIN colors as co ON no.color = co.id \n" + "LEFT JOIN makers as ma ON no.maker_id = ma.id \n"
				+ "LEFT JOIN cpus as cp ON no.cpu = cp.id \n" + "WHERE it.category_id = 1 ";

		StringBuilder sb = new StringBuilder(sql);
		String complateSql = sb.append(sqlAppendParts).toString();

		List<Object> itemList = new ArrayList<>();
		List<NotePc> notePcList = new ArrayList<>();

		notePcList = template.query(complateSql, NOTE_PC_ROW_MAPPER);
		for (NotePc notePc : notePcList) {
			itemList.add(notePc);
		}
		return itemList;
	}
}
