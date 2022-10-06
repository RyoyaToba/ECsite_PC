package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.domain.Color;
import com.example.demo.domain.Cpu;
import com.example.demo.domain.DesktopPc;
import com.example.demo.domain.Item;
import com.example.demo.domain.NotePc;
import com.example.demo.domain.Ram;
import com.example.demo.domain.Rom;
import com.example.demo.form.OrderItemForm;
import com.example.demo.form.SearchForm;
import com.example.demo.form.TotalSearchOptionsForm;
import com.example.demo.repository.ColorRepository;
import com.example.demo.repository.CpuRepository;
import com.example.demo.repository.DesktopPcRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.NotePcRepository;
import com.example.demo.repository.RamRepository;
import com.example.demo.repository.RomRepository;

@Service
@Transactional
public class ItemServise {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ColorRepository colorRepository;

	@Autowired
	private CpuRepository cpuRepository;

	@Autowired
	private RamRepository ramRepository;

	@Autowired
	private RomRepository romRepository;

	@Autowired
	private NotePcRepository notePcRepository;

	@Autowired
	private DesktopPcRepository desktopPcRepository;

	@Autowired
	private SearchService searchService;

	@ModelAttribute
	private TotalSearchOptionsForm setUpTotalSearchOptionsForm() {
		return new TotalSearchOptionsForm();
	}

	/**
	 * 商品の全件検索
	 * 
	 * @return
	 */
	public List<Object> findAll() {
		return itemRepository.findAll();
	}

	public List<Object> findAllDesktopPc() {
		return itemRepository.findAllDesktopPc();
	}

	/**
	 * 商品IDによる商品検索
	 * 
	 * @param id 商品ID
	 * @return
	 */
	public Object load(Integer id) {
		return itemRepository.load(id);
	}

	public Object filteredSearch(SearchForm form, Model model) {
		StringBuilder sb = new StringBuilder();
		switch (form.getCategoryId()) {
		case 0:
			return null;
		case 1:
			return null;
		case 2:
			return desktopPcRepository.filteredSearch(form, sb);
		}
		return null;
	}

	public Object xxx(Object item, OrderItemForm form) {
		Integer categoryId = ((Item) this.load(form.getItemId())).getCategoryId();
		switch (categoryId) {
		case 1:
			Color color = colorRepository.findById(form.getColorId());
			Cpu cpu = cpuRepository.findById(form.getCpuId());
			Ram ram = ramRepository.findById(form.getRamId());
			Rom rom = romRepository.findById(form.getRomId());
			Map<String, Object> optionMap = new HashMap<>();
			optionMap.put("color", color);
			optionMap.put("cpu", cpu);
			optionMap.put("ram", ram);
			optionMap.put("rom", rom);
			((NotePc) item).setOptionMap(optionMap);
			break;
		case 2:
			Color color2 = colorRepository.findById(form.getColorId());
			Cpu cpu2 = cpuRepository.findById(form.getCpuId());
			Ram ram2 = ramRepository.findById(form.getRamId());
			Rom rom2 = romRepository.findById(form.getRomId());
			Map<String, Object> optionMap2 = new HashMap<>();
			optionMap2.put("color", color2);
			optionMap2.put("cpu", cpu2);
			optionMap2.put("ram", ram2);
			optionMap2.put("rom", rom2);
			((DesktopPc) item).setOptionMap(optionMap2);
			break;
		}
		return item;
	}

	public List<Object> searchDetailsItems(TotalSearchOptionsForm totalSearchOptionsForm) {
		String[] totalMakerNum = totalSearchOptionsForm.getTotalMakerNum().split(",", -1);// チェックされた番号をカンマで区切り配列とします
		String[] totalPriceNum = totalSearchOptionsForm.getTotalPriceNum().split(",", -1);
		String[] totalColorNum = totalSearchOptionsForm.getTotalColorNum().split(",", -1);
		String[] totalMoniterSizeNum = totalSearchOptionsForm.getTotalMoniterSizeNum().split(",", -1);

		// 価格の配列から、最低価格と最高価格が入ったmapを集約したリストを作成する。
		// 最終的なイメージは、List<Map<String, Integer>> priceList=[{minPrice=50001,
		// maxPrice=100000}, {minPrice=200001, maxPrice=250000}]
		List<Map<String, Integer>> priceList = new ArrayList<>();// 最低価格と最高価格が入ったmapを集約するリスト
		if (totalPriceNum[0] != "") {
			Map<String, Integer> priceMap = new HashMap<>();// 最低価格と最高価格が入るmap
			for (int i = 0; i < totalPriceNum.length; i++) {
				Integer priceNum = Integer.parseInt(totalPriceNum[i]);
				if (i == 0 || i % 2 == 0) {// 「初回」と「奇数回(紛らわしいが回数が奇数のとき、添字は偶数になる)」。要するにminPrice
					priceMap = new HashMap<>();
					priceMap.put("minPrice", priceNum);
				} else {// 偶数回（要するにmaxPrice）
					priceMap.put("maxPrice", priceNum);
					priceList.add(priceMap);
				}
			}
		}

		Map<String, String[]> totalOptionsMap = new HashMap<>();// ソート情報の入ったマップ
		totalOptionsMap.put("totalMakerNum", totalMakerNum);
		totalOptionsMap.put("totalColorNum", totalColorNum);
		totalOptionsMap.put("totalMoniterSizeNum", totalMoniterSizeNum);

		String sqlAppendParts = searchService.createAppendSQL(totalOptionsMap);// appendParts作成
		System.out.println(sqlAppendParts);

		List<Object> itemList = itemRepository.searchDetailsItems(sqlAppendParts);// SQLの結合

		// 金額以外で絞り込まれたItemがitemListに格納されている。最後に金額で絞り込んでいく。
		if (!(totalPriceNum[0].equals(""))) {
			List<Object> priceSortItemList = new ArrayList<>();
			for (Map<String, Integer> priceMap : priceList) {
				int minPrice = priceMap.get("minPrice");
				int maxPrice = priceMap.get("maxPrice");
				for (Object item : itemList) {
					Item castedItem = (Item) item;
					if (minPrice <= castedItem.getPrice() && castedItem.getPrice() <= maxPrice) {
						priceSortItemList.add(castedItem);
					}
				}
			}
			return priceSortItemList;
		}
		return itemList;
	}
}
