package com.hottop.core.request.argument.validator.user;

import com.hottop.core.model.user.User;
import com.hottop.core.model.user.UserAddress;
import com.hottop.core.repository.user.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.PostConstruct;

/**
 * userAddress validator
 */
public class UserAddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserAddress.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) errors.rejectValue("", null, "用户地址不能为空");
        UserAddress userAddress = (UserAddress) target;
        String zipCodeRegex = "^\\d{6}$";
        String zipCode = userAddress.getZipCode() == null ? "" : userAddress.getZipCode();
        if(!zipCode.matches(zipCodeRegex))
            errors.rejectValue("zipCode", null, "邮政编码不合法");
        //用户token不能为空
        if(StringUtils.isBlank(userAddress.getUserToken())) {
            errors.rejectValue("userToken", null, "用户token不能为空");
        }
    }

}
