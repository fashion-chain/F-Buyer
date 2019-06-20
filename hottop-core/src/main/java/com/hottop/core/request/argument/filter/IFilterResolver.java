package com.hottop.core.request.argument.filter;

import com.hottop.core.model.zpoj.EntityBase;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public interface IFilterResolver<T extends EntityBase> {
<<<<<<< HEAD
    ArrayList<Specification<T>> orSpecifications();

    Specification<T> andSpecification();
=======
    ArrayList<Specification<T>> andSpecifications();

    Specification<T> orSpecification();
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

    IFilterFunctionResolver functionResolver(Class<T> clazz);

}
