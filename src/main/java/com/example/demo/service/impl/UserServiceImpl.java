package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoleMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public User findAllUserInfoByUsername(String username) {

		// 查询用户
		User user = userMapper.findByUsername(username);

		// 角色集合
		List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());

		// 设置角色
		user.setRoleList(roleList);

		return user;
	}

	@Override
	public User findSimpleUserInfoById(int userId) {
		return userMapper.findById(userId);
	}

	@Override
	public User findSimpleUserInfoByUsername(String username) {
		return userMapper.findByUsername(username);
	}

}
