package com.hottop.api.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hottop.core.config.BaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class HottopApiConfiguration implements WebMvcConfigurer {

    private Gson apiGson() {
        GsonBuilder gsonBuilder = (GsonBuilder) BaseConfiguration.getBean("gsonBuilder");
        return gsonBuilder
                .create();
    }

    @Autowired
    public void apiMessageSource(ReloadableResourceBundleMessageSource messageSource) {
        messageSource.addBasenames("classpath:i18n/api");
    }

    @Autowired
    public void apiPropertySource(MutablePropertySources propertySources) throws Exception {
        propertySources.addFirst(new ResourcePropertySource("api.properties"));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(apiGson());
        gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converters.add(0, gsonHttpMessageConverter);
    }
}
