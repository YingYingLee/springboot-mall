package com.springbootmall.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootmall.dao.UserDao;
import com.springbootmall.dto.UserLoginRequest;
import com.springbootmall.dto.UserRegisterRequest;
import com.springbootmall.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserDao userDao;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// 註冊新帳號
	@Test
	public void register_success() throws Exception {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setEmail("test1@gmail.com");
		userRegisterRequest.setPassword("123");
		
		String json = objectMapper.writeValueAsString(userRegisterRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isCreated())
		       .andExpect(jsonPath("$.userId", notNullValue()))
		       .andExpect(jsonPath("$.email", equalTo("test1@gmail.com")))
		       .andExpect(jsonPath("$.createdDate", notNullValue()))
		       .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
		
		// 檢查資料庫中的密碼不為明碼
		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
		assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());
	}
	
	@Test
	public void register_emailAlreadyExist() throws Exception {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setEmail("test2@gmail.com");
		userRegisterRequest.setPassword("123");
		
		String json = objectMapper.writeValueAsString(userRegisterRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isCreated());
		
		// 再次使用同個 email 註冊
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isBadRequest());
	}
	
	// 登入
	@Test
	public void login_success() throws Exception {
		// 先註冊新帳號
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setEmail("test3@gmail.com");
		userRegisterRequest.setPassword("123");
		
		register(userRegisterRequest);
		
		// 再測試登入功能
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail(userRegisterRequest.getEmail());
		userLoginRequest.setPassword(userRegisterRequest.getPassword());
		
		String json = objectMapper.writeValueAsString(userLoginRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.userId", notNullValue()))
		       .andExpect(jsonPath("$.email", equalTo(userRegisterRequest.getEmail())))
		       .andExpect(jsonPath("$.createdDate", notNullValue()))
		       .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}
	
	@Test
	public void login_wrongPassword() throws Exception {
		// 先註冊新帳號
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setEmail("test4@gmail.com");
		userRegisterRequest.setPassword("123");
		
		register(userRegisterRequest);
		
		// 測試密碼輸入錯誤的情況
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail(userRegisterRequest.getEmail());
		userLoginRequest.setPassword("unknown");
		
		String json = objectMapper.writeValueAsString(userLoginRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void login_invalidEmailFormat() throws Exception {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("unknown");
		userLoginRequest.setPassword("123");
		
		String json = objectMapper.writeValueAsString(userLoginRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void login_emailNotExist() throws Exception {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("unknown@gmail.com");
		userLoginRequest.setPassword("123");
		
		String json = objectMapper.writeValueAsString(userLoginRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		       .andExpect(status().isBadRequest());
	}
	
	private void register(UserRegisterRequest userRegisterRequest) throws Exception {
		String json = objectMapper.writeValueAsString(userRegisterRequest);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
	           .andExpect(status().isCreated());
	}
}
