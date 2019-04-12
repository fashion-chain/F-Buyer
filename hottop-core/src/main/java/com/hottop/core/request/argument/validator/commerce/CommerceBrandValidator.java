package com.hottop.core.request.argument.validator.commerce;

import com.hottop.core.model.zpoj.commerce.bean.CommerceBrandDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 商标 validator
 */
public class CommerceBrandValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CommerceBrandDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null) errors.rejectValue("", null, "商标信息不能为空");
        CommerceBrandDto commerceBrandDto = (CommerceBrandDto) target;
    }
}
