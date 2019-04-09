package com.hottop.backstage.user.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.hottop.backstage.product.service.CommerceBrandService;
import com.hottop.backstage.user.service.UserService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.SmsConfig;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.user.User;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController extends EntityBaseController<User> {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    public Class<User> clazz() {
        return User.class;
    }

    @Override
    public EntityBaseService service() {
        return userService;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取用户登录的验证码
     * 如果有，返回结果
     * 如果没有，给用户发送短信，并返回结果
     *
     * @param tel
     * @return
     */
    @RequestMapping(path = "/getLoginSmsCode", method = RequestMethod.GET)
    public Response getLoginSmsCode(@RequestParam(value = "tel") String tel) {
        String redis_key = tel + SmsUtil.ESmsCodeType.Login;
        Boolean hasKey = redisTemplate.hasKey(redis_key);
        logger.info(String.format("redis是否含有这个手机号（login type）:%s",hasKey));
        if (hasKey) {
            String smsCode = (String) redisTemplate.opsForValue().get(redis_key);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(smsCode).create();
        } else {
            String smsCode = SmsUtil.getMsgCode();
            //发送验证码
            HashMap<String, String> smsCodeMap = new HashMap<>();
            smsCodeMap.put("code", smsCode);
            boolean sendResult = SmsUtil.send(tel, SmsUtil.ALI_SMS_SIGN_FSYM,
                    SmsUtil.ALI_SMS_TEMPLATE_SMS_LOGIN, BaseConfiguration.generalGson().toJson(smsCodeMap));
            //阿里发送短信失败
            if( !sendResult ) return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).create();
            //发送短信成功
            //设置过期时间
            redisTemplate.opsForValue().set(redis_key, smsCode);
            redisTemplate.expire(redis_key, Long.parseLong(SmsConfig.smsCodeExpireTime), TimeUnit.SECONDS);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(smsCode).create();
        }
    }

    /**
     * 获取用户注册的验证码
     * 如果有，返回结果
     * 如果没有，给用户发送短信，并返回结果
     *
     * @param tel
     * @return
     */
    @RequestMapping(path = "/getRegisterSmsCode", method = RequestMethod.GET)
    public Response getRegisterSmsCode(@RequestParam(value = "tel") String tel) {
        String redis_key = tel + SmsUtil.ESmsCodeType.Registration;
        Boolean hasKey = redisTemplate.hasKey(redis_key);
        logger.info(String.format("redis是否含有这个手机号（login type）:%s",hasKey));
        if (hasKey) {
            String smsCode = (String) redisTemplate.opsForValue().get(redis_key);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(smsCode).create();
        } else {
            String smsCode = SmsUtil.getMsgCode();
            //发送验证码
            HashMap<String, String> smsCodeMap = new HashMap<>();
            smsCodeMap.put("code", smsCode);
            boolean sendResult = SmsUtil.send(tel, SmsUtil.ALI_SMS_SIGN_FSYM,
                    SmsUtil.ALI_SMS_TEMPLATE_USER_REGISTER, BaseConfiguration.generalGson().toJson(smsCodeMap));
            //阿里发送短信失败
            if( !sendResult ) return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).create();
            //发送短信成功
            //设置过期时间
            redisTemplate.opsForValue().set(redis_key, smsCode);
            redisTemplate.expire(redis_key, Long.parseLong(SmsConfig.smsCodeExpireTime), TimeUnit.SECONDS);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(smsCode).create();
        }
    }

    /**
     * 判断电话号码是否已存在
     *
     * @param tel
     * @return false表示不存在，true表示存在
     */
    @RequestMapping(path = "/telExists", method = RequestMethod.GET)
    public Response telExists(@RequestParam(value = "tel") String tel) {
       boolean result = userService.telExists(tel);
        return Response.ResponseBuilder.result(EResponseResult.OK).data(result).create();
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody User user, @RequestParam("verifyCode") String verifyCode){

        return null;
    }
}
