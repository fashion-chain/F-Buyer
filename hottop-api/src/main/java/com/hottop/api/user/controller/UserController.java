package com.hottop.api.user.controller;

import com.hottop.api.user.service.UserService;
import com.hottop.api.user.vo.UserDto;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.user.User;
import com.hottop.core.model.user.validator.UserRegisterValidator;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.validate.code.ValidateCode;
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import com.hottop.core.security.validate.code.sms.SmsCodeSender;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 判断用户表中的电话号码是否已存在
     *
     * @param tel
     * @return false表示不存在，true表示存在
     */
    @RequestMapping(path = "/telExists", method = RequestMethod.GET)
    public Response telExists(@RequestParam(value = "tel") String tel) {
        boolean result = userService.telExists(tel);
        return Response.ResponseBuilder.result(EResponseResult.OK).data(result).create();
    }

    /**
     * 用户注册
     * @return Response标准回复
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody UserDto userDto) {
        String tel = userDto.getTel();
        String password = userDto.getPassword();
        String verifyCode = userDto.getVerifyCode();
        if (!ValidatorUtil.ValidatePhone(tel)) {
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_PHONE);
        }
        if (!ValidatorUtil.ValidatePassword(password)) {
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_PASSWORD);
        }
        if (userService.telExists(tel)) { //手机号码已存在
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_REGISTER_HAS_REGISTER);
        }
        //对比verifyCode 验证码 (String) redisTemplate.opsForValue().get(redis_key);
        String redis_key = smsCodeSender.getRedisKey(tel, ESmsCodeType.Register);
        ValidateCode validateCode = BaseConfiguration.generalGson().fromJson((String)redisTemplate.opsForValue().get(redis_key), ValidateCode.class);
        String redis_validateCode = validateCode == null ? "" : validateCode.getCode();
        logger.info("redis-repo验证码:{},用户验证码:{},是否相等:{}", redis_validateCode, verifyCode, verifyCode.equalsIgnoreCase(redis_validateCode));
        verifyCode = StringUtils.isNotBlank(verifyCode) ? verifyCode : "";
        if (StringUtils.isBlank(redis_validateCode) || !verifyCode.equalsIgnoreCase(redis_validateCode)) {
            return ResponseUtil.createErrorResponse(EResponseResult.SMS_ERROR_VERIFY);
        }
        try {
            User user = new User();
            user.setTel(tel);
            user.setPassword(password);
            userService.save(user);
        } catch (Exception e) {
            logger.info("用户注册-保存用户出错：{}", CommonUtil.printStackTraceElements(e.getStackTrace()));
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_REGISTER_FAIL);
        }
        return ResponseUtil.createOKResponse(EResponseResult.USER_SUCCESS_REGISTER);
    }

    /**
     * 手机密码重置
     *
     * @return
     */
    @RequestMapping(path = "/resetPassword", method = RequestMethod.POST)
    public Response resetPassword(@RequestBody UserDto userDto) {
        String tel = userDto.getTel();
        String password = userDto.getPassword();
        String verifyCode = userDto.getVerifyCode();
        if (!ValidatorUtil.ValidatePhone(tel)) {
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_PHONE);
        }
        if (!ValidatorUtil.ValidatePassword(password)) { //手机号不合法
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_PASSWORD);
        }
        if (!userService.telExists(tel)) { //手机号码不存在
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_PHONE_NotExist);
        }
        String redis_key = smsCodeSender.getRedisKey(tel, ESmsCodeType.ResetPassword);
        ValidateCode validateCode = BaseConfiguration.generalGson().fromJson((String)redisTemplate.opsForValue().get(redis_key), ValidateCode.class);
        String redis_validateCode = validateCode == null ? "" : validateCode.getCode();
        logger.info("redis-repo验证码:{},用户验证码:{},是否相等:{}", redis_validateCode, verifyCode, verifyCode.equalsIgnoreCase(redis_validateCode));
        verifyCode = StringUtils.isNotBlank(verifyCode) ? verifyCode : "";
        if (StringUtils.isBlank(redis_validateCode) || !verifyCode.equalsIgnoreCase(redis_validateCode)) {
            return ResponseUtil.createErrorResponse(EResponseResult.SMS_ERROR_VERIFY);
        }
        return userService.resetPassword(tel, password);
    }


    public static void main(String[] args) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$).{8,16}$";
        boolean matches = "abcfffff1@".matches(regex);
        System.out.println(matches);
    }

}
