package com.example.demo.repository;

import java.util.ArrayList;
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
import com.example.demo.domain.DesktopPc;
import com.example.demo.domain.FavoriteItem;
import com.example.demo.domain.NotePc;
import com.example.demo.domain.Ram;
import com.example.demo.domain.Rom;

@Repository
public class FavoriteItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	public static final RowMapper<FavoriteItem> COMPRETE_FAVORITEITE_ROW_MAPPER = (rs, i) -> {

		FavoriteItem favoriteItem = new FavoriteItem();
		favoriteItem.setId(rs.getInt("id"));
		favoriteItem.setUserId(rs.getInt("user_id"));
		favoriteItem.setName(rs.getString("name"));
		favoriteItem.setCategoryId(rs.getInt("category_id"));
		return favoriteItem;
	};

	private static final RowMapper<Object> NOTEPC_FAVORITEITEM_ROW_MAPPER = (rs, i) -> {

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

	private static final RowMapper<Object> DESKTOPPC_FAVORITEITEM_ROW_MAPPER = (rs, i) -> {
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

	/** インサートメソッド */
	public void insert(FavoriteItem favoriteItem) {
		String sql = "INSERT INTO favorite_items (user_id, item_id) VALUES (:userId, :itemId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", favoriteItem.getUserId())
				.addValue("itemId", favoriteItem.getId());
		template.update(sql, param);
	}

	/** デリートメソッド */
	public void delete(Integer itemId, Integer userId) {
		String sql = "DELETE FROM favorite_items WHERE item_id = :itemId AND user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId).addValue("userId", userId);
		template.update(sql, param);
	}

	public List<FavoriteItem> loadForGetCategoryId(Integer userId) {
		String sql = "SELECT id, name, user_id, category_id  FROM items JOIN favorite_items ON favorite_items.item_id = items.id WHERE user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.query(sql, param, COMPRETE_FAVORITEITE_ROW_MAPPER);
	}

	/** userIdで検索メソッド */
	public List<Object> load(Integer userId) {

		List<FavoriteItem> favoriteItemList = this.loadForGetCategoryId(userId);
		List<Object> completeFavoriteItemList = new ArrayList<>();

		for (FavoriteItem favoriteItem : favoriteItemList) {
			Integer categoryId = favoriteItem.getCategoryId();
			Integer itemId = favoriteItem.getId();

			switch (categoryId) {
			case 1:
				String sql = "SELECT items.id, items.name as name, image_path, base_price, items.category_id as category_id, maker_id, os, description, deleted, "
						+ " roms.id as rom_id, roms.size as rom_size, roms.price as rom_price,"
						+ " rams.id as ram_id, rams.size as ram_size, rams.price as ram_price,"
						+ " colors.id as color_id, colors.name as color_name,"
						+ " cpus.id as cpu_id, cpus.name as cpu_name, cpus.price as cpu_price " + " FROM items "
						+ " LEFT JOIN notepcs ON items.id = notepcs.id "
						+ " LEFT JOIN favorite_items ON items.id = favorite_items.item_id "
						+ " LEFT JOIN roms ON roms.id = notepcs.rom" + " LEFT JOIN rams ON rams.id = notepcs.ram "
						+ " LEFT JOIN colors ON colors.id = notepcs.color"
						+ " LEFT JOIN makers ON makers.id = notepcs.maker_id"
						+ " LEFT JOIN cpus ON cpus.id = notepcs.cpu"
						+ " WHERE user_id = :userId AND items.id = :itemId";

				SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("itemId",
						itemId);
				completeFavoriteItemList.add(template.queryForObject(sql, param, NOTEPC_FAVORITEITEM_ROW_MAPPER));
				break;
			case 2:
				String sql2 = "SELECT items.id, items.name as name, image_path, base_price, items.category_id as category_id, maker_id, os, description, deleted, "
						+ " roms.id as rom_id, roms.size as rom_size, roms.price as rom_price,"
						+ " rams.id as ram_id, rams.size as ram_size, rams.price as ram_price,"
						+ " colors.id as color_id, colors.name as color_name,"
						+ " cpus.id as cpu_id, cpus.name as cpu_name, cpus.price as cpu_price " + " FROM items "
						+ " LEFT JOIN desktoppcs ON items.id = desktoppcs.id "
						+ " LEFT JOIN favorite_items ON items.id = favorite_items.item_id "
						+ " LEFT JOIN roms ON roms.id = desktoppcs.rom" + " LEFT JOIN rams ON rams.id = desktoppcs.ram "
						+ " LEFT JOIN colors ON colors.id = desktoppcs.color"
						+ " LEFT JOIN makers ON makers.id = desktoppcs.maker_id"
						+ " LEFT JOIN cpus ON cpus.id = desktoppcs.cpu"
						+ " WHERE user_id = :userId AND items.id = :itemId";

				SqlParameterSource param2 = new MapSqlParameterSource().addValue("userId", userId).addValue("itemId",
						itemId);
				completeFavoriteItemList.add(template.queryForObject(sql2, param2, DESKTOPPC_FAVORITEITEM_ROW_MAPPER));
			}
		}
		return completeFavoriteItemList;
	}
}
