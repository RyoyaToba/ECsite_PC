package com.example.demo.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AccessLimitation implements Filter {
	@Autowired
	private HttpSession session;

//	@Autowired
//	private Model model;

	// 予備知識：そもそも「パス」とは、URLにおけるポート番号より後ろの部分の名称。
	// 予備知識：ServletRequest(親インターフェース) <- HttpServletRequest(子インターフェース) /（まだ知らなくていい）

	/**
	 * クライアントが要求する全てのパス（コントローラへのパス、表示したいHTMLが必要としている全てのパス（CSS、画像、JSなど））が通るフィルター
	 * パスの数（要素の数）だけ繰り返される。 例えば100枚の画像を載せたHTMLを表示したければ、100回以上繰り返されることになる。
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;// クライアントがアクセスしたがっている「もの」のインスタンス（不確定なので誤魔化してます…）
		HttpServletResponse res = (HttpServletResponse) response;// ？
		String path = req.getRequestURI();// 「もの」からパス部分だけ抜き出し。

		if (authentication2(path)) {// クライアントがアクセスしたがっているパスがauthenticationに含まれていないとき
			if (session.getAttribute("user") == null) {// かつユーザーがnullだったとき
				req = new HttpServletRequestWrapper((HttpServletRequest) request) {// ？
					@Override
					public String getRequestURI() {
						// accessLimitationMessage(model);
						return "/ec-202204c/tologin";// ここに飛ばす。
					}
				};
			}
		}
		chain.doFilter(req, res);// なんだかんだを経て、クライアントにアクセスを許可してもいいと判断した場合の処理
	}


	/**
	 * 無条件でアクセスを許可するパスの指定
	 * 
	 * @param path クライアントが要求する全てのパス（コントローラへのパス、表示したいHTMLが必要としている全てのパス（CSS、画像、JSなど））
	 * @return パスが以下の要件に含まれていれば「false」を返す。
	 */
	private boolean authentication(String path) {
		return !(path.equals("/ec-202204c/item") || path.startsWith("/ec-202204c/css")
				|| path.startsWith("/ec-202204c/img_icon") || path.startsWith("/ec-202204c/img_indexPage")
				|| path.startsWith("/ec-202204c/img_myPageIcon") || path.startsWith("/ec-202204c/img_robot")
				|| path.startsWith("/ec-202204c/img_slick") || path.startsWith("/ec-202204c/img_topping")
				|| path.startsWith("/ec-202204c/js"));
	}

	/**
	 * 【実験】 アクセスを許可しないパスの指定(上のメソッドの逆)
	 * 
	 * @param path クライアントが要求する全てのパス（コントローラへのパス、表示したいHTMLが必要としている全てのパス（CSS、画像、JSなど））
	 * @return パスが以下の要件に含まれていれば「true」を返す。
	 */
	private boolean authentication2(String path) {
		return path.equals("/ec-202204c/order/update");
	}

	public void accessLimitationMessage(Model model) {
		model.addAttribute("accessLimitationMessage", "ログインしていないユーザーはこのページにアクセスできません");
	}

}
