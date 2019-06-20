package com.hottop.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
=======
import org.springframework.context.annotation.PropertySource;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import org.springframework.stereotype.Component;

/**
 * 短信发送配置类
 */
@Component
<<<<<<< HEAD
public class SmsConfig {

=======
@PropertySource("classpath:business.properties")
public class SmsConfig {

    /**
     * 验证码失效时间5分钟
     */
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    public static String smsCodeExpireTime;

    /**
     * set 方法需要把 static去掉
     * @param smsCodeExpireTime
     */
    @Value("${sms.code.expire.time}")
    public void setSmsCodeExpireTime(String smsCodeExpireTime) {
        SmsConfig.smsCodeExpireTime = smsCodeExpireTime;
    }

<<<<<<< HEAD
    public SmsConfig() {
    }

=======
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
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
