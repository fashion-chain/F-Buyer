package com.hottop.core.security.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

//spring security 工具类
public class SecurityUtil {

    private final static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static UserDetails getCurrentUserDetail() {
        Object principal = null;
        try {
            principal = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            principal = (String) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            logger.info("当前用户没有登录，登录主体为：{}", principal);
        }
        return null;
    }
}
