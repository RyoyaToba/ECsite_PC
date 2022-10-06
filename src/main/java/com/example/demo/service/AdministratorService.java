package com.example.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.OrderItem;
import com.example.demo.domain.Review;
import com.example.demo.repository.AdministratorRepository;

@Service
public class AdministratorService {

	@Autowired
	private AdministratorRepository admniAdministratorRepository;

	@Autowired
	private ReviewService reviewService;

	public void orderItemInsert() {
		for (int i = 1; i < 100000; i++) {
			OrderItem orderItem = new OrderItem();
			Random ramdom = new Random();
			Integer adminItemId = ramdom.nextInt(407) + 1;
			Integer adminOrderId = i;
			Integer adminQuantitiy = ramdom.nextInt(13) + 1;
			orderItem.setItemId(adminItemId);
			orderItem.setOrderId(adminOrderId);
			orderItem.setQuantity(adminQuantitiy);
			admniAdministratorRepository.orderItemInsert(orderItem);
		}
		System.out.println("完了しました");
	}

	public void createReviewAdmin() {
		for (int i = 1; i <= 10000; i++) {
			Review review = new Review();
			Random ramdom = new Random();
			Integer adminItemId = ramdom.nextInt(407) + 1;
			String adminName = "山田" + (ramdom.nextInt(100) + 1) + " 右衛門";
			float adminScore = ramdom.nextInt(5) + 1;
			String adminComment = "最高でした";
			review.setItemId(adminItemId);
			review.setName(adminName);
			review.setScore(adminScore);
			review.setComment(adminComment);
			reviewService.insert(review);
		}
		System.out.println("完了しました");
	}
}
