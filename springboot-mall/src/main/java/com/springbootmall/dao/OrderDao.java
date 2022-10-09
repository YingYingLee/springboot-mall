package com.springbootmall.dao;

import java.util.List;

import com.springbootmall.dto.OrderQueryParams;
import com.springbootmall.model.Order;
import com.springbootmall.model.OrderItem;

public interface OrderDao {

	Integer countOrder(OrderQueryParams orderQueryParams);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	Order getOrderById(Integer orderId);
	
	List<OrderItem> getOrderItemsByOrderId(Integer orderId);
	
	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
