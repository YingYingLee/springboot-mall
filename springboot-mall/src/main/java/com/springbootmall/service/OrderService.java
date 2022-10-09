package com.springbootmall.service;

import java.util.List;

import com.springbootmall.dto.CreateOrderRequest;
import com.springbootmall.dto.OrderQueryParams;
import com.springbootmall.model.Order;

public interface OrderService {

	Integer countOrder(OrderQueryParams orderQueryParams);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	Order getOrderById(Integer orderId);
	
	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
