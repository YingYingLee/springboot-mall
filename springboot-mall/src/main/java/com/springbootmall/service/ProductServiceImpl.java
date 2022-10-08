package com.springbootmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbootmall.dao.ProductDao;
import com.springbootmall.dto.ProductQueryParams;
import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		return productDao.countProduct(productQueryParams);
	}
	
	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		return productDao.getProducts(productQueryParams);
	}
	
	@Override
	public Product getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}
	
	@Override
	public Integer createProduct(ProductRequest productRequest) {
		return productDao.createProduct(productRequest);
	}
	
	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		productDao.updateProduct(productId, productRequest);
	}
	
	@Override
	public void deleteProductById(Integer productId) {
		productDao.deleteProductById(productId);
	}
}
