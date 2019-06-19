package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum EFilterFunction {
    flag(BaseConstant.Request.Argument.FLAG, BaseConstant.Request.Argument.SORT),
    fields(BaseConstant.Request.Argument.FIELDS),
    view(BaseConstant.Request.Argument.VIEW);

    private String[] parameters;
    private String primaryParameter;

    EFilterFunction(String... parameters) {
        this.parameters = parameters;
    }

    public String[] occupiedParameters() {
        return this.parameters;
    }

    public String primaryParameter() {
        return parameters[0];
    }
}
