package com.hottop.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ht.snowflake")
@Data
public class SnowFlakeWorkerProperties {

    private Long workerId;

    private Long datacenterId;
}
