package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.form.OrderForm;
import com.example.demo.service.ItemServise;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.SendMailService;
import com.example.demo.service.UserServise;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private ItemServise itemServise;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserController userController;

	@Autowired
	private UserServise userServise;

	@Autowired
	private SendMailService sendMailService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@ModelAttribute
	private OrderForm setUpOrderForm() {
		return new OrderForm();
	}

	@RequestMapping("/confirm")
	public String confirm(String transitionSourcePage, Model model) {
		// 非ログインユーザーだった場合ログインページへ遷移させる。
		if (session.getAttribute("user") == null) {
			model.addAttribute("transitionSourcePage", transitionSourcePage);
			return "forward:/tologin";
		}
		List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemList");
		Integer buyTotalPrice = 0;
		for (OrderItem orderItem : orderItemList) {
			buyTotalPrice += orderItem.getSubTotal();
		}
		Integer buyTotalTax = (int) (buyTotalPrice * 0.10);
		model.addAttribute("buyTotalPrice", buyTotalPrice);
		model.addAttribute("buyTotalTax", buyTotalTax);
		return "order_confirm";
	}

	@RequestMapping("/update")
	public String update(@Validated OrderForm orderForm, BindingResult result) {
		if (result.hasErrors()) {
			return "order_confirm";
		}

		Order order = (Order) session.getAttribute("order");
		BeanUtils.copyProperties(orderForm, order);
		order.setOrderDate(new Date());

		if (orderForm.getPaymentMethod() == 1) {
			order = orderService.upDateStatus(order, 1);
		} else {
			order = orderService.upDateStatus(order, 2);
		}
		orderService.update(order);
		session.removeAttribute("orderItemList");
		session.removeAttribute("order");

		Order NewOrder = new Order();
		User user = (User) session.getAttribute("user");
		orderService.create(user.getId());
		session.setAttribute("order", NewOrder);
		sendMailService.sendMessageWhenUserBuysItem();
		return "order_finished";
	}

	@RequestMapping("/order-history")
	public String orderHistory(Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "forward:/tologin";
		}
		List<Order> orderList = orderService.loadOrderAfterPayment(user.getId());

		for (Order order : orderList) {
			order.setOrderItemList((orderItemService.findByOrderId(order.getId())));
		}
		model.addAttribute("orderList", orderList);
		return "order_history";
	}
}
