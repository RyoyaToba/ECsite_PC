package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.OrderItem;
import com.example.demo.service.AdministratorService;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@RequestMapping("")
	public String index() {
		return "administrator";
	}

	@RequestMapping("/order-item-insert")
	public String orderItemInsert(OrderItem orderItem) {
		administratorService.orderItemInsert();
		return index();

	}

	@RequestMapping("/review-insert")
	public String reviewInsert() {
		administratorService.createReviewAdmin();
		return index();
	}

}
