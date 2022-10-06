package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Item;
import com.example.demo.domain.NotePc;
import com.example.demo.form.OrderItemForm;

@SpringBootTest
class ItemServiseTest {

	@Autowired
	private ItemServise itemServise;

	@Test
	void findAllメソッドでItemが241件取得できる() {
		List<Object> itemList = itemServise.findAll();
		assertEquals(241, itemList.size());
	}

	@Test
	void findAllDesktopPcメソッドでItemが168件取得できる() {
		List<Object> itemList = itemServise.findAllDesktopPc();
		assertEquals(168, itemList.size());
	}

	@Test
	void loadメソッドでid1を引数で指定するとid1の商品が検索できる() {
		assertEquals(1, ((NotePc) itemServise.load(1)).getId());
	}

	@Test
	@Disabled
	void xxxメソッドにおいてcategoryIdが1でitemにOptionMapが正しく生成される() {
		Item mock = Mockito.mock(Item.class);
		when(mock.getId()).thenReturn(1);
		OrderItemForm mockForm = Mockito.mock(OrderItemForm.class);
		when(mockForm.getItemId()).thenReturn(1);
		when(mockForm.getColorId()).thenReturn(1);
		when(mockForm.getCpuId()).thenReturn(1);
		when(mockForm.getRamId()).thenReturn(1);
		when(mockForm.getRomId()).thenReturn(1);

		assertEquals("", (itemServise.xxx((Object) mock, mockForm)));

	}


}
