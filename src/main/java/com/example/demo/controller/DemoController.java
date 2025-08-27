package com.example.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Shiro权限控制，方法1：注解方式
 * 
 * @ClassName: UserController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-08 11:00:05
 */
@Controller
@RequestMapping("/api/admin/user")
public class DemoController {

	/**
	 * @RequiresRoles，需要【角色】才能操作 —— 粗粒度
	 * @RequiresPermissions，需要【权限】才能操作 —— 细粒度
	 * @RequiresAuthentication，需要【已经登录】后才能操作
	 * @return
	 */
	@RequiresRoles(value = { "admin", "editor", "user" }, logical = Logical.AND)
	@RequiresPermissions(value = { "user:list" }, logical = Logical.OR)
	@RequiresAuthentication
	@GetMapping("/list_user")
	public Object listUser() {
		return null;
	}

	@RequiresRoles(value = { "admin", "editor" }, logical = Logical.AND)
	@RequiresPermissions(value = { "", "" })
	@PostMapping("/add_user")
	public Object addUser() {
		return null;
	}

	@RequiresRoles(value = { "admin", "editor" }, logical = Logical.AND)
	@PutMapping("/update_user")
	public Object updateUser() {
		return null;
	}

	@RequiresRoles(value = { "admin", "editor" }, logical = Logical.AND)
	@DeleteMapping("/delete_user")
	public Object deleteUser() {
		return null;
	}

	/*
	 * Shiro权限控制，方法2：编程方式
	 */
	@GetMapping("/change_user")
	public Object changeUser() {

		// 0，获取当前操作主题（用户）
		Subject subject = SecurityUtils.getSubject();

		// 1，判断登录状态
		if (subject.isAuthenticated()) { // 是否已经登录

			// 2， 判断角色
			if (subject.hasRole("admin") || subject.hasRole("editor")) { // 角色是否为admin或者editor
				// 执行操作
			} else {
				// 角色不符合
			}

			// 3，判断权限
			if (subject.isPermitted("/user/add")) {
				// 执行操作
			} else {
				// 没有权限
			}
		} else {
			// 没有登录
		}

		return null; // 4，返回结果
	}

}
