package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {

	// 获取全部用户信息，包括：角色，权限
	User findAllUserInfoByUsername(String username);

	// 获取用户基本信息
	User findSimpleUserInfoById(int userId);

	// 根据用户名查找用户信息
	User findSimpleUserInfoByUsername(String username);
}
