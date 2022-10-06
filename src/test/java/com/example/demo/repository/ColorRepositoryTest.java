package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Color;

@SpringBootTest
class ColorRepositoryTest {

	@Autowired
	private ColorRepository colorRepository;

	@Test
	void findAllで全件取得できるか() {
		List<Color> colorList = colorRepository.findAll();
		assertEquals(1, colorList.get(0).getId());
		assertEquals("ブラック", colorList.get(0).getName());
		assertEquals(2, colorList.get(1).getId());
		assertEquals("ホワイト", colorList.get(1).getName());
		assertEquals(3, colorList.get(2).getId());
		assertEquals("シルバー", colorList.get(2).getName());
		assertEquals(4, colorList.get(3).getId());
		assertEquals("レッド", colorList.get(3).getName());
	}

}
