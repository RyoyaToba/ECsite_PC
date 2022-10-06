package com.example.demo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.MakerRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class MakerServiceTest {

	@Mock
	private MakerRepository makerRepository;

	@InjectMocks
	private MakerService makerService;

	@Test
	void findAllでMakerRepositoryのfindAllを呼べるか() {
		makerService.findAll();
		verify(makerRepository, times(1)).findAll();
	}
}
