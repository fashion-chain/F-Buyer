package com.hottop.backstage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    }
}
