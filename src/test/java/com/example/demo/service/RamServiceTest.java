package com.example.demo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.RamRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class RamServiceTest {

	@Mock
	private RamRepository ramRepository;

	@InjectMocks
	private RamService ramservice;

	@Test
	void findAllRamsでramRepositoryのfindAllメソッドが呼ばれる() throws Exception {
		ramservice.findAllRams();
		verify(ramRepository, times(1)).findAll();
	}

}
