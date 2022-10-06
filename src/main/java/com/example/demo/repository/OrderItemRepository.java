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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Color;
import com.example.demo.domain.Cpu;
import com.example.demo.domain.DesktopPc;
import com.example.demo.domain.Item;
import com.example.demo.domain.NotePc;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.Ram;
import com.example.demo.domain.Rom;
import com.example.demo.service.ItemServise;

@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ColorRepository colorRepository;

	@Autowired
	private CpuRepository cpuRepository;

	@Autowired
	private RamRepository ramRepository;

	@Autowired
	private RomRepository romRepository;

	@Autowired
	private ItemServise itemServise;

	private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));

		Map<String, Integer> orderOptionMap = new HashMap<>();
		orderOptionMap.put("color", rs.getInt("color_id"));
		orderOptionMap.put("cpu", rs.getInt("cpu_id"));
		orderOptionMap.put("ram", rs.getInt("ram_id"));
		orderOptionMap.put("rom", rs.getInt("rom_id"));
		orderItem.setOrderOptionMap(orderOptionMap);

		// 以下はローマッパーではなく、メソッド内で詰める。
		// orderItem.setItem();
		// 以下はアイテムが定まらないと、計算できない。
		// subTotalWithoutTax;
		// TaxAmount;
		// subTotal;
		return orderItem;
	};

	public OrderItem insert(OrderItem orderItem, Order order) {
		Integer categolyId = ((Item) orderItem.getItem()).getCategoryId();
		switch (categolyId) {
		case 1:
			String sql = "insert into order_items (item_id, order_id, quantity) values (:itemId, :orderId, :quantity) returning id";
			SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", orderItem.getItemId())
					.addValue("orderId", order.getId()).addValue("quantity", orderItem.getQuantity());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			template.update(sql, param, keyHolder);
			orderItem.setId((Integer) keyHolder.getKey());
			orderItem.setOrderId(orderItem.getId());

			sql = "insert into order_option_notePc (order_item_id, color_id, cpu_id, ram_id, rom_id) values (:orderItemId, :colorId, :cpuId, :ramId, :romId)";
			param = new MapSqlParameterSource().addValue("orderItemId", orderItem.getId())
					.addValue("colorId", ((Color) ((NotePc) orderItem.getItem()).getOptionMap().get("color")).getId())
					.addValue("cpuId", ((Cpu) ((NotePc) orderItem.getItem()).getOptionMap().get("cpu")).getId())
					.addValue("ramId", ((Ram) ((NotePc) orderItem.getItem()).getOptionMap().get("ram")).getId())
					.addValue("romId", ((Rom) ((NotePc) orderItem.getItem()).getOptionMap().get("rom")).getId());
			template.update(sql, param);
			break;
		case 2:
			String sql2 = "insert into order_items (item_id, order_id, quantity) values (:itemId, :orderId, :quantity) returning id";
			SqlParameterSource param2 = new MapSqlParameterSource().addValue("itemId", orderItem.getItemId())
					.addValue("orderId", order.getId()).addValue("quantity", orderItem.getQuantity());
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			template.update(sql2, param2, keyHolder2);
			orderItem.setId((Integer) keyHolder2.getKey());
			orderItem.setOrderId(orderItem.getId());

			sql2 = "insert into order_option_notePc (order_item_id, color_id, cpu_id, ram_id, rom_id) values (:orderItemId, :colorId, :cpuId, :ramId, :romId)";
			param2 = new MapSqlParameterSource().addValue("orderItemId", orderItem.getId())
					.addValue("colorId",
							((Color) ((DesktopPc) orderItem.getItem()).getOptionMap().get("color")).getId())
					.addValue("cpuId", ((Cpu) ((DesktopPc) orderItem.getItem()).getOptionMap().get("cpu")).getId())
					.addValue("ramId", ((Ram) ((DesktopPc) orderItem.getItem()).getOptionMap().get("ram")).getId())
					.addValue("romId", ((Rom) ((DesktopPc) orderItem.getItem()).getOptionMap().get("rom")).getId());
			template.update(sql2, param2);
			break;
		}
		return orderItem;
	}

	public List<OrderItem> findByOrderId(Integer orderId) {
		String sql = "select it.id as id , item_id, order_id, quantity, color_id, cpu_id, ram_id, rom_id \n"
				+ "from order_items as it \n" + "left join order_option_notepc as op \n"
				+ "on it.id = op.order_item_id " + "where order_id = :orderId";

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<OrderItem> orderItemList = new ArrayList<>();
		orderItemList = template.query(sql, param, ORDER_ITEM_ROW_MAPPER);
		List<OrderItem> orderItemList2 = new ArrayList<>();
		System.out.println("orderItemList=" + orderItemList);
		for (OrderItem orderItem : orderItemList) {
			Integer categoryId = ((Item) itemServise.load(orderItem.getItemId())).getCategoryId();
			// Object item = null;
			switch (categoryId) {
			case 1:
				NotePc item = (NotePc) itemRepository.load(orderItem.getItemId());
				Color color = colorRepository.findById(orderItem.getOrderOptionMap().get("color"));
				Cpu cpu = cpuRepository.findById(orderItem.getOrderOptionMap().get("cpu"));
				Ram ram = ramRepository.findById(orderItem.getOrderOptionMap().get("ram"));
				Rom rom = romRepository.findById(orderItem.getOrderOptionMap().get("rom"));
				Map<String, Object> optionMap = new HashMap<>();
				optionMap.put("color", color);
				optionMap.put("cpu", cpu);
				optionMap.put("ram", ram);
				optionMap.put("rom", rom);
				item.setOptionMap(optionMap);
				Integer price = item.getBasePrice() + ((Cpu) (optionMap.get("cpu"))).getPrice()
						+ ((Ram) (optionMap.get("ram"))).getPrice() + ((Rom) (optionMap.get("rom"))).getPrice();
				item.setPrice(price);
				orderItem.setItem(item);
				orderItemList2.add(orderItem);
				break;
			case 2:
				DesktopPc item2 = (DesktopPc) itemRepository.load(orderItem.getItemId());
				Color color2 = colorRepository.findById(orderItem.getOrderOptionMap().get("color"));
				Cpu cpu2 = cpuRepository.findById(orderItem.getOrderOptionMap().get("cpu"));
				Ram ram2 = ramRepository.findById(orderItem.getOrderOptionMap().get("ram"));
				Rom rom2 = romRepository.findById(orderItem.getOrderOptionMap().get("rom"));
				Map<String, Object> optionMap2 = new HashMap<>();
				optionMap2.put("color", color2);
				optionMap2.put("cpu", cpu2);
				optionMap2.put("ram", ram2);
				optionMap2.put("rom", rom2);
				item2.setOptionMap(optionMap2);
				Integer price2 = item2.getBasePrice() + ((Cpu) (optionMap2.get("cpu"))).getPrice()
						+ ((Ram) (optionMap2.get("ram"))).getPrice() + ((Rom) (optionMap2.get("rom"))).getPrice();
				item2.setPrice(price2);
				orderItem.setItem(item2);
				orderItemList2.add(orderItem);
				break;
			}
		}
		for (OrderItem orderItem : orderItemList2) {
			Integer categoryId = ((Item) itemServise.load(orderItem.getItemId())).getCategoryId();
			Integer subTotalWithoutTax = 0;
			switch (categoryId) {
			case 1:
				subTotalWithoutTax += ((NotePc) (orderItem.getItem())).getPrice() * orderItem.getQuantity();
				break;
			case 2:
				subTotalWithoutTax += ((DesktopPc) (orderItem.getItem())).getPrice() * orderItem.getQuantity();
				break;
			}
			orderItem.setSubTotalWithoutTax(subTotalWithoutTax);
			orderItem.setTaxAmount((int) (subTotalWithoutTax * 0.1));
			orderItem.setSubTotal(subTotalWithoutTax + (int) (subTotalWithoutTax * 0.1));
		}
		if (orderItemList2.size() == 0) {
			System.out.println("mr.null");
			return null;
		}
		return orderItemList2;
	}

	public void delete(Integer id) {
		String sql = "delete from order_items where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
