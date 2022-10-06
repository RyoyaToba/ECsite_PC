package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Ram;
import com.example.demo.repository.RamRepository;

@Service
public class RamService {

	@Autowired
	private RamRepository ramRepository;

	/**
	 * @return ramの全件取得
	 */
	public List<Ram> findAllRams() {
		return ramRepository.findAll();
	}

}
