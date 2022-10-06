package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Order;

@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private JdbcTemplate template2;

	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		return order;
	};

	private static final RowMapper<Order> PERFECT_ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryDate(rs.getDate("preferred_delivery_date"));
		order.setPreferredDeliveryTime(rs.getInt("preferred_delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};

	public Order create(Integer userId) {
		String sql = "insert into orders (user_id, status) values (:userId, 0) returning id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, param, keyHolder);
		Order order = new Order();
		order.setId((Integer) keyHolder.getKey());
		order.setUserId(userId);
		order.setStatus(0);

		return order;
	}

	public Order loadOrderBeforePayment(Integer userId) {

		String sql = "select id from orders where id = :userId and status = 0";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Order> list = template.query(sql, param, ORDER_ROW_MAPPER);

		if (list.size() == 0) {
			return null;
		} else {
			Order order = new Order();
			order.setId(list.get(0).getId());
			order.setUserId(userId);
			order.setStatus(0);
			return order;
		}
	}

	public List<Order> loadOrderAfterPayment(Integer userId) {
		System.out.println("target===========" + userId);
		String sql = "select * from orders where user_id = :userId and (status = 1 OR status = 2 OR status = 3 OR status = 4 OR status = 9)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Order> list = template.query(sql, param, PERFECT_ORDER_ROW_MAPPER);

		return list;
	}

	public void update(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		String sql = "UPDATE orders SET user_id = :userId, status = :status, total_price = :totalPrice,"
				+ " order_date = :orderDate, destination_name = :destinationName, destination_email = :destinationEmail, "
				+ " destination_zipcode = :destinationZipcode, destination_address = :destinationAddress, destination_tel = :destinationTel,"
				+ " preferred_delivery_date = :preferredDeliveryDate, preferred_delivery_time = :preferredDeliveryTime,"
				+ " payment_method = :paymentMethod "
				+ " WHERE id = :id";

		template.update(sql, param);

	}

//	public void copyOrderHistory(Order order) {
//
//		String sql = "INSERT INTO order_history SELECT * FROM orders WHERE order_id = :orderId;";
//
//	}

}
