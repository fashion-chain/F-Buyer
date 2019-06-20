package com.hottop.core.security.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hottop.core.security.jwt.JwtAuthenticationToken;
import com.hottop.core.security.jwt.JwtUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component("jwtRefreshSuccessHandler")
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final int tokenRefreshInterval = 20;  //刷新间隔5分钟

	private JwtUserDetailService jwtUserDetailService;
	
	public JwtRefreshSuccessHandler(JwtUserDetailService jwtUserDetailService) {
		this.jwtUserDetailService = jwtUserDetailService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
		DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
		boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
		logger.info("token请求验证成功,是否需要刷新token：{}",shouldRefresh);
		if(shouldRefresh) {
            /*String newToken = jwtUserDetailService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
			response.getWriter().write(BaseConfiguration.generalGson().toJson(
					Response.ResponseBuilder.result(EResponseResult.JWT_TOKEN_EXPIRE).data(newToken).create()
			));
			return;*/
        }	
	}
	
	protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

}
