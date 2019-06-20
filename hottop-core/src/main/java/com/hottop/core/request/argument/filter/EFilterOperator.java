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
<<<<<<< HEAD
    like("like");
=======
    like("like"),
    unlike("unlike"),
    isnull("isnull"),
    notnull("notnull");
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

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

<<<<<<< HEAD
=======
    public String operator() {
        return this.operator;
    }

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    @Override
    public String toString() {
        return this.operator;
    }
}
