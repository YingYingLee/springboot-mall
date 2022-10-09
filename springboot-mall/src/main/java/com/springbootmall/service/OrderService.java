package com.springbootmall.service;

import com.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
