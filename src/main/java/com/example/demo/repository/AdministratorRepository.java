package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.OrderItem;

@Repository
public class AdministratorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;


//	private static final RowPapper<Integer> AVERAGE_CALC_ROW_MAPPER = (rs, i) -> {
//	
//		
//		
//	};

	public void orderItemInsert(OrderItem orderItem) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);

		String sql = "INSERT INTO order_items(item_id, order_id, quantity) VALUES(:itemId, :orderId, :quantity)";

		template.update(sql, param);
	}
}
