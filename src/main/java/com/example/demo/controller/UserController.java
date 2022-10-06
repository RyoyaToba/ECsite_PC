package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.form.LoginForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.SendMailService;
import com.example.demo.service.UserServise;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserServise userServise;

	@ModelAttribute
	public UserForm form() {
		return new UserForm();
	}

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	@Autowired
	private HttpSession session;


	@Autowired
	private SendMailService sendMailService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;


	/**
	 * ログイン画面の出力
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/tologin")
	public String index(Model model) {
		return "login";
	}

	/**
	 * ログイン機能
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @param model    エラーメッセージ
	 * @return 商品一覧画面
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm form, BindingResult result, String transitionSourcePage, Model model) {
		model.addAttribute("transitionSourcePage", transitionSourcePage);// エラーチェックで引っかかっても引き継げるようにもう一度セット
		if (result.hasErrors()) {
			return index(model);
		}
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		User user = userServise.findByEmail(form.getEmail());
		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています。");
			return index(model);
		} else {
			if (!(bcpe.matches(form.getPassword(), user.getPassword()))) {
				model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています。");
				return index(model);
			}
		}

		session.setAttribute("user", user);
		user = (User) session.getAttribute("user");
		
		// オーダー問題
		Order order = null;// ひとまず定義
		if (orderService.loadOrderBeforePayment(user.getId()) == null) {// 支払い前のオーダーがなければ
			order = orderService.create(user.getId());// オーダー生成
		} else {// 支払い前のオーダーがあれば
			order = orderService.loadOrderBeforePayment(user.getId());// 支払い前のオーダーを持ってくる
		}

		// ショッピングカートの中身問題(未登録のオーダーアイテムだけをDBに挿入したい)
		if (session.getAttribute("orderItemList") != null) {// セッションにショッピングカートがあれば
			List<OrderItem> unregisteredOrderItemList = orderItemService
					.createListOfUnregisteredOrderItem((List<OrderItem>) session.getAttribute("orderItemList"));// そのカートの中身で未登録のオーダーアイテムだけをリストにする
			if (unregisteredOrderItemList != null) {// そのカートの中身で未登録のオーダーアイテムがあれば
				for (OrderItem unregisteredOrderItem : unregisteredOrderItemList) {// そのオーダーアイテムをDBに格納する。
					orderItemService.insert(unregisteredOrderItem, order);
				}
			}
		}

		// セッションスコープのショッピングカート問題（最新状態にしたい）
		List<OrderItem> newOrderItemList = orderItemService.findByOrderId(order.getId());// 未登録だったオーダーアイテムをDBに格納した後の世界のショッピングカートを取得
		order.setOrderItemList(newOrderItemList);
		session.setAttribute("order", order);
		session.setAttribute("orderItemList", newOrderItemList);

		// 以下、ログイン成功時にどのページに遷移するかの条件分岐
		if (transitionSourcePage == null) {// 遷移元ページ情報が特にない場合
			return "forward:/item";// item一覧ページへ
		}
		switch (transitionSourcePage) {// 遷移元ページ情報がある場合
		case "cart_list":// その遷移元ページが"cart_list"だった場合
			return "forward:/order/confirm";// エラー対策として仮のリンクを書いているが、本来ならforward:/order/confirm
		}
		return null;// 便宜上、必要な記述
	}

	/**
	 * ログアウト機能
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/logout")
	public String logout(Model model) {
		// ログインしているユーザーがいない、あるいはダミーの場合
		if (session.getAttribute("user") == null) {
			model.addAttribute("logoutAlert", "お客さまはまだログインしておりません。ログインしてください。");
			return "login";
		}
		session.removeAttribute("user");
		session.removeAttribute("orderItemList");
		session.removeAttribute("order");
		return "logout";
	}

//
	/**
	 * ユーザー登録画面の出力
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/register")
	public String register(Model model) {
		return "register_user";
	}

	/**
	 * リダイレクトで使用されるメソッド
	 * 
	 * @return 登録完了画面
	 */
	@RequestMapping("/registerUserResult")
	public String registerUserResult() {
		return "register_user_result";
	}

	/**
	 * 入力されたユーザー情報にエラーがないかをチェックする。
	 * 
	 * @param form ユーザーのフォーム
	 * @return 認証コードを確認するページへ遷移
	 */
	@RequestMapping("/checkRegisterError")
	public String checkRegisterError(@Validated UserForm form, BindingResult result, Model model) {

		session.removeAttribute("user");// 新規登録するなら、現在ログイン中のユーザーがいればログアウトされるべきだと考えた。

		if (result.hasErrors()) {
			return register(model);
		}
		if (userServise.findByEmail(form.getEmail()) != null) {
			model.addAttribute("emailMessage", "そのメールアドレスはすでに使われています");
			return register(model);
		}
		if (!(form.getPassword().equals(form.getCheckPassword()))) {
			model.addAttribute("checkPasswordMessage", "パスワードと入力が異なります");
			return register(model);
		}

		int total = userServise.checkPasswordType(form.getPassword());

		if (total < 3) {
			model.addAttribute("checkPasswordMessage", "パスワードは小文字・大文字・数字・記号の3種類以上を使用して設定してください");
			return register(model);
		}

		String sentAuthenticationCode = userServise.generateAuthenticationCode();
		session.setAttribute("userForm", form);
		session.setAttribute("sentAuthenticationCode", sentAuthenticationCode);
		sendMailService.sendAuthenticationMail(form, sentAuthenticationCode);


		// 認証コードを確認するページへ遷移
		return "register_user_mail";
	}

	/**
	 * 認証コードを確認する処理
	 * 
	 * @param form                      新規登録する可能性のあるユーザー情報
	 * @param sentAuthenticationCode    メールで送った認証コード
	 * @param inputedAuthenticationCode ユーザーが入力した認証コード
	 * @return 登録完了画面（失敗）
	 */
	@RequestMapping("/checkAuthenticationCode")
	public String checkAuthenticationCode(String inputedAuthenticationCode) {
		String sentAuthenticationCode = (String) session.getAttribute("sentAuthenticationCode");
		if (sentAuthenticationCode.equals(inputedAuthenticationCode)) {// 認証コードが一致した場合
			return insert();// ユーザーをに新規登録するメソッドを呼ぶ
		} else {// 認証コードが一致しなかった場合
			return "redirect:/registerUserResult";// 登録完了画面（失敗）へ遷移
		}
	}

	/**
	 * メールのURLをクリックしたときに実行されるURL
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping("/checkAuthenticationUrl")
	public String checkAuthenticationUrl(String key) {
		// DBとの照合
		if (userServise.keyVerification(key) != null) {
			UserForm form = (UserForm) session.getAttribute(key);
			User user = userServise.generateUserFromUserForm(form);// フォームからUserインスタンスを生成
			user = userServise.insert(user);// ユーザーをDBに新規登録
			session.setAttribute("user", user);
			// オーダー問題
			Order order = null;// ひとまず定義
			if (orderService.loadOrderBeforePayment(user.getId()) == null) {// 支払い前のオーダーがなければ
				order = orderService.create(user.getId());// オーダー生成
			} else {// 支払い前のオーダーがあれば
				order = orderService.loadOrderBeforePayment(user.getId());// 支払い前のオーダーを持ってくる
			}

			// ショッピングカートの中身問題(未登録のオーダーアイテムだけをDBに挿入したい)
			if (session.getAttribute("orderItemList") != null) {// セッションにショッピングカートがあれば
				List<OrderItem> unregisteredOrderItemList = orderItemService
						.createListOfUnregisteredOrderItem((List<OrderItem>) session.getAttribute("orderItemList"));// そのカートの中身で未登録のオーダーアイテムだけをリストにする
				if (unregisteredOrderItemList != null) {// そのカートの中身で未登録のオーダーアイテムがあれば
					for (OrderItem unregisteredOrderItem : unregisteredOrderItemList) {// そのオーダーアイテムをDBに格納する。
						orderItemService.insert(unregisteredOrderItem, order);
					}
				}
			}

			// セッションスコープのショッピングカート問題（最新状態にしたい）
			List<OrderItem> newOrderItemList = orderItemService.findByOrderId(order.getId());// 未登録だったオーダーアイテムをDBに格納した後の世界のショッピングカートを取得
			order.setOrderItemList(newOrderItemList);
			session.setAttribute("order", order);
			session.setAttribute("orderItemList", newOrderItemList);

			return "redirect:/registerUserResult";// 登録完了画面（成功）へ遷移
		}

		return "register_user_result";
	}

	/**
	 * 認証コードが一致したときの処理
	 * 
	 * @param form 新規登録するユーザー情報
	 * @return 登録完了画面（成功）
	 */
	public String insert() {
		UserForm form = (UserForm) session.getAttribute("userForm");
		User user = userServise.generateUserFromUserForm(form);// フォームからUserインスタンスを生成
		user = userServise.insert(user);// ユーザーをDBに新規登録
		session.setAttribute("user", user);
		// オーダー問題
		Order order = null;// ひとまず定義
		if (orderService.loadOrderBeforePayment(user.getId()) == null) {// 支払い前のオーダーがなければ
			order = orderService.create(user.getId());// オーダー生成
		} else {// 支払い前のオーダーがあれば
			order = orderService.loadOrderBeforePayment(user.getId());// 支払い前のオーダーを持ってくる
		}

		// ショッピングカートの中身問題(未登録のオーダーアイテムだけをDBに挿入したい)
		if (session.getAttribute("orderItemList") != null) {// セッションにショッピングカートがあれば
			List<OrderItem> unregisteredOrderItemList = orderItemService
					.createListOfUnregisteredOrderItem((List<OrderItem>) session.getAttribute("orderItemList"));// そのカートの中身で未登録のオーダーアイテムだけをリストにする
			if (unregisteredOrderItemList != null) {// そのカートの中身で未登録のオーダーアイテムがあれば
				for (OrderItem unregisteredOrderItem : unregisteredOrderItemList) {// そのオーダーアイテムをDBに格納する。
					orderItemService.insert(unregisteredOrderItem, order);
				}
			}
		}

		// セッションスコープのショッピングカート問題（最新状態にしたい）
		List<OrderItem> newOrderItemList = orderItemService.findByOrderId(order.getId());// 未登録だったオーダーアイテムをDBに格納した後の世界のショッピングカートを取得
		order.setOrderItemList(newOrderItemList);
		session.setAttribute("order", order);
		session.setAttribute("orderItemList", newOrderItemList);

		return "redirect:/registerUserResult";// 登録完了画面（成功）へ遷移
	}

	@RequestMapping("/myPage")
	public String myPage() {
		return "myPage";
	}
}
