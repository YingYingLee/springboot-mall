package com.springbootmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.springbootmall.model.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		
		order.setOrderId(rs.getInt("orderId"));
		order.setUserId(rs.getInt("userId"));
		order.setTotalAmount(rs.getInt("totalAmount"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime createdDateTime = LocalDateTime.parse(rs.getString("createdDate"), formatter);
		Date createdDate = Date.from(createdDateTime.atZone(ZoneId.systemDefault()).toInstant());
		order.setCreatedDate(createdDate);
		
		LocalDateTime lastModifiedDateTime = LocalDateTime.parse(rs.getString("lastModifiedDate"), formatter);
		Date lastModifiedDate = Date.from(lastModifiedDateTime.atZone(ZoneId.systemDefault()).toInstant());
		order.setLastModifiedDate(lastModifiedDate);
		
		return order;
	}
}
