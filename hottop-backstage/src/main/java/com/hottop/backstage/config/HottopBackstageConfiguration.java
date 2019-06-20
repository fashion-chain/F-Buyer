package com.hottop.backstage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.boot.env.YamlPropertySourceLoader;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
<<<<<<< HEAD
=======
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePropertySource;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
<<<<<<< HEAD

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HottopBackstageConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .httpBasic();
=======
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class HottopBackstageConfiguration implements WebMvcConfigurer {

    @Autowired
    public void apiMessageSource(ReloadableResourceBundleMessageSource messageSource) {
        messageSource.addBasenames("classpath:i18n/back");
    }

    @Autowired
    public void apiPropertySource(MutablePropertySources propertySources) throws Exception {
        YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> sources = sourceLoader.load("application", new ClassPathResource("application.yml"));
        for (PropertySource<?> propertySource: sources) {
            propertySources.addFirst(propertySource);
        }
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }
}
