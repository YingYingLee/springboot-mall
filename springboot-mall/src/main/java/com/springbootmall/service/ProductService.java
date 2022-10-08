package com.springbootmall.service;

import java.util.List;

import com.springbootmall.constant.ProductCategory;
import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;

public interface ProductService {

	List<Product> getProducts(ProductCategory category, String search);
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
}
