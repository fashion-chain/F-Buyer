package com.hottop.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 短信发送配置类
 */
@Component
@PropertySource("classpath:business.properties")
public class SmsConfig {

    /**
     * 验证码失效时间5分钟
     */
    public static String smsCodeExpireTime;

    /**
     * set 方法需要把 static去掉
     * @param smsCodeExpireTime
     */
    @Value("${sms.code.expire.time}")
    public void setSmsCodeExpireTime(String smsCodeExpireTime) {
        SmsConfig.smsCodeExpireTime = smsCodeExpireTime;
    }

    public static String smsCodeLoginExpireTime;

    @Value("${sms.code.login.expire.time}")
    public void setSmsCodeLoginExpireTime(String smsCodeLoginExpireTime) {
        SmsConfig.smsCodeLoginExpireTime = smsCodeLoginExpireTime;
    }

    public SmsConfig() {
    }

    public static String smsCodeSendInterval;
    @Value("${sms.code.send.interval.time}")
    public void setSmsCodeSendInterval(String smsCodeSendInterval) {
        SmsConfig.smsCodeSendInterval = smsCodeSendInterval;
    }
}
