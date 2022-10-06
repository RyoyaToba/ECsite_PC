package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.User;

@SpringBootTest
class UserServiseTest {

	@Autowired
	private UserServise userServise;

	@BeforeAll // 全テスト実行前に１度だけ実行されるメソッド
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll // 全テスト実行後に１度だけ実行されるメソッド
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach // 各テスト実行前に１度だけ実行されるメソッド
	void setUp() throws Exception {
	}

	@AfterEach // 各テスト実行後に１度だけ実行されるメソッド
	void tearDown() throws Exception {
	}

	private User setUpUser() {
		User user = new User();
		user.setId(1);
		user.setName("washio");
		user.setEmail("washio.imp@gmail.com");
		user.setPassword("password");
		user.setZipcode("111-1111");
		user.setAddress("address");
		user.setTelephone("000-0000-0000");
		return user;
	}

	@Test
	void testInsert() {

	}

	@Test
	void testFindByEmail() {
		// このメソッドの主たる役割
		User user = userServise.findByEmail("washio.nnimp@gmail.com");
		// 予期したものを持ってこれているかの検証
		assertEquals(user.getId(), 1);
		assertEquals(user.getId(), 1);

	}

	@Test
	void testFindByEmailAndPassword() {

	}

	@Test
	void testInsertDummy() {

	}

	@Test
	void testLoad() {

	}

	@Test
	void testCheckPasswordType() {

	}

	@Test
	void testGenerateAuthenticationCode() {

	}

	@Test
	void testGenerateUserFromUserForm() {

	}

	@Test
	void testKeyVerification() {

	}

}
