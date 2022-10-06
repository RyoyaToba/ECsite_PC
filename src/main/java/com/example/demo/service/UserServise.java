package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;
import com.example.demo.form.UserForm;
import com.example.demo.repository.KeyRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class UserServise {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KeyRepository keyRepository;

	/**
	 * ユーザー情報の登録
	 * 
	 * @param user ユーザー情報
	 */
	public User insert(User user) {
		return userRepository.insert(user);
	}

	/**
	 * メールアドレスでユーザー検索する
	 * 
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public User insertDummy(User user) {
		return userRepository.insertDummy(user);
	}

	public User load(int id) {
		return userRepository.load(id);
	}

	/**
	 * 入力されたパスワードが「文字種」の条件を満たしているか確認する
	 * 
	 * @param password
	 * @return
	 */
	public int checkPasswordType(String password) {
		int upperCase = 0;
		int lowerCase = 0;
		int symbol = 0;
		int num = 0;

		String[] words = password.split("");
		for (String word : words) {
			if (word.matches("[A-Z]")) {
				upperCase = 1;
			} else if (word.matches("[a-z]")) {
				lowerCase = 1;
			} else if (word.matches("[-!\"#$%&'@()=~|^\\\\]")) { // [!\"#$%&'()=~|-^\\]
				symbol = 1;
			} else if (word.matches("[0-9]")) {
				num = 1;
			}
		}
		int total = upperCase + lowerCase + symbol + num;
		return total;
	}

	/**
	 * ランダムで４桁の整数を生成し、文字列として返す
	 * 
	 * @return
	 */
	public String generateAuthenticationCode() {
		String sentAuthenticationCode = "";
		for (int i = 1; i <= 4; i++) {
			Random random = new Random();
			int randomNumber = random.nextInt(10);// 0以上10未満
			sentAuthenticationCode += randomNumber;
		}
		return sentAuthenticationCode;
	}

	/**
	 * 入力されたフォームの情報をもとに、Userオブジェクトを生成する
	 * 
	 * @param form
	 * @return
	 */
	public User generateUserFromUserForm(UserForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);

		// パスワードのhash化
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		String encodeedPassword = bcpe.encode(user.getPassword());
		user.setPassword(encodeedPassword);

		return user;
	}

	/**
	 * keyと現在時刻を元に、リポジトリクラスを呼ぶメソッド
	 * 
	 * @param key
	 * @return なければnull
	 */
	public String keyVerification(String key) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime before30minutes = now.minusMinutes(30);// 24時間前
		String email = keyRepository.findByKeyAndTime(key, before30minutes);
		return email;
	}


}
