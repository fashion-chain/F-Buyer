package com.hottop.core.security.form;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class FormAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 是否开启验证码功能
	private boolean isOpenValidateCode = true;


    //拦截此url的登录form请求
	public FormAuthenticationFilter() {
		super(new AntPathRequestMatcher("/authentication/form", "POST"));
	}
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
		Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
		Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
	}


	//获得用户名密码组装authentication token
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
		logger.info("登录body：\n{}", body);
		String username = null, password = null;
		if(StringUtils.hasText(body)) {
			JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
			username = jsonObject.get("username").getAsString();
			password = jsonObject.get("password").getAsString();
		}
		if (username == null)
			username = "";
		if (password == null)
			password = "";
		username = username.trim();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		return this.getAuthenticationManager().authenticate(token);
	}




}
