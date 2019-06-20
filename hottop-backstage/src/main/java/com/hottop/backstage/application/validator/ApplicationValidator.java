package com.hottop.backstage.application.validator;

import com.hottop.backstage.application.service.ApplicationService;
import com.hottop.core.model.user.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ApplicationValidator implements Validator{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationService applicationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Application.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null ) {
            errors.rejectValue("", null, "用户不能为空");
        }
        Application user = null;
        user = (Application) target;
        //判断用户是否已经存在
        if(applicationService.bgUserExistsByUsername(user.getUsername())){
            errors.rejectValue("username", null, "用户已经注册");
        }
        //验证密码字母数组6-16位
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if(user.getPassword() == null) errors.rejectValue("password", null, "密码不能为空");
        if(!user.getPassword().matches(regex)) errors.rejectValue("password", null, "密码格式不符合要求");
    }
}
