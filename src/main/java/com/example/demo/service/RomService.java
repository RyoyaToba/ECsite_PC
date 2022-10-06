package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Rom;
import com.example.demo.repository.RomRepository;

@Service
public class RomService {
	@Autowired
	private RomRepository romRepository;

	public List<Rom> findAll() {
		return romRepository.findAll();
	}
}
