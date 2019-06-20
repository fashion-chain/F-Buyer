package com.hottop.core.order.validator;

import com.hottop.core.order.vo.PurchaseOrderVo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PurchaseOrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PurchaseOrderVo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PurchaseOrderVo purchaseOrderVo = (PurchaseOrderVo) target;


    }
}
