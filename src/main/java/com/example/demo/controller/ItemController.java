package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;
import com.example.demo.form.OrderForm;
import com.example.demo.form.ReviewForm;
import com.example.demo.form.SearchForm;
import com.example.demo.form.TotalSearchOptionsForm;
import com.example.demo.service.ColorService;
import com.example.demo.service.CpuService;
import com.example.demo.service.FavoriteItemService;
import com.example.demo.service.ItemServise;
import com.example.demo.service.MakerService;
import com.example.demo.service.RamService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.RomService;
import com.example.demo.service.SendMailService;
import com.example.demo.service.UserServise;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemServise itemServise;

	@Autowired
	private HttpSession session;

	@Autowired
	private FavoriteItemService favoriteItemService;

	@ModelAttribute
	private SearchForm setUpSearchForm() {
		return new SearchForm();
	}

	@Autowired
	private UserController userController;

	@Autowired
	private UserServise userServise;

	@Autowired
	private SendMailService sendMailService;

	@Autowired
	private ColorService colorService;

	@Autowired
	private CpuService cpuService;

	@Autowired
	private RamService ramService;

	@Autowired
	private RomService romService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MakerService makerService;

	@ModelAttribute
	private OrderForm setUpOrderForm() {
		return new OrderForm();
	}

	@ModelAttribute
	private ReviewForm setUpReviewForm() {
		return new ReviewForm();
	}

	@ModelAttribute
	private TotalSearchOptionsForm setUpTotalSearchOptionsForm() {
		return new TotalSearchOptionsForm();
	}

	/* indexページへ遷移するメソッド */
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("makers", makerService.findAll());
		model.addAttribute("colors", colorService.findAll());
		return "item_list_index";
	}

	/* 絞り込み検索がかけられるメソッド */
	@RequestMapping("search")
	public String search(TotalSearchOptionsForm totalSearchOptionsForm, Integer categoryId, Model model) {
		model.addAttribute("itemList", itemServise.searchDetailsItems(totalSearchOptionsForm));
		model.addAttribute("scoreMap", reviewService.showScoreForFindAll(categoryId));
		return "item_list_robot";
	}

	/**
	 * ヒットした件数を返すメソッド
	 * 
	 * @param totalMakerNum
	 * @param totalPriceNum
	 * @param totalColorNum
	 * @param totalMoniterSizeNum
	 * @return
	 */
	@ResponseBody // JSON形式で返す場合はこのアノテーションが必要。
	@RequestMapping(value = "/getNumber", method = RequestMethod.POST)
	public Map<String, String> getNumber(String totalMakerNum, String totalPriceNum, String totalColorNum,
			String totalMoniterSizeNum) {

		// 引数からformのインスタンスを生成する作業
		// 本音を言うと引数の段階で「form」として受け取りたいのですが、それはできないかもしれないです…
		TotalSearchOptionsForm form = new TotalSearchOptionsForm();
		form.setTotalMakerNum(totalMakerNum);
		form.setTotalPriceNum(totalPriceNum);
		form.setTotalColorNum(totalColorNum);
		form.setTotalMoniterSizeNum(totalMoniterSizeNum);

		// formをサービスクラスに渡して、ヒット件数を取得する作業
		List<Object> itemList = itemServise.searchDetailsItems(form);// 既存メソッドを活用。この「結果（配列）のサイズ」は「ヒット件数」と同義。
		Map<String, String> map = new HashMap<>();// JSがレスポンスデータにJSON形式を指定しているため、Mapで返さなければならない。
		map.put("number", String.valueOf(itemList.size()));// マップに値をセットする。
		return map;// 返す！
	}

	@RequestMapping("/notePc")
	public String notePc(Model model) {
		session.setAttribute("currentMethod", "index");
		model.addAttribute("itemList", itemServise.findAll());
		model.addAttribute("scoreMap", reviewService.showScoreForFindAll(1));
		return "item_list_robot";
	}

	@RequestMapping("/desktopPc")
	public String desktopPc(Model model) {
		session.setAttribute("currentMethod", "index");
		model.addAttribute("itemList", itemServise.findAllDesktopPc());
		model.addAttribute("scoreMap", reviewService.showScoreForFindAll(2));
		return "item_list_robot";
	}

	/**
	 * @param id         商品id
	 * @param itemTypeId アイテムの種類を識別するID
	 * @param model
	 * @return 商品詳細画面へ遷移
	 */
	@RequestMapping("/detail")
	public String detail(Integer id, Model model) {
		model.addAttribute("romList", romService.findAll());
		model.addAttribute("ramList", ramService.findAllRams());
		model.addAttribute("colorList", colorService.findAll());
		model.addAttribute("cpuList", cpuService.findAllCpus());
		model.addAttribute("item", itemServise.load(id));
		model.addAttribute("reviewScore", reviewService.loadReviewScore(id));
		return "item_detail";
	}

	/**
	 * レビュー画面への遷移
	 * 
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("/show-review")
	public String showReview(Integer itemId, Model model) {
		model.addAttribute("item", itemServise.load(itemId));
		model.addAttribute("reviewList", reviewService.load(itemId));
		model.addAttribute("reviewScore", reviewService.loadReviewScore(itemId));
		model.addAttribute("scoreCulc", reviewService.scoreCulc(itemId));
		return "review";
	}

	/**
	 * レビュー表示
	 * 
	 * @param reviewForm
	 * @return
	 */
	@RequestMapping("/write-review")
	public String writeReview(ReviewForm reviewForm, Integer categoryId, Model model) {
		reviewService.insert(reviewService.makeReview(reviewForm));
		return this.showReview(Integer.parseInt(reviewForm.getItemId()), model);
	}

	/**
	 * レビュー投稿
	 * 
	 * @param itemId
	 * @param score
	 * @param model
	 * @return
	 */
	@RequestMapping("/search-review")
	public String searchReview(Integer itemId, float score, Model model) {
		model.addAttribute("item", itemServise.load(itemId));
		model.addAttribute("reviewList", reviewService.reviewSearch(itemId, score));
		model.addAttribute("reviewScore", reviewService.loadReviewScore(itemId));
		return "reviewDetail";
	}

	/**
	 * お気に入り画面表示
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/favorite")
	public String showFavorite(Model model) {
		model.addAttribute("favoriteItemList",
				favoriteItemService.load(((User) session.getAttribute("user")).getId()));
		return "favorite_items";
	}

	/**
	 * お気に入りボタンを押したときDBにインサートするメソッド
	 * 
	 * @param userId
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/favorite_insert")
	public String insertFavoriteItems(Integer itemId, Model model) {
		favoriteItemService
				.insert(favoriteItemService.createFavoriteItem(((User) session.getAttribute("user")).getId(), itemId));
		return showFavorite(model);
	}

	@RequestMapping("/favorite_delete")
	public String deleteFavoriteItems(Integer itemId, Model model) {
		favoriteItemService.delete(itemId, ((User) session.getAttribute("user")).getId());
		return showFavorite(model);
	}
}
