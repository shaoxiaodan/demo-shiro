package com.example.demo.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	/**
	 * Shiro过滤器工厂
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
		System.out.println("ShiroConfig::ShiroFilterFactoryBean...");

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 需要登录的接口，如果访问某个接口，需要登录却没有登录，则调用此接口
		// 如果不是前后端分离，则跳转页面
		shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

		// 登录成果，跳转url
		// 如果前后端分离，则不会调用该方法
		shiroFilterFactoryBean.setSuccessUrl("/");

		// 没有权限，未授权就会调用此方法，先验证登录 -》 在验证是否有权限
		shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");

		/*
		 * 拦截器路径，有先后顺序，必须以LinkedHashMap来装配，不能使用HashMap
		 */
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		// 退出过滤器（路径，退出过滤器）
		filterChainDefinitionMap.put("/logout", "logout");

		// 匿名可访问，也就是游客模式（路径，匿名过滤器）
		filterChainDefinitionMap.put("/pub/**", "anon");

		// 登录用户才可以访问（路径，登录过滤器）
		filterChainDefinitionMap.put("/authc/**", "authc");

		// 管理员角色才可以访问
		filterChainDefinitionMap.put("/admin/**", "roles[admin]");

		// 编辑权限才可以访问
		filterChainDefinitionMap.put("/video/update", "perms[video_update]");

		/*
		 * 注意：过滤链是顺序执行，从上而下，一般将/**放到最后
		 */
		// authc: url定义必须通过认证才可以访问
		// anon: url可以匿名访问
		filterChainDefinitionMap.put("/**", "authc");

		// 设置拦截器的过滤链
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 安全认证器
	 * 
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customRealm());
		
		// 如果不是前后端分离，则不需要设置以下的sessionManager
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public CustomRealm customRealm() {
		CustomRealm customRealm = new CustomRealm();
		customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return customRealm;
	}

	/**
	 * 密码验证器（加密算法）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		// 设置散列算法：这里使用MD5加密算法
		hashedCredentialsMatcher.setHashAlgorithmName("md5");

		// 设置散列次数：这里进行散列2次，即相当于md5(md5(xxx))
		hashedCredentialsMatcher.setHashIterations(2);

		return hashedCredentialsMatcher;
	}

	/**
	 * 会话管理器
	 * 前后端分离模式，必须要配置session manager
	 * @return
	 */
	@Bean
	public SessionManager sessionManager() {
		CustomSessionManager customSessionManager = new CustomSessionManager();
		customSessionManager.setGlobalSessionTimeout(20000); // 会话过期时间，	毫秒
		return customSessionManager;
	}

}
