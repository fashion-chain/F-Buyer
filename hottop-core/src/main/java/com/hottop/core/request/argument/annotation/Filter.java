package com.hottop.core.request.argument.annotation;

import com.hottop.core.model.zpoj.EntityBase;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {
    String parameter() default "filter";

//    Class filter() default void.class;
}
