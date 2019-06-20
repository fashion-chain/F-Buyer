package com.hottop.core.controller;

import com.hottop.core.config.SmsConfig;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.validate.code.sms.AliSmsCodeSender;
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.SmsUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 用户登录控制器
 */
@RestController
public class SmsController {

    private static Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AliSmsCodeSender aliSmsCodeSender;

    /**
     * 获取用户登录的验证码
     * 如果有，返回结果
     *
     * @param tel
     * @param type
     * @return
     */
    @RequestMapping(path = "/getSmsCode", method = RequestMethod.GET)
    public Response getLoginSmsCode(@RequestParam(value = "tel") String tel, @RequestParam(value = "type") String type) {
        ESmsCodeType[] values = ESmsCodeType.values();
        ESmsCodeType smsType = null;
        for (ESmsCodeType eSmsCodeType : values) {
            if (eSmsCodeType.name().equalsIgnoreCase(type)) {
                smsType = eSmsCodeType;
                break;
            }
        }
        if (smsType == null) {
            logger.info("发送验证码-没有这种类型的短信发送-type:{}", type);
            return ResponseUtil.createErrorResponse(EResponseResult.SMS_ERROR_SEND);
        }
        String redis_key = aliSmsCodeSender.getRedisKey(tel, smsType);
        Boolean hasKey = redisTemplate.hasKey(redis_key);
        logger.info("redis是否含有这个手机号:{},短信类型：{}", hasKey, smsType);
        if (hasKey && isTimeLessThanSmsInterval(redis_key)) {
            String smsCode = (String) redisTemplate.opsForValue().get(redis_key);
            return ResponseUtil.createErrorResponse(EResponseResult.SMS_ERROR_TimeTooShort);
        } else { //发送短信验证码
            String smsCode = SmsUtil.getMsgCode();
            logger.info("发送短信验证码为：{}", smsCode);
            aliSmsCodeSender.send(tel, smsCode, smsType);
            return ResponseUtil.createOKResponse(EResponseResult.SMS_SUCCESS_SEND);
        }
    }


    /**
     * true 表示时间太短不可发送，false 可以发送
     * @param redis_key
     * @return
     */
    private boolean isTimeLessThanSmsInterval (String redis_key) {
        Long expire = redisTemplate.getExpire(redis_key, TimeUnit.SECONDS);
        Long smsCodeExpireTime = StringUtils.isNotBlank(SmsConfig.smsCodeExpireTime) ? Long.parseLong(SmsConfig.smsCodeExpireTime) : 300l;
        Long smsCodeSendInterval = StringUtils.isNotBlank(SmsConfig.smsCodeSendInterval) ? Long.parseLong(SmsConfig.smsCodeSendInterval)
                : 60l;
        logger.info("redis_key:{},剩余时间:{} 秒,是否时间间隔太短：{}", redis_key, expire, expire > (smsCodeExpireTime - smsCodeSendInterval));
        return expire > (smsCodeExpireTime - smsCodeSendInterval);
    }


}
