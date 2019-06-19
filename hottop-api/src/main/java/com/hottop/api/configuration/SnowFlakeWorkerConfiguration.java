package com.hottop.api.configuration;

import com.hottop.core.utils.SnowflakeIdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SnowFlakeWorkerProperties.class)
public class SnowFlakeWorkerConfiguration {

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(SnowFlakeWorkerProperties prop) {
        return new SnowflakeIdWorker(prop.getWorkerId(), prop.getDatacenterId());
    }
}
