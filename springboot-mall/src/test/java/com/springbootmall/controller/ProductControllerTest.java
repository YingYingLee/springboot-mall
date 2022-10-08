package com.springbootmall.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootmall.constant.ProductCategory;
import com.springbootmall.dto.ProductRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// 查詢商品
	@Test
	public void getProduct_success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products/{productId}", 1);
		
		mockMvc.perform(requestBuilder)
		       .andDo(print())
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")))
		       .andExpect(jsonPath("$.category", equalTo("FOOD")))
		       .andExpect(jsonPath("$.imageUrl", notNullValue()))
		       .andExpect(jsonPath("$.price", notNullValue()))
		       .andExpect(jsonPath("$.stock", notNullValue()))
		       .andExpect(jsonPath("$.description", notNullValue()))
		       .andExpect(jsonPath("$.createdDate", notNullValue()))
		       .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}
	
	@Test
	public void getProduct_notFound() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products/{productId}", 20000);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isNotFound());
	}
	
	// 創建商品
	@Transactional
	@Test
	public void createProduct_success() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		productRequest.setCategory(ProductCategory.FOOD);
		productRequest.setImageUrl("http://test.com");
		productRequest.setPrice(100);
		productRequest.setStock(2);
		productRequest.setDescription("test description");
		
		String json = objectMapper.writeValueAsString(productRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isCreated())
		       .andExpect(jsonPath("$.productName", equalTo("test food product")))
		       .andExpect(jsonPath("$.category", equalTo("FOOD")))
		       .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
		       .andExpect(jsonPath("$.price", equalTo(100)))
		       .andExpect(jsonPath("$.stock", equalTo(2)))
		       .andExpect(jsonPath("$.description", notNullValue()))
		       .andExpect(jsonPath("$.createdDate", notNullValue()))
		       .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}
	
	@Transactional
	@Test
	public void createProduct_illegalArgument() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		
		String json = objectMapper.writeValueAsString(productRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isBadRequest());
	}
	
	// 更新商品
	@Transactional
	@Test
	public void updateProduct_success() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		productRequest.setCategory(ProductCategory.FOOD);
		productRequest.setImageUrl("http://test.com");
		productRequest.setPrice(100);
		productRequest.setStock(2);
		productRequest.setDescription("test description");
		
		String json = objectMapper.writeValueAsString(productRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/products/{productId}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
	           .andExpect(status().isOk())
	           .andExpect(jsonPath("$.productName", equalTo("test food product")))
	           .andExpect(jsonPath("$.category", equalTo("FOOD")))
	           .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
	           .andExpect(jsonPath("$.price", equalTo(100)))
	           .andExpect(jsonPath("$.stock", equalTo(2)))
	           .andExpect(jsonPath("$.description", notNullValue()))
	           .andExpect(jsonPath("$.createdDate", notNullValue()))
	           .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}
	
	@Transactional
	@Test
	public void updateProduct_illegalArgument() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		
		String json = objectMapper.writeValueAsString(productRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/products/{productId}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isBadRequest());
	}
	
	@Transactional
	@Test
	public void updateProduct_productNotFound() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		productRequest.setCategory(ProductCategory.FOOD);
		productRequest.setImageUrl("http://test.com");
		productRequest.setPrice(100);
		productRequest.setStock(2);
		productRequest.setDescription("test description");
		
		String json = objectMapper.writeValueAsString(productRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/products/{productId}", 100)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
	           .andExpect(status().isNotFound());
	}
	
	// 刪除商品
	@Transactional
	@Test
	public void deleteProduct_success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/products/{productId}", 1);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isNoContent());
	}
	
	@Transactional
	@Test
	public void deleteProduct_deleteNonExistingProduct() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/products/{productId}", 100);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isNoContent());
	}
	
	// 查詢商品列表
	@Test
	public void getProducts() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products");
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.limit", notNullValue()))
		       .andExpect(jsonPath("$.offset", notNullValue()))
		       .andExpect(jsonPath("$.total", notNullValue()))
		       .andExpect(jsonPath("$.results", hasSize(5)));
	}
	
	@Test
	public void getProducts_filtering() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products")
				.param("category", "CAR")
				.param("search", "B");
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.limit", notNullValue()))
		       .andExpect(jsonPath("$.offset", notNullValue()))
		       .andExpect(jsonPath("$.total", notNullValue()))
		       .andExpect(jsonPath("$.results", hasSize(2)));
	}
	
	@Test
	public void getProducts_sorting() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products")
				.param("orderBy", "price")
				.param("sort", "asc");
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.limit", notNullValue()))
		       .andExpect(jsonPath("$.offset", notNullValue()))
		       .andExpect(jsonPath("$.total", notNullValue()))
		       .andExpect(jsonPath("$.results", hasSize(5)))
		       .andExpect(jsonPath("$.results[0].productId", equalTo(3)))
		       .andExpect(jsonPath("$.results[1].productId", equalTo(1)))
		       .andExpect(jsonPath("$.results[2].productId", equalTo(2)))
		       .andExpect(jsonPath("$.results[3].productId", equalTo(4)))
		       .andExpect(jsonPath("$.results[4].productId", equalTo(7)));
	}
	
	@Test
	public void getProducts_pagination() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products")
				.param("limit", "3")
				.param("offset", "2");
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.limit", notNullValue()))
		       .andExpect(jsonPath("$.offset", notNullValue()))
		       .andExpect(jsonPath("$.total", notNullValue()))
		       .andExpect(jsonPath("$.results", hasSize(3)))
		       .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
		       .andExpect(jsonPath("$.results[1].productId", equalTo(4)))
		       .andExpect(jsonPath("$.results[2].productId", equalTo(3)));
	}
}
