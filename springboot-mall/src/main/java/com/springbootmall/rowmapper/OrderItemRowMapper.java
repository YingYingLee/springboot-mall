package com.springbootmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springbootmall.model.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

	@Override
	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderItem orderItem = new OrderItem();
		
		orderItem.setOrderItemId(rs.getInt("orderItemId"));
		orderItem.setOrderId(rs.getInt("orderId"));
		orderItem.setProductId(rs.getInt("productId"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setAmount(rs.getInt("amount"));
		
		orderItem.setProductName(rs.getString("productName"));
		orderItem.setImageUrl(rs.getString("imageUrl"));
		
		return orderItem;
	}
}
