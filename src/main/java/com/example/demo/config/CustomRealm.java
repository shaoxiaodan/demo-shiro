package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.Permission;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

/**
 * 自定义Realm
 * 
 * @ClassName: CustomRealm
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-08 02:35:29
 */
public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/*
	 * 授权，用户登陆后进行授权校验的时候调用该方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权：AuthenticationInfo...");

		// 获取当前操作的主题名称
		String username = (String) principals.getPrimaryPrincipal();

		// 查询用户
		User user = userService.findAllUserInfoByUsername(username);

		// 把角色，权限以string类型装配到list中
		List<String> strRoleList = new ArrayList<>();
		List<String> strPermissionList = new ArrayList<>();

		// 获取用户中的角色集合，并遍历
		List<Role> roleList = user.getRoleList();
		if (roleList != null && roleList.size() > 0) {
			for (Role role : roleList) {
				strRoleList.add(role.getName());

				// 获取角色中的权限集合，并遍历
				List<Permission> permissionList = role.getPermissionList();
				if (permissionList != null && permissionList.size() > 0) {
					for (Permission permission : permissionList) {
						if (permission != null) {
							strPermissionList.add(permission.getName());
						}
					}
				}
			}
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(strRoleList);
		simpleAuthorizationInfo.addStringPermissions(strPermissionList);

		return simpleAuthorizationInfo;
	}

	/*
	 * 认证，用户登录的时候调用该方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		System.out.println("认证：AuthenticationInfo...");

		// 从token中获取用户信息，token代表用户输入
		String username = (String) token.getPrincipal();

		// 查找用户
		User user = userService.findAllUserInfoByUsername(username);

		// 获取密码
		String pwd = user.getPassword();
		if (pwd == null || "".equals(pwd)) {
			return null;
		}

		System.out.println("AuthenticationInfo...class name=" + this.getClass().getName());
		return new SimpleAuthenticationInfo(username, pwd, this.getClass().getName());
	}

}
