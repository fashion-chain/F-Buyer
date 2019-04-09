package com.hottop.backstage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hottop.core.config.BaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    private Gson backstageGson() {
        GsonBuilder gsonBuilder = (GsonBuilder) BaseConfiguration.getBean("gsonBuilder");
        return gsonBuilder
                .create();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(backstageGson());
        gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converters.add(0, gsonHttpMessageConverter);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .host(BaseConfiguration.getHost(contextPath, port))
//                .pathProvider(new RelativePathProvider(servletContext) {
//                    @Override
//                    public String getApplicationBasePath() {
//                        if (BaseConfiguration.isDeploy()) {
//                            return "/backstage" + contextPath;
//                        } else {
//                            return contextPath;
//                        }
//                    }
//                })
//                .pathMapping("/")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.hottop"))
                    .paths(PathSelectors.any())
                    .build()
                .pathMapping("/")
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Hottop backstage API",
                "backstage API swagger2 version",
                "API 100",
                "Terms of service",
                new Contact("manager", "fashionet.io", "manager@fsym.tech"),
                "License of API", "API license URL",
                Collections.emptyList()
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
