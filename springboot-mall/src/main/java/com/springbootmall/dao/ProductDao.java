package com.springbootmall.dao;

import java.util.List;

import com.springbootmall.dto.ProductQueryParams;
import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;

public interface ProductDao {

	Integer countProduct(ProductQueryParams productQueryParams);
	
	List<Product> getProducts(ProductQueryParams productQueryParams);
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void updatStock(Integer productId, Integer stock);
	
	void deleteProductById(Integer productId);
}
