package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.filter.exception.FilterIllegalArgumentException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterParameterSpecification<T extends EntityBase> implements Specification<T> {

    private ParameterCriteria parameterCriteria;

    public FilterParameterSpecification(String parameterSpecification) {
        this.parameterCriteria = new ParameterCriteria(parameterSpecification);
    }

    private Predicate in(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return root.get(parameterCriteria.getKey())
                .in(Arrays.asList(parameterCriteria.getValue().split(BaseConstant.Common.FILTER_SPECIFICATION_VALUE_SPLITTER)));
    }

    public static <T extends EntityBase> Specification<T> unionAnd(ArrayList<Specification<T>> specifications) {
        if (specifications != null && specifications.size() > 0) {
            Specification<T> specification = specifications.get(0);
            for (int i=1; i<specifications.size(); i++) {
                specification = specification.and(specifications.get(i));
            }
            return specification;
        }
        return null;
    }

    public static <T extends EntityBase> Specification<T> unionOr(ArrayList<Specification<T>> specifications) {
        if (specifications != null && specifications.size() > 0) {
            Specification<T> specification = specifications.get(0);
            for (int i=1; i<specifications.size(); i++) {
                specification = specification.or(specifications.get(i));
            }
            return specification;
        }
        return null;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (this.parameterCriteria.getOperator()) {
            case greaterEqualThan:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(parameterCriteria.getKey()), parameterCriteria.getValue());
            case greaterThan:
                return criteriaBuilder.greaterThan(root.get(parameterCriteria.getKey()), parameterCriteria.getValue());
            case lessEqualThan:
                return criteriaBuilder.lessThanOrEqualTo(root.get(parameterCriteria.getKey()), parameterCriteria.getValue());
            case lessThan:
                return criteriaBuilder.lessThan(root.get(parameterCriteria.getKey()), parameterCriteria.getValue());
            case equal:
                return criteriaBuilder.equal(root.get(parameterCriteria.getKey()), parameterCriteria.getValue());
            case notEqual:
                return criteriaBuilder.notEqual(root.get(parameterCriteria.getKey()), parameterCriteria.getValue());
            case in:
                return in(root, criteriaQuery, criteriaBuilder);
            case notIn:
                return criteriaBuilder.not(in(root, criteriaQuery, criteriaBuilder));
            case like:
                return criteriaBuilder.like(root.get(parameterCriteria.getKey()), "%" + parameterCriteria.getValue() + "%");
            default:
                return null;
        }
    }

    public class ParameterCriteria {
        private String key;
        private EFilterOperator operator;
        private String value;

        ParameterCriteria(String parameterSpecification) {
            String[] parts = parameterSpecification.split(BaseConstant.Common.FILTER_OPERATION_SPLITTER);
            this.key = parts[0];
            this.operator = EFilterOperator.fromOperator(parts[1]);
            this.value = parts[2];
        }

        public String getKey() {
            return key;
        }

        public EFilterOperator getOperator() {
            return operator;
        }

        public String getValue() {
            return value;
        }
    }
}
