package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.form.UserForm;
import com.example.demo.repository.KeyRepository;

@Service
public class SendMailService {
	@Autowired
	private MailSender mailSender;

	@Autowired
	private HttpSession session;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private HttpSession sesison;

	@Autowired
	private KeyRepository keyRepository;

	/**
	 * ランダムな文字列を生成するメソッド
	 * 
	 * @return
	 */
	public String generateKey() {
		// 指定の文字によるリストを作成
		List<Character> letters = new ArrayList<>();
		char lowercaseAlphabet = 'a';
		for (int i = 1; i <= 26; i++) {// 理解できなければ参照：https://inarizuuuushi.hatenablog.com/entry/2017/05/31/173343
			letters.add(lowercaseAlphabet++);
		}
		char uppercaseAlphabet = 'A';
		for (int i = 1; i <= 26; i++) {
			letters.add(uppercaseAlphabet++);
		}
		char number = '0';
		for (int i = 1; i <= 10; i++) {
			letters.add(number++);
		}

		// ランダムなkeyを生成
		String key = "";
		int digit = 20;// 桁数の指定
		Random random = new Random();
		for (int i = 1; i <= digit; i++) {
			int randomNumber = random.nextInt(letters.size() - 1);
			key += letters.get(randomNumber);
		}
		return key;
	}

	/**
	 * リクエストパラメーターをURLの末尾に埋め込むメソッド
	 * 
	 * @param path             パス
	 * @param requestParameter 変数名
	 * @param value            値
	 * @return
	 */
	public String generateUrl(String path, String requestParameterName, String value) {
		String url = path + "?" + requestParameterName + "=" + value;
		return url;
	}

	/**
	 * HTMLメール送信メソッド
	 * 
	 * @param key
	 * @param email
	 */
	public void sendHtmlMail(String url, String email, String subject, String text) {
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("rakuraku.robot.202204@gmail.com");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText("本文", text);
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param form                   登録ユーザー
	 * @param sentAuthenticationCode 認証コード
	 */
	public void sendAuthenticationMail(UserForm form, String sentAuthenticationCode) {
		String key = generateKey();
		session.setAttribute(key, form);
		keyRepository.insert(key, form.getEmail());
		String url = generateUrl("http://localhost:8080/ec-202204c/checkAuthenticationUrl", "key", key);
		String subject = "【まだ会員登録は完了しておりません】";
		String text = form.getName()+"様<br>"
				+ "数ある企業の中から【らくらくPC】にご興味を持っていただき、誠にありがとうございます。<br>" + "<a href=" + url + ">" + url + "</a><br>"
				+ "30分以内に上記のURLにアクセスするか、ブラウザの画面に下記の認証コードを入力し、会員登録を完了してください。<br>" + "認証コード:【" + sentAuthenticationCode
				+ "】";
		sendHtmlMail(url, form.getEmail(), subject, text);
	}

	/**
	 * 商品注文確定の際に送られるメール送信用メソッド
	 */
	public void sendMessageWhenUserBuysItem() {
		User user = (User) session.getAttribute("user");
		String TEXT = "" + "　---------------------------------------\n" + "　この度は【らくらくPC】をご利用いただきありがとうございました。\n"
				+ "　ご注文番号「XXXX-XXXX-XXXX」で受け付けいたしました。\n" + "　本メール到着後は、商品や本サービスにおけるご注文はキャンセル・変更できません。\n"
				+ "　ご不明な点がございましたら、下記からお問い合わせください。\n" + "　連絡先：XXX-XXXX-XXXX\n"
				+ " ---------------------------------------";
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail()); // 送り手のアドレス
		msg.setSubject("【ご注文を受け付けました】");// タイトル
		msg.setText(TEXT);
		mailSender.send(msg);
	}

}
