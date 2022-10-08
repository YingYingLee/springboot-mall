package com.springbootmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.springbootmall.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		
		user.setUserId(rs.getInt("userId"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime createdDateTime = LocalDateTime.parse(rs.getString("createdDate"), formatter);
		Date createdDate = Date.from(createdDateTime.atZone(ZoneId.systemDefault()).toInstant());
		user.setCreatedDate(createdDate);
		
		LocalDateTime lastModifiedDateTime = LocalDateTime.parse(rs.getString("lastModifiedDate"), formatter);
		Date lastModifiedDate = Date.from(lastModifiedDateTime.atZone(ZoneId.systemDefault()).toInstant());
		user.setLastModifiedDate(lastModifiedDate);
		
		return user;
	}
}
