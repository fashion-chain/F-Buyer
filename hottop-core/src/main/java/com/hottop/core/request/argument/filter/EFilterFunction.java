package com.hottop.core.request.argument.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum EFilterFunction {
    flag("flag", "sort"), fields("fields"), view("view");

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
