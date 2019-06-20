package com.hottop.backstage.security.signup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 用户第三方登录之后，直接生成后台用户时启用
 */
@Component
public class AutoSignUp implements ConnectionSignUp {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String execute(Connection<?> connection) {
        logger.info("providerUserId:{}", connection.getKey().getProviderUserId());
        return connection.getKey().getProviderUserId();
    }
}
