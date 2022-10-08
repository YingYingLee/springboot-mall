package com.springbootmall.dao;

import com.springbootmall.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);
}
