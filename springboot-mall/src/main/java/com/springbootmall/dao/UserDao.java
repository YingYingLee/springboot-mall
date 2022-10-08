package com.springbootmall.dao;

import com.springbootmall.dto.UserRegisterRequest;
import com.springbootmall.model.User;

public interface UserDao {

	User getUserById(Integer userId);
	
	Integer createUser(UserRegisterRequest userRegisterRequest);
}
