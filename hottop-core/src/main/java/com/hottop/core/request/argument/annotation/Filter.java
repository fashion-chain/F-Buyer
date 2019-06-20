package com.hottop.core.request.argument.annotation;

<<<<<<< HEAD
=======
import com.hottop.core.BaseConstant;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import com.hottop.core.model.zpoj.EntityBase;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {
<<<<<<< HEAD
    String parameter() default "filter";
=======
    String parameter() default BaseConstant.Request.Argument.FILTER;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

//    Class filter() default void.class;
}
