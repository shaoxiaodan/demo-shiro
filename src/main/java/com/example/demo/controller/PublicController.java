package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/pub")
public class PublicController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/find_user_info")
	public Object findUserInfo(String username) {
			return userService.findAllUserInfoByUsername(username);
	}
}
