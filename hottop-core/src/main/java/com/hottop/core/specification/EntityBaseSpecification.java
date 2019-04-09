package com.hottop.core.specification;

import com.hottop.core.model.zpoj.EntityBase;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class EntityBaseSpecification<T extends EntityBase> {

    public Specification<T> valid() {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get("deleteTime"));
            }
        };
    }


}
