package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Color;
import com.example.demo.repository.ColorRepository;

@Service
public class ColorService {

	@Autowired
	private ColorRepository colorRepository;

	/**
	 * @return 色全件取得
	 */
	public List<Color> findAll() {
		return colorRepository.findAll();
	}
}
