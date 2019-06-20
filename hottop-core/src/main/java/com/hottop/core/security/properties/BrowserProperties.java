/**
 * 
 */
package com.hottop.core.security.properties;

import lombok.Data;

/**
 *
 *
 */
@Data
public class BrowserProperties {
	
	private SessionProperties session = new SessionProperties();
	
	private String signUpUrl = "/signUp.html";
	
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	private LoginResponseType loginType = LoginResponseType.JSON;
	
	private int rememberMeSeconds = 3600;

	private String jwtExpireTime = 3600 + "";//默认一个小时过期

	private String jwtRefreshTime = 3600*24*7 + "";//默认刷新时间 1周


	
}
