package com.springbootmall.service;

import com.springbootmall.dto.UserLoginRequest;
import com.springbootmall.dto.UserRegisterRequest;
import com.springbootmall.model.User;

public interface UserService {

	User getUserById(Integer userId);
	
	Integer register(UserRegisterRequest userRegisterRequest);
	
	User login(UserLoginRequest userLoginRequest);
}
