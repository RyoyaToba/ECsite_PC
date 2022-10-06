package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CpuRepositoryTest {

	@Autowired
	private CpuRepository cpuRepository;

	@Test
	void findAllで全件が取得できる() {
		assertEquals(1, cpuRepository.findAll().get(0).getId());
		assertEquals(2, cpuRepository.findAll().get(1).getId());
		assertEquals(3, cpuRepository.findAll().get(2).getId());
		assertEquals(4, cpuRepository.findAll().get(3).getId());
		assertEquals("i3", cpuRepository.findAll().get(0).getName());
		assertEquals("i5", cpuRepository.findAll().get(1).getName());
		assertEquals("i7", cpuRepository.findAll().get(2).getName());
		assertEquals("i10", cpuRepository.findAll().get(3).getName());
		assertEquals(5000, cpuRepository.findAll().get(0).getPrice());
		assertEquals(20000, cpuRepository.findAll().get(1).getPrice());
		assertEquals(40000, cpuRepository.findAll().get(2).getPrice());
		assertEquals(60000, cpuRepository.findAll().get(3).getPrice());
		cpuRepository.findAll();
	}

	@Test
	void findByIdで１を１を引数にしたとき名前i3金額5000が検索できる() {
		assertEquals("i3", cpuRepository.findById(1).getName());
		assertEquals(5000, cpuRepository.findById(1).getPrice());
	}
}
