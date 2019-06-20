package com.hottop.core.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CommonConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private static Boolean cmsAdviceDefaultEnabled;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        switch (BaseConfiguration.getProperty("cms.advice.default")) {
//            case "extractor":
//                cmsAdviceDefaultEnabled = true;
//                break;
//        }
    }

    public static boolean cmsAdviceEnabled() {
        return cmsAdviceDefaultEnabled;
    }
}
