package com.hottop.core.request.argument.filter;

import com.hottop.core.model.zpoj.EntityBase;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public interface IFilterResolver<T extends EntityBase> {
    ArrayList<Specification<T>> orSpecifications();

    Specification<T> andSpecification();

    IFilterFunctionResolver functionResolver(Class<T> clazz);

}