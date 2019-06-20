package com.hottop.api.security;

import com.hottop.api.security.filter.OptionsRequestFilter;
import com.hottop.core.model.user.User;
import com.hottop.core.security.AbstractChannelSecurityConfig;
import com.hottop.core.security.form.FormLoginApiConfig;
import com.hottop.core.security.handler.AuthenticationFailureHandler;
import com.hottop.core.security.handler.AuthenticationSuccessHandler;
import com.hottop.core.security.handler.TokenClearLogoutHandler;
import com.hottop.core.security.jwt.JwtLoginConfig;
import com.hottop.core.security.jwt.JwtUserDetailService;
import com.hottop.core.security.mobile.SmsCodeAuthenticationSecurityConfig;
import com.hottop.core.security.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
public class ApiSecurityConfig extends AbstractChannelSecurityConfig {

	@Autowired
	private TokenClearLogoutHandler tokenClearLogoutHandler;
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	@Autowired
	private FormLoginApiConfig formLoginApiConfig;
	@Autowired
	private JwtLoginConfig jwtLoginConfig;


	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/getSms*").permitAll()
				.antMatchers("/authentication/mobile").permitAll() //短信登录放行
				.antMatchers("/provinceCity/*").permitAll() //省市区查询放行
				.antMatchers("/user/*").permitAll()
		        .antMatchers("/image/**").permitAll()
				.antMatchers("/trade/**").permitAll()
		        .antMatchers("/admin/**").hasAnyRole("ADMIN")
		        .antMatchers("/article/**").hasRole("USER")
		        .anyRequest().permitAll().and()
		    .csrf().disable()
		    .formLogin().disable()
		    .sessionManagement().disable()
		    .cors().and()
		    .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
		    		new Header("Access-Control-Allow-Origin","*"),
		    		new Header("Access-Control-Expose-Headers","Authorization")))).and()
		    .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
			.apply(validateCodeSecurityConfig).and()
			.apply(smsCodeAuthenticationSecurityConfig).and()
		    .apply(formLoginApiConfig).and()
		    .apply(jwtLoginConfig).and()
			//.apply(validateCodeSecurityConfig).and()
		    .logout()
//		        .logoutUrl("/logout")   //默认就是"/logout"
		        .addLogoutHandler(tokenClearLogoutHandler)
		        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).and()
		    .sessionManagement().disable();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Bean
	public JwtUserDetailService jwtUserDetailService(){
		JwtUserDetailService<User> userJwtUserDetailService = new JwtUserDetailService<>();
		userJwtUserDetailService.setT(new User());
		return userJwtUserDetailService;
	}


	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "OPTION"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
