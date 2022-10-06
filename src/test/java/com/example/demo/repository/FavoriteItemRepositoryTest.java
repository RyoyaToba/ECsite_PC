package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.DesktopPc;
import com.example.demo.domain.FavoriteItem;
import com.example.demo.domain.NotePc;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class FavoriteItemRepositoryTest {

	@Autowired
	@InjectMocks
	private FavoriteItemRepository favoriteItemRepository;

	@Mock
	private FavoriteItem favoriteItem;

	@BeforeEach
	private void setUp() {
		when(favoriteItem.getUserId()).thenReturn(10000);
		when(favoriteItem.getId()).thenReturn(1);
	}

	@AfterEach
	private void tearDown() {
		favoriteItemRepository.delete(favoriteItem.getId(), favoriteItem.getUserId());
		System.out.println("該当のものを削除しました");
	}

	@Test
	void insertでアイテムが挿入できるか() {
		favoriteItemRepository.insert(favoriteItem);
		System.out.println(favoriteItem.getId() + "を挿入しました");
	}

	@Test
	void loadForgetCategoryIdで検索できるか() {
		this.insertでアイテムが挿入できるか();
		List<FavoriteItem> favoriteItemList = favoriteItemRepository.loadForGetCategoryId(10000);
		assertEquals(1, favoriteItemList.get(0).getId());
		assertEquals("E210KA-GJ03PWS ASUS E210KA", favoriteItemList.get(0).getName());
		assertEquals(10000, favoriteItemList.get(0).getUserId());
		assertEquals(1, favoriteItemList.get(0).getCategoryId());
	}

	@Test
	void loadでcategoryIdが1のものを検索できるか() {
		this.insertでアイテムが挿入できるか();
		List<Object> favoriteItemList = favoriteItemRepository.load(favoriteItem.getUserId());
		assertEquals(1, ((NotePc) favoriteItemList.get(0)).getId());
		assertEquals("E210KA-GJ03PWS ASUS E210KA", ((NotePc) favoriteItemList.get(0)).getName());
		assertEquals("1.jpeg", ((NotePc) favoriteItemList.get(0)).getImagePath());
		assertEquals(45730, ((NotePc) favoriteItemList.get(0)).getBasePrice());
		assertEquals(1, ((NotePc) favoriteItemList.get(0)).getCategoryId());
		assertEquals(1, ((NotePc) favoriteItemList.get(0)).getMakerId());
		assertEquals("Windows11Home", ((NotePc) favoriteItemList.get(0)).getOs());
		assertEquals("Intel Celeron N4500搭載 Windows 11 Home(Sモード) ノートPC",
				((NotePc) favoriteItemList.get(0)).getDescription());
	}

	@Test
	void loadでcategoryIdが2のものを検索できるか() {
		when(favoriteItem.getUserId()).thenReturn(10000);
		when(favoriteItem.getId()).thenReturn(243);
		this.insertでアイテムが挿入できるか();
		List<Object> favoriteItemList = favoriteItemRepository.load(favoriteItem.getUserId());
		assertEquals(243, ((DesktopPc) favoriteItemList.get(0)).getId());
		assertEquals("G-GEAR", ((DesktopPc) favoriteItemList.get(0)).getName());
		assertEquals("243.jpeg", ((DesktopPc) favoriteItemList.get(0)).getImagePath());
		assertEquals(239800, ((DesktopPc) favoriteItemList.get(0)).getBasePrice());
		assertEquals(2, ((DesktopPc) favoriteItemList.get(0)).getCategoryId());
		assertEquals(12, ((DesktopPc) favoriteItemList.get(0)).getMakerId());
		assertEquals("Windows10Pro", ((DesktopPc) favoriteItemList.get(0)).getOs());
		assertEquals("G-GEAR 即納モデル ゲーミングデスクトップPC GeForce RTX 3070 Ti Windows11 Home 搭載",
				((DesktopPc) favoriteItemList.get(0)).getDescription());
	}

}
