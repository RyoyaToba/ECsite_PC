package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;

@SpringBootTest
class OrderControllerTest {

	@Autowired
	OrderController orderController;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	/**
	 * ログインしていなければログイン画面に遷移することを確認するテスト
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("非ログインユーザーだった場合ログインページへ遷移するか")
	void confirmTestメソッドのテスト1() throws Exception {
		mockMvc.perform(post("/order/confirm"))// /order/confirmにpostリクエストの送信
				.andExpect(status().isOk()) // ステータスコード200を確認
				.andExpect(forwardedUrl("/tologin")); // forward先のURLの確認
	}

	/**
	 * ログインしているのであれば（userがいるなら）オーダー確認画面に遷移することを 確認するメソッド
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("ログイン済ユーザーだった場合ログインページへ遷移するか")
	void confirmTestメソッドのテスト2() throws Exception {
		User user = new User();
		List<OrderItem> orderItemList = new ArrayList<>();
		MockHttpSession mockSession = new MockHttpSession();
		mockSession.setAttribute("user", user);
		mockSession.setAttribute("orderItemList", orderItemList);
		// コントローラー呼び出し、OrderControllerクラスのsessionにmockSessionを紐付ける
		mockMvc.perform(post("/order/confirm").session(mockSession))
				.andExpect(view().name("order_confirm"));
	}
	
	@Test
	@DisplayName("フォームに不足値があった場合、確認画面に遷移するか")
	void updateTest() throws Exception {
		mockMvc.perform(post("/order/update")).andExpect(status().isOk()).andExpect(view().name("order_confirm"));
	}
	
	@Test
	@DisplayName("フォームの入力値を受け取り、購入終了画面へ遷移するか")
	void updateTest2() throws Exception {
		mockMvc.perform(post("/order/update").param("destinationName", "test").param("destinationEmail", "a@a")
				.param("destinationZipcode", "000-0000").param("destinationAddress", "テスト県テスト市")
				.param("destinationTel", "000-0000-0000").param("preferredDeliveryDate", "2022-07-07")
				.param("preferredDeliveryTime", "13").param("paymentMethod", "1"))
				.andExpect(status().isOk()).andExpect(view().name("order_finished"));
	}
}
