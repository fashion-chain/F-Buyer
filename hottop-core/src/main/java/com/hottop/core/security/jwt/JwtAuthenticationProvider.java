package com.hottop.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.reflect.TypeToken;
import com.hottop.core.config.BaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * jwt token 请求 认证处理器
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JwtUserDetailService jwtUserDetailService;

	public void setJwtUserDetailService(JwtUserDetailService jwtUserDetailService) {
		this.jwtUserDetailService = jwtUserDetailService;
	}
	
	public JwtAuthenticationProvider() {

	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
		HashMap<String, String> map = new HashMap<>();
//		if(jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
//			logger.info("token 过期时间点：{},距离过期还有【{}】秒，用户名:{}", jwt.getExpiresAt(),
//					(jwt.getExpiresAt().getTime() - Calendar.getInstance().getTime().getTime())/1000 +"",
//					jwt.getSubject());
//			map.put("msg", "TokenExpire");
//			map.put("subject", jwt.getSubject());
//			throw new NonceExpiredException(BaseConfiguration.generalGson().toJson(map));
//		}
		String username = jwt.getSubject();
		UserDetails user = jwtUserDetailService.getUserLoginInfo(username);
		if(user == null || user.getPassword()==null){ //redis中数据过期
			map.put("msg", "TokenExpire");
			map.put("subject", jwt.getSubject());
			throw new NonceExpiredException(BaseConfiguration.generalGson().toJson(map));
		}
		String encryptSalt = user.getPassword();
		try {
			//加密的盐
            Algorithm algorithm = Algorithm.HMAC256(encryptSalt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(username)
                    .build();
            verifier.verify(jwt.getToken());
        } catch (Exception e) {
			//要求就算是token过期，token非法 都算过期异常
			map.put("msg", "refreshTokenExpire");
			map.put("subject", jwt.getSubject());
			throw new NonceExpiredException(BaseConfiguration.generalGson().toJson(map));
        }
        //认证通过
		Claim claim = jwt.getClaim(JwtUserDetailService.ROLE_CLAIMS);
		String s = claim.asString();
		ArrayList<SimpleGrantedAuthority> arrayList = BaseConfiguration.generalGson().fromJson(s, new TypeToken<List<SimpleGrantedAuthority>>(){}.getType());
		logger.info("jwt请求认证通过，用户权限如下：" );
		arrayList.stream().forEach(x -> logger.info(x.getAuthority()));
		//从jwt中加载权限
		JwtAuthenticationToken token = new JwtAuthenticationToken(user, jwt, arrayList);
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}

}
