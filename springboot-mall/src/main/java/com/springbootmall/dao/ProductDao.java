package com.springbootmall.dao;

import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
}
