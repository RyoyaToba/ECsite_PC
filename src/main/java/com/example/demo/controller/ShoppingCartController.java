package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.form.OrderItemForm;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ShoppingCartService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private HttpSession session;

//	/**
//	 * ショッピングカートに商品を挿入する
//	 * 
//	 * @param form オプションの内容や数量を含んだフォーム
//	 * @return ショッピングカートを表示する
//	 */
//	@RequestMapping("/insert")
//	public String insert(OrderItemForm form) {
//		OrderItem orderItem = orderItemService.createIncompleteOrderItem(form);
//		if (shoppingCartService.isThereOrderItemList()) {// オーダーアイテムリストがセッションスコープにあった場合
//			List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemList");
//			orderItemList.add(0, orderItem);
//			session.setAttribute("orderItemList", orderItemList);
//			if (session.getAttribute("order") != null) {
//				User user = (User) session.getAttribute("user");
//				Order order = null;
//				if (orderService.loadOrderBeforePayment(user.getId()) == null) {
//					order = orderService.create(user.getId());
//				} else {
//					order = orderService.loadOrderBeforePayment(user.getId());
//				}
//
//				if (session.getAttribute("orderItemList") != null) {
//					for (OrderItem orderItem1 : (List<OrderItem>) (session.getAttribute("orderItemList"))) {
//						orderItemService.insert(orderItem1, order);
//					}
//				}
//				List<OrderItem> orderItemList1 = orderItemService.findByOrderId(order.getId());
//				order.setOrderItemList(orderItemList1);
//				session.setAttribute("order", order);
//				session.setAttribute("orderItemList", orderItemList1);
//			}
//		} else {// オーダーアイテムリストがセッションスコープになかった場合
//			List<OrderItem> orderItemList = shoppingCartService.createOrderItemList();
//			orderItemList.add(orderItem);
//			session.setAttribute("orderItemList", orderItemList);
//			if (session.getAttribute("order") != null) {
//				User user = (User) session.getAttribute("user");
//				Order order = null;
//				if (orderService.loadOrderBeforePayment(user.getId()) == null) {
//					order = orderService.create(user.getId());
//				} else {
//					order = orderService.loadOrderBeforePayment(user.getId());
//				}
//
//				if (session.getAttribute("orderItemList") != null) {
//					for (OrderItem orderItem1 : (List<OrderItem>) (session.getAttribute("orderItemList"))) {
//						orderItemService.insert(orderItem1, order);
//					}
//				}
//				List<OrderItem> orderItemList1 = orderItemService.findByOrderId(order.getId());
//				order.setOrderItemList(orderItemList1);
//				session.setAttribute("order", order);
//				session.setAttribute("orderItemList", orderItemList1);
//			}
//		}
//		return "redirect:/shopping-cart/show";
//	}

	/**
	 * ショッピングカートに商品を挿入する
	 * 
	 * @param form オプションの内容や数量を含んだフォーム
	 * @return ショッピングカートを表示する
	 */
	@RequestMapping("/insert")
	public String insert2(OrderItemForm form) {
		System.out.println(form);
		OrderItem orderItem = orderItemService.createIncompleteOrderItem(form);// まず不完全なオーダーアイテムを作成
		User user = (User) session.getAttribute("user");
		Order order = (Order) session.getAttribute("order");
		List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemList");

		// ユーザーがログインしているかどうかで「不完全オーダーアイテム」の取り扱いが変わる
		if (user != null) {// ユーザーがログインしていた場合
			orderItem = orderItemService.insert(orderItem, order);// DBにオーダーアイテムを挿入(idを持った完全なオーダーアイテムを戻り値として受け取る)
			System.out.println("orderItem:" + orderItem);
			orderItemList = orderItemService.findByOrderId(order.getId());// 最新版のオーダーアイテムのリストをDBから取得する
			System.out.println("orderItemList:" + orderItemList);
			order.setOrderItemList(orderItemList);
		} else {// ユーザーがログインしていない場合
			if (orderItemList == null) {// もしセッションにショッピングカートがなければここで作る
				orderItemList = new ArrayList<>();
			}
			orderItemList.add(0, orderItem);// ショッピングカートの先頭に不完全なオーダーアイテムを追加
		}
		session.setAttribute("order", order);
		session.setAttribute("orderItemList", orderItemList);// 最新版のオーダーアイテムリストをセッションにセットする
		return "redirect:/shopping-cart/show";
	}

	/**
	 * ショッピングカートを表示する
	 * 
	 * @return
	 */
	@RequestMapping("/show")
	public String show() {
		return "cart_list";
	}

	@RequestMapping("/delete") // redirect-showかな
	public String delete(String index, String id) {
		int intedIndex = Integer.parseInt(index);
		List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemList");
		Order order = (Order) session.getAttribute("order");

		// DBの更新
		if (id != "") {// 削除したいオーダーアイテムがidを持っていた場合
			orderItemService.delete(Integer.valueOf(id));
		}

		// セッションスコープの更新
		// 1.オーダーリスト編
		orderItemList.remove(intedIndex);// リストから削除する
		if (orderItemList.size() == 0) {// その結果リストの中身が何も無くなったら
			session.setAttribute("orderItemList", null);
		} else {
			session.setAttribute("orderItemList", orderItemList);
		}
		// 2.オーダー編
		if (order != null) {// オーダーがあれば
			order.setOrderItemList(orderItemList);// 削除後のリストをセット
		}
		session.setAttribute("order", order);

		return "redirect:/shopping-cart/show";
	}

}
