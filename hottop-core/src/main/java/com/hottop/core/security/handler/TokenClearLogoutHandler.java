package com.hottop.core.security.handler;

import com.hottop.core.security.jwt.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenClearLogoutHandler implements LogoutHandler {

	@Autowired
	private JwtUserDetailService jwtUserDetailService;
	
	public TokenClearLogoutHandler(JwtUserDetailService jwtUserService) {
		this.jwtUserDetailService = jwtUserService;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		clearToken(authentication);
	}
	
	protected void clearToken(Authentication authentication) {
		if(authentication == null)
			return;
		UserDetails user = (UserDetails)authentication.getPrincipal();
		if(user!=null && user.getUsername()!=null)
			jwtUserDetailService.deleteUserLoginInfo(user.getUsername());
	}

}
