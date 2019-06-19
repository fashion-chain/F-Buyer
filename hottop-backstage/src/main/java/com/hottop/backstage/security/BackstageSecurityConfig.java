/**
 * 
 */
package com.hottop.backstage.security;

import com.hottop.core.model.user.Application;
import com.hottop.core.security.AbstractChannelSecurityConfig;
import com.hottop.core.security.form.FormLoginConfig;
import com.hottop.core.security.handler.JwtAccessDeniedHandler;
import com.hottop.core.security.handler.TokenClearLogoutHandler;
import com.hottop.core.security.jwt.access.JwtAccessDeniedManager;
import com.hottop.core.security.jwt.JwtLoginConfig;
import com.hottop.core.security.jwt.JwtUserDetailService;
import com.hottop.core.security.mobile.SmsCodeAuthenticationSecurityConfig;
import com.hottop.core.security.properties.SecurityConstants;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.security.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


/**
 *
 * spring security 配置类
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class BackstageSecurityConfig extends AbstractChannelSecurityConfig {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Autowired
	private TokenClearLogoutHandler tokenClearLogoutHandler;

	@Autowired
	private JwtLoginConfig jwtLoginConfig;

	@Autowired
	private FormLoginConfig formLoginConfig;
	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	@Autowired
	private JwtAccessDeniedManager jwtAccessDeniedManager;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers(
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
					securityProperties.getBrowser().getLoginPage(),
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
					"/user/regist",
					"/imageUpload",
					"/getSmsCode"
						)
					.permitAll()
				//.anyRequest().permitAll()
				.anyRequest().authenticated()

				.and()
				.apply(validateCodeSecurityConfig).and()
				.apply(smsCodeAuthenticationSecurityConfig).and()
				.apply(formLoginConfig).and()
				.apply(jwtLoginConfig).and()
				.logout()
//		        .logoutUrl("/logout")   //默认就是"/logout"
				.addLogoutHandler(tokenClearLogoutHandler)
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
				.and()
			.csrf().disable()

		;
		//访问决策管理器
		http.authorizeRequests().accessDecisionManager(jwtAccessDeniedManager);
		//处理无权限handler
		http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);
		//
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}

	@Bean
	public JwtUserDetailService jwtUserDetailService() {
		JwtUserDetailService bgUserJwtUserDetailService = new JwtUserDetailService();
		bgUserJwtUserDetailService.setT(new Application());
		return bgUserJwtUserDetailService;
	}


}
