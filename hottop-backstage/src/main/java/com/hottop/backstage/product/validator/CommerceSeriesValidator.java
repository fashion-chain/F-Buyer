package com.hottop.backstage.product.validator;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceSeries;
import com.hottop.core.utils.ResponseUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//系列 校验逻辑
public class CommerceSeriesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CommerceSeries.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null) {
            errors.rejectValue("", null, ResponseUtil.notExistResponse(CommerceSeries.class.getSimpleName()).getMessage());
        }
        CommerceSeries series = (CommerceSeries) target;
    }
}
