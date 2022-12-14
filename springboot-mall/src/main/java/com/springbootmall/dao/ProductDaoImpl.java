package com.springbootmall.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.springbootmall.dto.ProductQueryParams;
import com.springbootmall.dto.ProductRequest;
import com.springbootmall.model.Product;
import com.springbootmall.rowmapper.ProductRowMapper;

@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		String sql = "SELECT count(*) FROM Product WHERE 1=1";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 查詢條件
		sql = addFilteringSql(sql, map, productQueryParams);
		
		Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
		
		return total;
	}
	
	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		String sql = "SELECT productId, productName, category, imageUrl, price, "
				+ "stock, description, createdDate, lastModifiedDate "
				+ "FROM Product WHERE 1=1";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 查詢條件
		sql = addFilteringSql(sql, map, productQueryParams);
		
		// 排序
		sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
		
		// 分頁
		sql += " LIMIT :limit OFFSET :offset";
		map.put("limit", productQueryParams.getLimit());
		map.put("offset", productQueryParams.getOffset());
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		
		return productList;
	}
	
	@Override
	public Product getProductById(Integer productId) {
		String sql = "SELECT productId, productName, category, imageUrl, price, "
				+ "stock, description, createdDate, lastModifiedDate "
				+ "FROM Product WHERE productId = :productId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		
		if (productList.size() > 0) {
			return productList.get(0);
		}
		else {
			return null;
		}
	}
	
	@Override
	public Integer createProduct(ProductRequest productRequest) {
		String sql = "INSERT INTO Product(productName, category, imageUrl, price, "
				+ "stock, description, createdDate, lastModifiedDate) "
				+ "VALUES (:productName, :category, :imageUrl, :price, "
				+ ":stock, :description, :createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().name());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		map.put("createdDate", formatter.format(now));
		map.put("lastModifiedDate", formatter.format(now));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		String sql = "UPDATE Product SET productName = :productName, category = :category, "
				+ "imageUrl = :imageUrl, price = :price, stock = :stock, "
				+ "description = :description, lastModifiedDate = :lastModifiedDate "
				+ "WHERE productId = :productId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().name());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		map.put("lastModifiedDate", formatter.format(now));
		
		namedParameterJdbcTemplate.update(sql, map);
	}
	
	@Override
	public void updatStock(Integer productId, Integer stock) {
		String sql = "UPDATE Product SET stock = :stock, lastModifiedDate = :lastModifiedDate "
				+ "WHERE productId = :productId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("stock", stock);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		map.put("lastModifiedDate", formatter.format(now));
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void deleteProductById(Integer productId) {
		String sql = "DELETE FROM Product WHERE productId = :productId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}
	
	private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
		if (productQueryParams.getCategory() != null) {
			sql += " AND category = :category";
			map.put("category", productQueryParams.getCategory().name());
		}
		
		if (productQueryParams.getSearch() != null) {
			sql += " AND productName LIKE :search";
			map.put("search", "%" + productQueryParams.getSearch() + "%");
		}
		
		return sql;
	}
}
