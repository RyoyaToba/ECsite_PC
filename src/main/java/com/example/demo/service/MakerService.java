package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Maker;
import com.example.demo.repository.MakerRepository;

@Service
public class MakerService {

	@Autowired
	private MakerRepository makerRepository;

	public List<Maker> findAll() {
		return makerRepository.findAll();
	}

}
