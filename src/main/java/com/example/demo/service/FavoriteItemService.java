package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.FavoriteItem;
import com.example.demo.domain.Item;
import com.example.demo.repository.FavoriteItemRepository;

@Service
public class FavoriteItemService {

	@Autowired
	private FavoriteItemRepository favoriteItemRepository;

	/**
	 * インサート
	 * 
	 * @param favoriteItem
	 */
	public void insert(FavoriteItem favoriteItem) {
		if (this.insertJudgment(favoriteItem.getUserId(), favoriteItem.getId())) {
			favoriteItemRepository.insert(favoriteItem);
		}
	}

	/**
	 * 既にお気に入りかどうかを判定するメソッド
	 * 
	 * @param userId
	 * @param itemId
	 * @return
	 */
	public boolean insertJudgment(Integer userId, Integer itemId) {
		List<Object> favoriteItemsList = favoriteItemRepository.load(userId);// ユーザーIDでお気に入りリストを引っ張ってくる
		for (Object favoriteItem : favoriteItemsList) {
			if (((Item) favoriteItem).getId() == itemId) {// 今回INSERTするものと一致しているIDのものが既にあれば・・・
				return false;
			}
		}
		return true;
	}

	/**
	 * デリート
	 * 
	 * @param itemId
	 * @param userId
	 */
	public void delete(Integer itemId, Integer userId) {
		favoriteItemRepository.delete(itemId, userId);
	}

	/**
	 * 検索
	 * 
	 * @param userId
	 * @return
	 */
	public List<Object> load(Integer userId) {
		return favoriteItemRepository.load(userId);
	}

	public FavoriteItem createFavoriteItem(Integer userId, Integer itemId) {
		FavoriteItem favoriteItem = new FavoriteItem();
		favoriteItem.setUserId(userId);
		favoriteItem.setId(itemId);
		return favoriteItem;
	}

}
