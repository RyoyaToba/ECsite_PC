package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

@Repository
public class UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};

	private static final RowMapper<User> DUMMYUSER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("max"));
		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * @return 全数検索結果
	 */
	public List<User> findAll() {
		return null;
	}

	/**
	 * usersテーブルから引数と一致したidのユーザーを取得する
	 * 
	 * @param id
	 * @return ユーザー
	 */
	public User load(int id) {
		String loadSql = "select id,name,email,password,zipcode,address,telephone from users where id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(loadSql, param, USER_ROW_MAPPER);
	}

	/**
	 * ユーザーの挿入
	 * 
	 * @param user
	 */
	public User insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into users(name, email, password, zipcode, address, telephone) "
				+ " values(:name, :email, :password, :zipcode, :address, :telephone) returning id;";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, param, keyHolder);
		user.setId((Integer) keyHolder.getKey());
		return user;
	}

	/**
	 * 入力されたメールアドレスからユーザー情報を取得
	 * 
	 * @param mailAddress メールアドレス
	 * @return 管理者情報 存在しない場合はnullを返す
	 */
	public User findByEmail(String email) {
		String sql = "select id,name,email,password,zipcode,address,telephone from users where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * メールアドレスとパスワードの両方が一致したユーザー情報を取得。
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public User findByEmailAndPassword(String email, String password) {
		String findByEmailAndPasswordSQL = "select id, name, email, password, zipcode, address"
				+ ", telephone from users where email = :email and password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> users = template.query(findByEmailAndPasswordSQL, param, USER_ROW_MAPPER);
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	/**
	 * ダミーユーザーをDBに登録し、再度取得し、返す。
	 * 
	 * @param user
	 * @return 登録したダミーユーザー
	 */
	public User insertDummy(User user) {
		// dummy情報入力
		String insertDummySql = "insert into users(name, email, password, zipcode, address, telephone) "
				+ " values(:name, :email, 'dummy', 'dummy', 'dummy', 'dummy');";
		user.setEmail("" + (int) (Math.random() * 1000) + "@" + (int) (Math.random() * 1000));
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(insertDummySql, param);
		
		// dummyUserId取得
		String getDummyUserIdSql = "select max(id) from users;";
		List<User> dummyUser = template.query(getDummyUserIdSql, DUMMYUSER_ROW_MAPPER);
		if (dummyUser.size() == 0) {
			return null;
		}
		return dummyUser.get(0);
	}

}
