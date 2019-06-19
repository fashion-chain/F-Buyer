package com.hottop.core.request.argument.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProgramInterfaceNamespace {
    String namespace();
}
