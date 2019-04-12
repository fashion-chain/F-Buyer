package com.hottop.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.commerce.CommerceBrand_;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.zpoj.adapter.JsonDeserializationWithOptions;
import com.hottop.core.model.zpoj.adapter.SpringfoxJsonToGsonAdapter;
import com.hottop.core.model.zpoj.adapter.serializer.*;
import com.hottop.core.model.zpoj.cms.*;
import com.hottop.core.model.zpoj.cms.field.FieldBase;
import com.hottop.core.model.zpoj.cms.field.FieldTemplate;
import com.hottop.core.model.zpoj.cms.template.ComponentTemplate;
import com.hottop.core.request.argument.base.BaseParamArgumentResolver;
import com.hottop.core.request.argument.filter.FilterArgumentResolver;
import com.hottop.core.request.argument.flag.FlagArgumentResolver;
import com.hottop.core.request.argument.view.DataView;
import com.hottop.core.request.argument.view.DataViewRegistration;
import com.hottop.core.response.exclusion.BaseExclusionStrategyFactory;
import com.hottop.core.trading.TradeFactory;
import com.hottop.core.utils.RsaUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.*;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
public class BaseConfiguration implements ApplicationContextAware, WebMvcConfigurer {
    private static Gson generalGson = new Gson();

    private static ApplicationContext context;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new FlagArgumentResolver());
        argumentResolvers.add(new FilterArgumentResolver());
        argumentResolvers.add(new BaseParamArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new VersionInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new BaseParamsInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.addBasenames("classpath:i18n/base");
        return messageSource;
    }

    @Bean
    public MutablePropertySources propertySources() throws Exception {
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast(new ResourcePropertySource("business.properties"));
        propertySources.addLast(new ResourcePropertySource("trade.properties"));
        return propertySources;
    }

    @Bean
    public PropertyResolver propertyResolver(MutablePropertySources propertySources) {
        return new PropertySourcesPropertyResolver(propertySources);
    }

    @Bean
    public RsaUtil rsaUtil() {
        return new RsaUtil();
    }

    public static String getProperty(String code) {
        return ((PropertyResolver) getBean("propertyResolver")).getProperty(code);
    }

    public static String getMessage(String code, Object... objs) {
        String codeString;
        try {
            codeString = ((MessageSource) getBean("messageSource")).getMessage(code, objs, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            codeString = String.format("cannot find corresponding i18n message: %s", code);
        }
        return codeString;
    }

    @Bean
    public GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter())
                .registerTypeAdapter(IComponent.class, new ComponentSerializer())
                .registerTypeAdapter(IAction.class, new ActionSerializer())
                .registerTypeAdapter(IWidget.class, new WidgetSerializer())
                .registerTypeAdapter(ITemplate.class, new TemplateSerializer())
                .registerTypeAdapter(IWrapper.class, new WrapperSerializer())
                .registerTypeAdapter(FieldBase.class, new FieldSerializer())
                .registerTypeAdapter(FieldTemplate.class, new JsonDeserializationWithOptions<FieldTemplate>())
                .registerTypeAdapter(ComponentTemplate.class, new JsonDeserializationWithOptions<ComponentTemplate>());
    }

    @Bean
    public DataViewRegistration dataViewRegistration() throws Exception {
        DataViewRegistration dataViewRegistration = new DataViewRegistration();
        dataViewRegistration
                .clazz(CommerceBrand.class)
                    .addView("list", DataView.create(CommerceBrand_.name, CommerceBrand_.country, CommerceBrand_.description))
                .and().clazz(CommerceSpu.class);
        return dataViewRegistration;
    }

    @Bean
    public TradeFactory tradeFactory() {
        return new TradeFactory();
    }

    public static String getActiveProfile() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        return profiles[profiles.length-1];
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Gson generalGson() {
        return generalGson;
    }
}
