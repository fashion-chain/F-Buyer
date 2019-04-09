package com.hottop.api.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HottopApiConfiguration {
    @Bean
    @Autowired
    public Gson applicationGson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }
}
