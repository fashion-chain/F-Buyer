package com.hottop.core.model.cms.annotation;

import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.EComponentType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentConverter {
    EComponentType[] component();

    EActionType[] action() default {};
}
