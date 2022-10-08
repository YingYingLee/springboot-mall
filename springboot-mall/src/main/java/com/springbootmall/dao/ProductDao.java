package com.springbootmall.dao;

import java.util.List;

import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;

public interface ProductDao {

	List<Product> getProducts();
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
}
