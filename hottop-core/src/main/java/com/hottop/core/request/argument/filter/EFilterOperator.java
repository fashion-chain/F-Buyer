package com.hottop.core.request.argument.filter;

import org.apache.commons.lang.StringUtils;

public enum EFilterOperator {
    greaterThan(">"),
    greaterEqualThan(">:"),
    lessThan("<"),
    lessEqualThan("<:"),
    equal(":"),
    notEqual("!:"),
    in("in"),
    notIn("!in"),
    like("like"),
    unlike("unlike"),
    isnull("isnull"),
    notnull("notnull");

    private String operator;

    EFilterOperator(String operator) {
        this.operator = operator;
    }

    public static EFilterOperator fromOperator(String operatorString) throws IllegalArgumentException {
        for (EFilterOperator operator: EFilterOperator.values()) {
            if (StringUtils.equals(operatorString, operator.toString())) {
                return operator;
            }
        }
        throw new IllegalArgumentException(String.format("operator: %s not found", operatorString));
    }

    public String operator() {
        return this.operator;
    }

    @Override
    public String toString() {
        return this.operator;
    }
}
