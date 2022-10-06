package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MakerRepositoryTest {

	@Autowired
	private MakerRepository makerRepository;

	@Test
	void findAllメソッドで全てのデータが取得できるか() {
		assertEquals(1, makerRepository.findAll().get(0).getId());
		assertEquals("ASUS エイスース", makerRepository.findAll().get(0).getName());
		assertEquals(2, makerRepository.findAll().get(1).getId());
		assertEquals("DELL デル", makerRepository.findAll().get(1).getName());
		assertEquals(3, makerRepository.findAll().get(2).getId());
		assertEquals("Dynabook ダイナブック", makerRepository.findAll().get(2).getName());
		assertEquals(4, makerRepository.findAll().get(3).getId());
		assertEquals("GIGABYTE ギガバイト", makerRepository.findAll().get(3).getName());
		assertEquals(5, makerRepository.findAll().get(4).getId());
		assertEquals("GPD ジーピーディー", makerRepository.findAll().get(4).getName());
		assertEquals(6, makerRepository.findAll().get(5).getId());
		assertEquals("Lenovo レノボ・ジャパン", makerRepository.findAll().get(5).getName());
		assertEquals(7, makerRepository.findAll().get(6).getId());
		assertEquals("Microsoft マイクロソフト", makerRepository.findAll().get(6).getName());
		assertEquals(8, makerRepository.findAll().get(7).getId());
		assertEquals("富士通 FUJITSU", makerRepository.findAll().get(7).getName());
		assertEquals(9, makerRepository.findAll().get(8).getId());
		assertEquals("Razer レイザー", makerRepository.findAll().get(8).getName());
		assertEquals(10, makerRepository.findAll().get(9).getId());
		assertEquals("NEC エヌイーシー", makerRepository.findAll().get(9).getName());
		assertEquals(11, makerRepository.findAll().get(10).getId());
		assertEquals("MSI エムエスアイ", makerRepository.findAll().get(10).getName());
		assertEquals(12, makerRepository.findAll().get(11).getId());
		assertEquals("eX.computer イーエックスコンピュータ", makerRepository.findAll().get(11).getName());
		assertEquals(13, makerRepository.findAll().get(12).getId());
		assertEquals("MINISFORUM ミニズフォーラム", makerRepository.findAll().get(12).getName());
		assertEquals(14, makerRepository.findAll().get(13).getId());
		assertEquals("テックウインド", makerRepository.findAll().get(13).getName());
		assertEquals(15, makerRepository.findAll().get(14).getId());
		assertEquals("STORM", makerRepository.findAll().get(14).getName());
		assertEquals(16, makerRepository.findAll().get(15).getId());
		assertEquals("intel インテル", makerRepository.findAll().get(15).getName());
		assertEquals(17, makerRepository.findAll().get(16).getId());
		assertEquals("mouse マウスコンピューター", makerRepository.findAll().get(16).getName());
		assertEquals(18, makerRepository.findAll().get(17).getId());
		assertEquals("ECS イーシーエス", makerRepository.findAll().get(17).getName());
	}

}
