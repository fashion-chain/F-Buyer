package com.hottop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
        @ComponentScan(basePackages = {
                "com.hottop.api",
                "com.hottop.core"
        })
})
public class HottopApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HottopApiApplication.class, args);
    }
}
