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
		
		
	}

}
