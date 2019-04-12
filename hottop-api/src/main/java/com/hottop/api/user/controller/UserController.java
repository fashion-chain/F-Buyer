package com.hottop.api.user.controller;

import com.hottop.api.user.service.UserService;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.user.User;
import com.hottop.core.model.user.validator.UserRegisterValidator;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.SmsUtil;
import com.hottop.core.utils.ValidatorUtil;
import com.hottop.core.utils.validator.Phone;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     * 注册验证器
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new UserRegisterValidator());
    }

    @Autowired
    private RedisTemplate redisTemplate;

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
     *
     * @param tel
     * @param password
     * @param verifyCode
     * @return Response标准回复
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestParam String tel,
                             @RequestParam String password,
                             @RequestParam("verifyCode") String verifyCode) {
        if(tel.length() != 11) {
            return ResponseUtil.createErrorResponse("手机号非法");
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        if(!password.matches(regex)) {
            return ResponseUtil.createErrorResponse("密码8到16位数字字母组成");
        }
        //手机号是否已存在
        if( userService.telExists(tel) ){
            return ResponseUtil.createErrorResponse("手机号已经注册");
        }
        //对比verifyCode 验证码
        String tmp_verifyCode = (String) redisTemplate.opsForValue().get(tel + ESmsCodeType.Registration);
        verifyCode = StringUtils.isNotBlank(verifyCode) ? verifyCode : "";
        if (!verifyCode.equalsIgnoreCase(tmp_verifyCode)){
            return ResponseUtil.createErrorResponse("验证码错误");
        }
        try {
            User user = new User();
            user.setTel(tel);
            user.setPassword(password);
            userService.save(user);
        } catch (Exception e) {
            logger.info( "用户注册-保存用户出错：{}", CommonUtil.printStackTraceElements(e.getStackTrace()) );
            return ResponseUtil.createErrorResponse("用户注册-保存用户出错");
        }
        return ResponseUtil.createOKResponse("用户注册成功");
    }

    /**
     * 用户手机密码登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestParam String tel,
                          @RequestParam String password) {
        return userService.login(tel, password);
    }

    /**
     * 免密登录,短信登录
     * @return
     */
    @RequestMapping(value = "/loginMianMi", method = RequestMethod.POST)
    public Response loginMianMi(@Valid @RequestBody User user, Errors errors) {
        HashMap<String,String> map = new HashMap<>();
        if(errors.hasErrors()){
            ValidatorUtil.errorsToMap(map, errors);
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data(map).create();
        }
        return null;
    }


    public static void main(String[] args) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$).{8,16}$";
        boolean matches = "abcfffff1@".matches(regex);
        System.out.println(matches);
    }

}
