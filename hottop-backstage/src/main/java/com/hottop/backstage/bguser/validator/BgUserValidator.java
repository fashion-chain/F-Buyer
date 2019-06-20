package com.hottop.backstage.bguser.validator;

import com.hottop.backstage.bguser.model.BgUser;
import com.hottop.core.model.user.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BgUserValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(BgUser.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null ) {
            errors.rejectValue("", null, "用户不能为空");
        }
        BgUser user = (BgUser) target;
        if(user.getTel() == null ) {
            errors.rejectValue("tel", null, "手机号码不能为空");
        }
        if(user.getTel().length() != 11) errors.rejectValue("tel", null, "手机号码非法");
        //验证密码字母数组8-16位
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        if(user.getPassword() == null) errors.rejectValue("password", null, "密码不能为空");
        if(!user.getPassword().matches(regex)) errors.rejectValue("password", null, "密码格式不符合要求");
    }
}
