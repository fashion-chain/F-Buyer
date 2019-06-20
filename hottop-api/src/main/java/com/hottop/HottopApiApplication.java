package com.hottop;

import org.springframework.boot.SpringApplication;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.SpringBootApplication;
=======
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
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
<<<<<<< HEAD
=======
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
public class HottopApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HottopApiApplication.class, args);
    }
}
