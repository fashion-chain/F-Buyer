package com.hottop.core.security.form;

import com.hottop.core.security.handler.AuthenticationFailureHandler;
import com.hottop.core.security.handler.AuthenticationSuccessHandler;
import com.hottop.core.security.jwt.JwtUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("formLoginApiConfig")
public class FormLoginApiConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        FormAuthenticationApiFilter formAuthenticationFilter = new FormAuthenticationApiFilter();
        formAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        formAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        formAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        DaoAuthenticationProvider formAuthenticationProvider = new DaoAuthenticationProvider();
        formAuthenticationProvider.setUserDetailsService(jwtUserDetailService);
        formAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        http.authenticationProvider(formAuthenticationProvider)
                .addFilterAfter(formAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
