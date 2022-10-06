package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.domain.Item;
import com.example.demo.service.ColorService;
import com.example.demo.service.CpuService;
import com.example.demo.service.ItemServise;
import com.example.demo.service.RamService;
import com.example.demo.service.RomService;

@SpringBootTest
class ItemControllerTest {

	@Autowired
	@InjectMocks
	ItemController itemController;

	@MockBean
	ItemServise itemServise;

	@MockBean
	RomService romService;

	@MockBean
	RamService ramService;

	@MockBean
	ColorService colorService;

	@MockBean
	CpuService cpuService;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
	}

	// 入力値、バリデーションが発生しているかのチェック、スコープの確認チェック、（期待しているサービスを呼び出せているか？）

	@Test
	void indexメソッドはindexページへ遷移するか() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/item"))// /itemに対してpostメソッドを発行
				.andExpect(MockMvcResultMatchers.status().isOk()) // リクエストした結果のHTTPステータスがOK(200)であることを確認
				.andExpect(MockMvcResultMatchers.view().name("item_list_index")); // テンプレート名としてprofile/addが返却されることを確認
	}

	@Test
	@DisplayName("ノートPCページへの遷移")
	void ノートPCページへ遷移するか() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/item/notePc")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("item_list_robot"));
	}

	@Test
	@DisplayName("デスクトップPCページへの遷移")
	void デスクトップPCページへ遷移するか() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/item/desktopPc")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("item_list_robot"));
	}

	@Test
	@DisplayName("詳細ページへの遷移")
	void 商品詳細ページへ遷移するか() throws Exception {
		Item item = new Item();
		when(itemServise.load(anyInt())).thenReturn(item);
		mockMvc.perform(MockMvcRequestBuilders.post("/item/detail")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("item_detail"));
	}

}
