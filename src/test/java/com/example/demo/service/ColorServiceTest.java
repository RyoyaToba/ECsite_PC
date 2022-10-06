package com.example.demo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.ColorRepository;

@SpringBootTest
class ColorServiceTest {

	@Mock
	private ColorRepository colorRepository;

	@InjectMocks
	private ColorService colorService;

	@Test
	void findAllが呼ばれるか() {
		colorService.findAll();
		verify(colorRepository, times(1)).findAll();
	}
}
