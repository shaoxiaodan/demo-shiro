package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/*
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

	// 模拟数据库中的用户数据
	private final Map<String, String> userInfoMap = new HashMap<>();
	{
		userInfoMap.put("aaa", "111"); // 用户数据aaa
		userInfoMap.put("bbb", "222"); // 用户数据bbb
	}

	// 模拟数据库中(role - permission)，角色对应的权限
	private final Map<String, Set<String>> permissionMap = new HashMap<>();
	{
		// 用户权限
		Set<String> set1 = new HashSet<>();
		set1.add("video:find"); // 视频查找
		set1.add("video:buy"); // 视频购买

		// 管理员权限
		Set<String> set2 = new HashSet<>();
		set2.add("video:add"); // 视频添加
		set2.add("video:delete"); // 视频删除

		// 装配权限
		permissionMap.put("aaa", set1); // aaa，为普通用户角色
		permissionMap.put("bbb", set2); // bbb，为管理员角色
	}
	
	// 模拟数据库中(user - role)，用户对应的角色
		private final Map<String, Set<String>> roleMap = new HashMap<>();
		{
			// 用户角色
			Set<String> set1 = new HashSet<>();
			set1.add("role1");
			set1.add("role2");

			// 管理员角色
			Set<String> set2 = new HashSet<>();
			set2.add("root");

			// 装配角色
			permissionMap.put("aaa", set1); // aaa，为普通用户角色
			permissionMap.put("bbb", set2); // bbb，为管理员角色
		}

	/**
	 * 1，重写认证方法 当用户登录的时候，会调用该方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		System.out.println("认证：doGetAuthenticationInfo");

		// 从token中获取用户登录信息，token代表用户输入的信息
		String name = (String) token.getPrincipal(); // 用户名
//		String pwd = (String) token.getCredentials(); // 密码
		String pwd = getPwdByUsername(name); // 密码

		if (pwd == null || "".equals(pwd)) {
			return null;
		}

		// 构造SimpleAuthenticationInfo对象
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());

		// 返回SimpleAuthenticationInfo对象
		return simpleAuthenticationInfo;
	}

	/**
	 * 2，重写授权方法 进行权限校验的时候，会调用该方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		System.out.println("授权：doGetAuthorizationInfo");

		// 获取账号信息
		String name = (String) principals.getPrimaryPrincipal();

		// 从数据库中，模拟获取用户对应的权限集合
		Set<String> permissions = getPermissionsByNameFromDb(name);

		// 从数据库中，模拟获取用户对应的角色集合
		Set<String> roles = getRolesByNameFromDb(name);
		
		// 构建SimpleAuthorizationInfo对象
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(roles); // 设置角色
		simpleAuthorizationInfo.setStringPermissions(permissions); //设置权限

		// 返回SimpleAuthorizationInfo对象，完成Shiro基本的权限校验过程
		return simpleAuthorizationInfo;
	}

	// 根据用户名称，获取角色
	private Set<String> getRolesByNameFromDb(String name) {
		return this.roleMap.get(name);
	}

	// 根据用户名称，获取权限
	private Set<String> getPermissionsByNameFromDb(String name) {
		return this.permissionMap.get(name);
	}

	// 根据用户名称，获取密码
	private String getPwdByUsername(String name) {
		return this.userInfoMap.get(name);
	}

}
