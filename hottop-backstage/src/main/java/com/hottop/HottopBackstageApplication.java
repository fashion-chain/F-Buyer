package com.hottop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScans({
        @ComponentScan(basePackages = {
                "com.hottop.backstage",
                "com.hottop.core"
        })
})
public class HottopBackstageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HottopBackstageApplication.class, args);
    }
}
