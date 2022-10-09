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

import com.springbootmall.model.Order;
import com.springbootmall.model.OrderItem;
import com.springbootmall.rowmapper.OrderItemRowMapper;
import com.springbootmall.rowmapper.OrderRowMapper;

@Component
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Order getOrderById(Integer orderId) {
		String sql = "SELECT orderId, userId, totalAmount, createdDate, lastModifiedDate "
				+ "FROM `Order` WHERE orderId = :orderId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		
		List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
		
		if (orderList.size() > 0) {
			return orderList.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
		String sql = "SELECT oi.orderItemId, oi.orderId, oi.productId, "
				+ "oi.quantity, oi.amount, p.productName, p.imageUrl "
				+ "FROM OrderItem AS oi "
				+ "LEFT JOIN Product AS p ON oi.productId = p.productId "
				+ "WHERE oi.orderId = :orderId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		
		List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
		
		return orderItemList;
	}
	
	@Override
	public Integer createOrder(Integer userId, Integer totalAmount) {
		String sql = "INSERT INTO `Order`(userId, totalAmount, createdDate, lastModifiedDate) "
				+ "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("totalAmount", totalAmount);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		map.put("createdDate", formatter.format(now));
		map.put("lastModifiedDate", formatter.format(now));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
		
		// 使用 for loop 一條一條 sql 加入數據，效率較低
//		for (OrderItem orderItem : orderItemList) {
//			String sql = "INSERT INTO OrderItem(orderId, productId, quantity, amount) "
//					+ "VALUES (:orderId, :productId, :quantity, :amount)";
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("orderId", orderId);
//			map.put("productId", orderItem.getProductId());
//			map.put("quantity", orderItem.getQuantity());
//			map.put("amount", orderItem.getAmount());
//			
//			namedParameterJdbcTemplate.update(sql, map);
//		}
		
		// 使用 batchUpdate 一次性加入數據，效率較高
		String sql = "INSERT INTO OrderItem(orderId, productId, quantity, amount) "
				+ "VALUES (:orderId, :productId, :quantity, :amount)";
		
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
		
		for (int i = 0; i < orderItemList.size(); i++) {
			OrderItem orderItem = orderItemList.get(i);
			
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("orderId", orderId);
			parameterSources[i].addValue("productId", orderItem.getProductId());
			parameterSources[i].addValue("quantity", orderItem.getQuantity());
			parameterSources[i].addValue("amount", orderItem.getAmount());
		}
		
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
	}
}
