package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.OrderItem;

@Service
public class ShoppingCartService {

	@Autowired
	private HttpSession session;

	/**
	 * ショッピングカートの生成
	 * 
	 * @return
	 */
	public List<OrderItem> createOrderItemList() {
		List<OrderItem> orderItemList = new ArrayList<>();
		return orderItemList;
	}

	/**
	 * セッションスコープにショッピングカートがあるかのチェック
	 * 
	 * @return あればtrue なければfalse
	 */
	public Boolean isThereOrderItemList() {
		if (session.getAttribute("orderItemList") == null) {
			return false;
		}
		return true;
	}
}
