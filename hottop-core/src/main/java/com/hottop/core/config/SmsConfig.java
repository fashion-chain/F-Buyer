package com.hottop.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 短信发送配置类
 */
@Component
public class SmsConfig {

    public static String smsCodeExpireTime;

    /**
     * set 方法需要把 static去掉
     * @param smsCodeExpireTime
     */
    @Value("${sms.code.expire.time}")
    public void setSmsCodeExpireTime(String smsCodeExpireTime) {
        SmsConfig.smsCodeExpireTime = smsCodeExpireTime;
    }

    public SmsConfig() {
    }

}
