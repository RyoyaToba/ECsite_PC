package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SearchService {

	public String createAppendSQL(Map<String, String[]> totalOptionsMap) {
		StringBuilder sqlAppendParts = new StringBuilder();

		String[] totalMakerNum = totalOptionsMap.get("totalMakerNum");
		String[] totalColorNum = totalOptionsMap.get("totalColorNum");
		String[] totalMoniterSizeNum = totalOptionsMap.get("totalMoniterSizeNum");


		// ①makerのソート処理
		if (!(totalMakerNum[0].equals(""))) {
			sqlAppendParts.append("AND(");
			Integer counter = 0;
			for (Integer makerId = 1; makerId <= 10; makerId++) {
				if (counter == 0) {
					if (Arrays.asList(totalMakerNum).contains(makerId.toString())) {
						sqlAppendParts.append("maker_id = " + makerId);
						counter++;
					}
				} else {
					if (Arrays.asList(totalMakerNum).contains(makerId.toString())) {
						sqlAppendParts.append(" OR maker_id = " + makerId);
						counter++;
					}
				}
			}
			sqlAppendParts.append(")");
		}

		// ②Colorのソート処理
		if (!(totalColorNum[0].equals(""))) {
			// Makerの処理
			sqlAppendParts.append("AND(");
			Integer counter = 0;
			for (Integer colorId = 1; colorId <= 4; colorId++) {
				if (counter == 0) {
					if (Arrays.asList(totalColorNum).contains(colorId.toString())) {
						sqlAppendParts.append("color = " + colorId);
						counter++;
					}
				} else {
					if (Arrays.asList(totalColorNum).contains(colorId.toString())) {
						sqlAppendParts.append(" OR color = " + colorId);
						counter++;
					}
				}
			}
			sqlAppendParts.append(")");
		}

		// ③MoniterSizeのソート処理
		if (!(totalMoniterSizeNum[0].equals(""))) {
			sqlAppendParts.append("AND(");

			Map<String, Integer> moniterSizeMap = new HashMap<>();
			List<Map<String, Integer>> moniterSizeList = new ArrayList<>();
			Integer minMoniterSize = 0;
			Integer maxMoniterSize = 0;
			for (Integer i = 0; i < totalMoniterSizeNum.length; i++) {
				if (i == 0 || i % 2 == 0) {
					moniterSizeMap = new HashMap<>();
					minMoniterSize = Integer.parseInt(totalMoniterSizeNum[i]);
					moniterSizeMap.put("minMoniterSize", minMoniterSize);
				} else {
					maxMoniterSize = Integer.parseInt(totalMoniterSizeNum[i]);
					moniterSizeMap.put("maxMoniterSize", maxMoniterSize);
					moniterSizeList.add(moniterSizeMap);
				}
			}

			Integer counter = 0;
			for (Map<String, Integer> moniterSizeMapSqlCreate : moniterSizeList) {
				Integer minMoniterSizeSqlCreate = moniterSizeMapSqlCreate.get("minMoniterSize");
				Integer maxMoniterSizeSqlCreate = moniterSizeMapSqlCreate.get("maxMoniterSize");
				if (counter == 0) {
					sqlAppendParts
							.append(minMoniterSizeSqlCreate + " <= moniter_size" + " AND moniter_size <= "
									+ maxMoniterSizeSqlCreate);
				} else {
					sqlAppendParts.append(
							" OR " + minMoniterSizeSqlCreate + " <= moniter_size" + " AND moniter_size <= "
									+ maxMoniterSizeSqlCreate);
				}
				counter++;
			}
			sqlAppendParts.append(")");
		}
		return sqlAppendParts.toString();
	}
}
