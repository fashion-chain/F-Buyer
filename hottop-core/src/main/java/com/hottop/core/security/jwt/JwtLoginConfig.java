package com.hottop.core.security.jwt;

import com.hottop.core.security.handler.AuthenticationFailureHandler;
import com.hottop.core.security.handler.JwtRefreshSuccessHandler;
import com.hottop.core.security.jwt.access.JwtFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

@Component("jwtLoginConfig")
public class JwtLoginConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private JwtRefreshSuccessHandler jwtRefreshSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private JwtFilterSecurityInterceptor jwtFilterSecurityInterceptor;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtRefreshSuccessHandler);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        jwtAuthenticationFilter.setPermissiveUrl("/logout");

        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        jwtAuthenticationProvider.setJwtUserDetailService(jwtUserDetailService);


        http.authenticationProvider(jwtAuthenticationProvider)
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);

        //http.addFilterBefore(jwtFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
}
