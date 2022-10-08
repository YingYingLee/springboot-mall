package com.springbootmall.service;

import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;

public interface ProductService {

	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
}
