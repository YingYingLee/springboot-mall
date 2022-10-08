package com.springbootmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.springbootmall.constant.ProductCategory;
import com.springbootmall.model.Product;


public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		
		product.setProductId(rs.getInt("productId"));
		product.setProductName(rs.getString("productName"));
		product.setCategory(ProductCategory.valueOf(rs.getString("category")));
		product.setImageUrl(rs.getString("imageUrl"));
		product.setPrice(rs.getInt("price"));
		product.setStock(rs.getInt("stock"));
		product.setDescription(rs.getString("description"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime createdDateTime = LocalDateTime.parse(rs.getString("createdDate"), formatter);
		Date createdDate = Date.from(createdDateTime.atZone(ZoneId.systemDefault()).toInstant());
		product.setCreatedDate(createdDate);
		
		LocalDateTime lastModifiedDateTime = LocalDateTime.parse(rs.getString("lastModifiedDate"), formatter);
		Date lastModifiedDate = Date.from(lastModifiedDateTime.atZone(ZoneId.systemDefault()).toInstant());
		product.setLastModifiedDate(lastModifiedDate);
		
		return product;
	}
}
