package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cpu;
import com.example.demo.domain.DesktopPc;
import com.example.demo.domain.Item;
import com.example.demo.domain.NotePc;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.Ram;
import com.example.demo.domain.Rom;
import com.example.demo.form.OrderItemForm;
import com.example.demo.repository.CpuRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderOptionRepository;
import com.example.demo.repository.RamRepository;
import com.example.demo.repository.RomRepository;

@Service
public class OrderItemService {

	private final double taxRate = 0.1;

	@Autowired
	private ItemServise itemService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderOptionRepository orderOptionRepository;

	@Autowired
	private CpuRepository cpuRepository;

	@Autowired
	private RamRepository ramRepository;

	@Autowired
	private RomRepository romRepository;

	/**
	 * OrderItemFormをもとに、不完全なOrderItemを作成する。 不完全の意味：idとorderIdがnullであるため。
	 * 想定される用途：非ログインユーザーが商品をカートに入れた際に、このメソッドを呼び出し不完全なOrderItemを作成する。
	 * その後、セッションスコープのショッピングカートへ格納する。
	 * 
	 * @param form
	 * @return
	 */
	public OrderItem createIncompleteOrderItem(OrderItemForm form) {
		Integer categoryId = ((Item) itemService.load(form.getItemId())).getCategoryId();
		OrderItem orderItem = new OrderItem();
		// orderItem.setId(id);
		orderItem.setItemId(form.getItemId());
		// orderItem.setOrderId(orderId);
		orderItem.setQuantity(form.getQuantity());
		switch (categoryId) {
		case 1:
			NotePc item = (NotePc) itemService.load(form.getItemId());
			item = (NotePc) itemService.xxx(item, form);
			orderItem.setItem(item);

			Map<String, Integer> orderOptionMap = new HashMap<>();
			orderOptionMap.put("colorId", form.getColorId());
			orderOptionMap.put("cpuId", form.getCpuId());
			orderOptionMap.put("ramId", form.getRamId());
			orderOptionMap.put("romId", form.getRomId());
			orderItem.setOrderOptionMap(orderOptionMap);
//priceにオプションの価格が入ってない。
			Cpu cpu1 = cpuRepository.findById(form.getCpuId());
			Ram ram1 = ramRepository.findById(form.getRamId());
			Rom rom1 = romRepository.findById(form.getRomId());
			item.setPrice(item.getBasePrice() + cpu1.getPrice() + ram1.getPrice() + rom1.getPrice());
			Integer subTotalWithoutTax = item.getPrice() * form.getQuantity();
			Integer TaxAmount = (int) (subTotalWithoutTax * taxRate);
			orderItem.setSubTotalWithoutTax(subTotalWithoutTax);
			orderItem.setTaxAmount(TaxAmount);
			orderItem.setSubTotal(subTotalWithoutTax + TaxAmount);
			System.out.println("item.getPrice():" + item.getPrice());
			break;
		case 2:
			DesktopPc item2 = (DesktopPc) itemService.load(form.getItemId());
			item2 = (DesktopPc) itemService.xxx(item2, form);
			orderItem.setItem(item2);

			Map<String, Integer> orderOptionMap2 = new HashMap<>();
			orderOptionMap2.put("colorId", form.getColorId());
			orderOptionMap2.put("cpuId", form.getCpuId());
			orderOptionMap2.put("ramId", form.getRamId());
			orderOptionMap2.put("romId", form.getRomId());
			orderItem.setOrderOptionMap(orderOptionMap2);
			Cpu cpu2 = cpuRepository.findById(form.getCpuId());
			Ram ram2 = ramRepository.findById(form.getRamId());
			Rom rom2 = romRepository.findById(form.getRomId());
			item2.setPrice(item2.getBasePrice() + cpu2.getPrice() + ram2.getPrice() + rom2.getPrice());
			Integer subTotalWithoutTax2 = item2.getPrice() * form.getQuantity();
			Integer TaxAmount2 = (int) (subTotalWithoutTax2 * taxRate);
			orderItem.setSubTotalWithoutTax(subTotalWithoutTax2);
			orderItem.setTaxAmount(TaxAmount2);
			orderItem.setSubTotal(subTotalWithoutTax2 + TaxAmount2);
			break;
		}
		System.out.println(orderItem);
		return orderItem;
	}

	/**
	 * 該当オーダーに相当するオーダーアイテムをDBから取ってくる。
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderItem> getOrderItemsListFromDB(Integer orderId) {
		return null;
	}

	/**
	 * DBに未登録のオーダーアイテムをDBに登録する。
	 * 
	 * @param orderItem スコープから取り出したオーダーアイテムを1つずつ挿入する。
	 */
	public OrderItem insert(OrderItem orderItem, Order order) {
		return orderItemRepository.insert(orderItem, order);
	}

	public List<OrderItem> findByOrderId(Integer orderId) {
		return orderItemRepository.findByOrderId(orderId);
	}

	public List<OrderItem> createListOfUnregisteredOrderItem(List<OrderItem> shoppingCart) {
		List<OrderItem> unregisteredOrderItemList = new ArrayList<>();
		for (OrderItem orderItem : shoppingCart) {
			if (orderItem.getId() == null) {// idがnullということはDB未登録
				unregisteredOrderItemList.add(0, orderItem);
			}
		}
		return unregisteredOrderItemList;
	}

	public void delete(Integer id) {
		orderItemRepository.delete(id);
		orderOptionRepository.delete(id);
	}

	public List<OrderItem> findByOrderItem(Integer orderId) {
		return orderItemRepository.findByOrderId(orderId);
	}

}
