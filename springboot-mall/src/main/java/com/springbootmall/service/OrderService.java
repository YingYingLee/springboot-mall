package com.springbootmall.service;

import com.springbootmall.dto.CreateOrderRequest;
import com.springbootmall.model.Order;

public interface OrderService {

	Order getOrderById(Integer orderId);
	
	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
