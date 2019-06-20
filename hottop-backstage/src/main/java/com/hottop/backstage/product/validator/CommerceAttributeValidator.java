package com.hottop.backstage.product.validator;

import com.hottop.core.model.commerce.CommerceAttribute;
import com.hottop.core.model.commerce.CommerceCategory;
import com.hottop.core.utils.ResponseUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 属性 验证器
 */
public class CommerceAttributeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CommerceAttribute.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null) {
            errors.rejectValue("", null, ResponseUtil.notExistResponse(CommerceCategory.class.getSimpleName()).getMessage());
        }
        CommerceAttribute commerceAttribute = (CommerceAttribute) target;

    }
}
