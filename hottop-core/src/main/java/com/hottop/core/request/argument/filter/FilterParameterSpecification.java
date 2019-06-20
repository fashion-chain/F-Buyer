package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;
import com.hottop.core.feature.type.TypeFactory;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.filter.exception.FilterIllegalArgumentException;
import com.hottop.core.utils.LogUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterParameterSpecification<T extends EntityBase> implements Specification<T> {

    private ParameterCriteria parameterCriteria;

    public FilterParameterSpecification(String parameterSpecification) {
        this.parameterCriteria = new ParameterCriteria(parameterSpecification);
    }

    private Predicate in(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return get(root, parameterCriteria.getKey())
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

    private <X> Expression<X> get(Root<T> root, String path) {
        String[] paths = path.split("\\.");
        Path finalPath = paths.length > 1 ? root.get(paths[0]) : root.get(path);
        if (paths.length > 1 ) {
            for (int i = 1; i < paths.length; i++) {
                finalPath = finalPath.get(paths[i]);
            }
        }
        if (String.class.isAssignableFrom(finalPath.getJavaType())
                || Number.class.isAssignableFrom(finalPath.getJavaType())
                || Date.class.isAssignableFrom(finalPath.getJavaType()) ) {
            return finalPath;
        }
        return finalPath.as(String.class);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (this.parameterCriteria.getOperator()) {
            case greaterEqualThan:
                return criteriaBuilder.greaterThanOrEqualTo(get(root, parameterCriteria.getKey()), parameterCriteria.getValue());
            case greaterThan:
                return criteriaBuilder.greaterThan(get(root, parameterCriteria.getKey()), parameterCriteria.getValue());
            case lessEqualThan:
                return criteriaBuilder.lessThanOrEqualTo(get(root, parameterCriteria.getKey()), parameterCriteria.getValue());
            case lessThan:
                return criteriaBuilder.lessThan(get(root, parameterCriteria.getKey()), parameterCriteria.getValue());
            case equal:
                return criteriaBuilder.equal(get(root, parameterCriteria.getKey()), parameterCriteria.getValue());
            case notEqual:
                return criteriaBuilder.notEqual(get(root, parameterCriteria.getKey()), parameterCriteria.getValue());
            case in:
                return in(root, criteriaQuery, criteriaBuilder);
            case notIn:
                return criteriaBuilder.not(in(root, criteriaQuery, criteriaBuilder));
            case like:
                return criteriaBuilder.like(get(root, parameterCriteria.getKey()), "%" + parameterCriteria.getValue() + "%");
            case unlike:
                return criteriaBuilder.notLike(get(root, parameterCriteria.getKey()), "%" + parameterCriteria.getValue() + "%");
            case isnull:
                return criteriaBuilder.isNull(get(root, parameterCriteria.getKey()));
            case notnull:
                return criteriaBuilder.isNotNull(get(root, parameterCriteria.getKey()));
            default:
                return null;
        }
    }

    public static class ParameterCriteria {
        private String key;
        private EFilterOperator operator;
        private String value;

        public ParameterCriteria(String key, EFilterOperator operator, String value) {
            this.key = key;
            this.operator = operator;
            this.value = value;
        }

        private ParameterCriteria(String parameterSpecification) {
            String[] parts = parameterSpecification.split(BaseConstant.Common.FILTER_OPERATION_SPLITTER);
            this.key = parts[0];
            this.operator = EFilterOperator.fromOperator(parts[1]);
            String[] valueParts = new String[parts.length-2];
            System.arraycopy(parts, 2, valueParts, 0, valueParts.length);
            this.value = parts.length > 2 ? String.join(BaseConstant.Common.COMMON_SPLITTER, valueParts) : "";
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

        @Override
        public String toString() {
            return String.join(BaseConstant.Common.FILTER_OPERATION_SPLITTER, new String[]{
                    key,
                    operator.operator(),
                    value
            });
        }
    }
}
