package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private HttpSession session;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	/**
	 * オーダーの生成 呼び出し条件：「ユーザーがログインしている」かつ「セッションスコープにカートがある」
	 */
	public Order create(Integer userId) {
		return orderRepository.create(userId);
	}

	public Order loadOrderBeforePayment(Integer userId) {
		return orderRepository.loadOrderBeforePayment(userId);
	}

	public List<Order> loadOrderAfterPayment(Integer userId) {
		return orderRepository.loadOrderAfterPayment(userId);
	}

	public void update(Order order) {
		orderRepository.update(order);
	}

	public Order upDateStatus(Order order, int newStatus) {
		order.setStatus(newStatus);
		return order;
	}
}
