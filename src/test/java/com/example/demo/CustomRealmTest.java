package com.example.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomRealmTest {

	private CustomRealm customRealm = new CustomRealm();

	private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

	// 初始化方法
	private void init() {
		// 构建认证环境
		defaultSecurityManager.setRealm(customRealm);
		SecurityUtils.setSecurityManager(defaultSecurityManager);
	}

	@Test
	public void testAuthentication() {

		// 初始化数据
		init();
		
		// 获取当前操作主题
		Subject subject = SecurityUtils.getSubject();
		
		// 用户名称 + 密码
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("aaa", "111");
		
		// 进行登录验证操作
		subject.login(usernamePasswordToken);
		
		// 验证结果
		System.out.println("验证结果=" + subject.isAuthenticated());
		
		// 主题标识属性
		System.out.println("主题标识=" + subject.getPrincipal());
		
		// 判断角色
//		subject.checkRole("root"); 
//		subject.checkRole("role1");
		
//		System.out.println("判断授权,root=" + subject.hasRole("root"));
//		System.out.println("判断授权,role1=" + subject.hasRole("role1"));
		System.out.println("判断授权,role2=" + subject.hasRole("role2"));
		
		// 判断权限
		System.out.println("判断权限,video:find=" + subject.isPermitted("video:find"));
		
	}

}
