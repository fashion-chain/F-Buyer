package com.hottop.backstage.product.validator;

import com.hottop.core.model.commerce.CommerceAttribute;
import com.hottop.core.model.commerce.CommerceService;
import com.hottop.core.utils.ResponseUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * commerceService 服务 验证器
 */
public class CommerceServiceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CommerceService.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null) {
            errors.rejectValue("", null, ResponseUtil.notExistResponse(CommerceService.class.getSimpleName()).getMessage());
        }
        CommerceService commerceAttribute = (CommerceService) target;

    }
}
