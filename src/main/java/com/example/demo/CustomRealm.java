package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/*
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

	// 模拟数据库中的用户数据
	private final Map<String, String> userInfoMap = new HashMap<>();

	// 初始化
	{
		userInfoMap.put("aaa", "111");
		userInfoMap.put("bbb", "222");
		userInfoMap.put("ccc", "333");
	}

	/*
	 * 重写授权方法 进行权限校验的时候，会调用该方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	/*
	 * 重写认证方法 当用户登录的时候，会调用该方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		System.out.println("认证：AuthenticationInfo");

		// 从token中获取用户登录信息，token代表用户输入的信息
		String name = (String) token.getPrincipal(); // 用户名
//		String pwd = (String) token.getCredentials(); // 密码
		String pwd = getPwdByUsername(name); // 密码

		if(pwd == null || "".equals(pwd)) {
			return null;
		}
		
		// 构造SimpleAuthenticationInfo对象
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());
		
		// 返回SimpleAuthenticationInfo对象
		return simpleAuthenticationInfo;
	}

	private String getPwdByUsername(String name) {
		return userInfoMap.get(name);
	}

}
