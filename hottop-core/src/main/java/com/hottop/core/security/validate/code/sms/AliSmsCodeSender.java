package com.hottop.core.security.validate.code.sms;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.config.SmsConfig;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.security.properties.SmsCodeProperties;
import com.hottop.core.security.validate.code.ValidateCode;
import com.hottop.core.utils.SmsUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.patterns.ScopeWithTypeVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component("aliSmsCodeSender")
public class AliSmsCodeSender implements SmsCodeSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void send(String mobile, String code, ESmsCodeType eSmsCodeType) {
        SmsCodeProperties smsCodeProperties = securityProperties.getCode().getSms();
        Map map = new HashMap<String, String>();
        map.put("code", code);
        String jsonStr = BaseConfiguration.generalGson().toJson(map);
        String template = getTemplate(eSmsCodeType);
        String expireTime = StringUtils.isBlank(SmsConfig.smsCodeExpireTime) ? "300" : SmsConfig.smsCodeExpireTime;
        String redis_key = getRedisKey(mobile, eSmsCodeType);
        if (StringUtils.isNotBlank(template)) {
            boolean sendResult = true;
            if (smsCodeProperties.isAliSmsOpen()) { //是否真实发送短信
                sendResult = SmsUtil.send(mobile, smsCodeProperties.getCurrentCompanyName(), template, jsonStr);
            }
            if (sendResult) {  //确认短信发送成功后，存redis
                ValidateCode validateCode = new ValidateCode(code, Integer.parseInt(SmsConfig.smsCodeExpireTime));
                redisTemplate.opsForValue().set(redis_key, BaseConfiguration.generalGson().toJson(validateCode));
                redisTemplate.expire(redis_key, Long.parseLong(expireTime), TimeUnit.SECONDS);
            }else {
                logger.info("阿里-发送短信失败");
            }
        }
    }

    public String getRedisKey(String mobile, ESmsCodeType eSmsCodeType) {
        String prefix = null;
        switch (eSmsCodeType) {
            case Login:
                prefix = RedisConfig.Prefix_user_verifyCode_login;
                break;
            case Register:
                prefix = RedisConfig.Prefix_user_verifyCode_register;
                break;
            case ResetPassword:
                prefix = RedisConfig.Prefix_user_verifyCode_changePassword;
                break;
        }
        return prefix + mobile;
    }

    public static String getTemplate(ESmsCodeType eSmsCodeType) {
        String template = null;
        switch (eSmsCodeType) {
            case Login:
                template = SmsUtil.ALI_SMS_TEMPLATE_SMS_LOGIN;
                break;
            case Register:
                template = SmsUtil.ALI_SMS_TEMPLATE_USER_REGISTER;
                break;
            case ResetPassword:
                template = SmsUtil.ALI_SMS_TEMPLATE_CHANGE_PASS;
                break;
        }
        return template;
    }

}
