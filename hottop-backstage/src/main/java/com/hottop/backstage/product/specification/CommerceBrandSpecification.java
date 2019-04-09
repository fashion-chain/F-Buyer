package com.hottop.backstage.product.specification;

import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.commerce.CommerceBrand_;
import com.hottop.core.specification.EntityBaseSpecification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommerceBrandSpecification extends EntityBaseSpecification<CommerceBrand> {

    public static Specification<CommerceBrand> filterCountry(String country) {
        return new Specification<CommerceBrand>() {
            @Override
            public Predicate toPredicate(Root<CommerceBrand> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(CommerceBrand_.country), country);
            }
        };
    }
}
