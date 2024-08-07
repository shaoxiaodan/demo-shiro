package com.example.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.ini.IniSecurityManagerFactory;
import org.apache.shiro.lang.util.Factory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShiroTest {

	// Shiro（自带默认）最简单的实现Realm
	private SimpleAccountRealm accountRealm = new SimpleAccountRealm();

	// Shiro的安全认证管理器
	private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

	/*
	 * 初始化数据
	 */
	public void init() {
		System.out.println("init ...");

		// 注册用户信息,方式1(用户名+密码)
//		accountRealm.addAccount("aaa", "111");
//		accountRealm.addAccount("bbb", "222");
//		accountRealm.addAccount("ccc", "333");

		// 注册用户信息,方式2(用户名+密码+角色)
		accountRealm.addAccount("aaa", "111", "root", "admin"); // root=超级管理员，admin=普通管理员
		accountRealm.addAccount("bbb", "222", "user"); // user=用户
		accountRealm.addAccount("ccc", "333", "user");

		// 构建认证环境
		defaultSecurityManager.setRealm(accountRealm);
	}

	@Test
	public void testAuthentication() {
		System.out.println("testAuthentication ...");

		// 数据初始化
		init();

		// 环境初始化，设置上下文
		SecurityUtils.setSecurityManager(defaultSecurityManager);

		// 当前操作主题
		Subject subject = SecurityUtils.getSubject();

		// 构造(外部)的登录操作，提供用户名字+密码
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("aaa", "111");

		// 登录认证操作
		subject.login(usernamePasswordToken);

		// 判断认证结果
		System.out.println("login认证结果=" + subject.isAuthenticated());

		// 是否有对应角色
		System.out.println("是否有对应角色=" + subject.hasRole("root"));

		// 获取subject名称
		System.out.println("当前subject名称=" + subject.getPrincipal());

		// 检查是否有对应角色，没有返回值，直接在SecurityManager内部进行判断
		subject.checkRole("root");

		// 退出登录
		subject.logout();
		System.out.println("logout后的认证结果=" + subject.isAuthenticated());
	}


}
