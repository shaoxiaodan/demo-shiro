package com.example.demo.config;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

/**
 * 前后端分离，必须采用以下基于Token的令牌交互
 * 
 * @ClassName: CustomSessionManager
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-08 04:14:17
 */
public class CustomSessionManager extends DefaultWebSessionManager {

	private static final String AUTHORIZATION = "token";

	public CustomSessionManager() {
		super();
	}

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

		String sessioinId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);

		if (sessioinId != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
					ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);

			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessioinId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessioinId;
		} else {
			return super.getSessionId(request, response);
		}
	}

}
