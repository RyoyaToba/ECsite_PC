package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cpu;
import com.example.demo.repository.CpuRepository;

@Service
public class CpuService {

	@Autowired
	private CpuRepository cpuRepository;

	public List<Cpu> findAllCpus() {
		return cpuRepository.findAll();
	}

	public Cpu findById(Integer id) {
		return cpuRepository.findById(id);
	}
}
