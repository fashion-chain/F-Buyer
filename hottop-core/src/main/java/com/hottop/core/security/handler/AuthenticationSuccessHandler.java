/**
 * 
 */
package com.hottop.core.security.handler;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.jwt.JwtUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 *认证成功时，执行返回
 *
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JwtUserDetailService jwtUserDetailService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功，认证成功");
		response.setContentType("com.hottop.application/json;charset=UTF-8");
		//返回身份认证信息
		//redis中生成token，返回
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Map tokenMap = jwtUserDetailService.saveUserLoginInfo(userDetails);
		logger.info("用户验证成功，用户名：{},token:{}", userDetails.getUsername(), tokenMap.get("token"));
		logger.info("登录成功，认证成功");
		response.setContentType("com.hottop.application/json;charset=UTF-8");
		//返回身份认证信息
		String resp = BaseConfiguration.generalGson().toJson(Response.ResponseBuilder
				.result(EResponseResult.OK).data(tokenMap).create());
		response.getWriter().write(resp);
	}

}
