package com.springbootmall.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.springbootmall.dto.UserRegisterRequest;
import com.springbootmall.model.User;
import com.springbootmall.rowmapper.UserRowMapper;

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public User getUserById(Integer userId) {
		String sql = "SELECT userId, email, password, createdDate, lastModifiedDate "
				+ "FROM User WHERE userId = :userId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		
		if (userList.size() > 0) {
			return userList.get(0);
		}
		else {
			return null;
		}
	}
	
	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT userId, email, password, createdDate, lastModifiedDate "
				+ "FROM User WHERE email = :email";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		
		if (userList.size() > 0) {
			return userList.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public Integer createUser(UserRegisterRequest userRegisterRequest) {
		String sql = "INSERT INTO User(email, password, createdDate, lastModifiedDate) "
				+ "VALUES (:email, :password, :createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", userRegisterRequest.getEmail());
		map.put("password", userRegisterRequest.getPassword());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		map.put("createdDate", formatter.format(now));
		map.put("lastModifiedDate", formatter.format(now));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		return keyHolder.getKey().intValue();
	}
}
