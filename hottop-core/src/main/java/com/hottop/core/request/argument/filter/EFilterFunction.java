package com.hottop.core.request.argument.filter;

<<<<<<< HEAD
=======
import com.hottop.core.BaseConstant;

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum EFilterFunction {
<<<<<<< HEAD
    flag("flag", "sort"), fields("fields"), view("view");
=======
    flag(BaseConstant.Request.Argument.FLAG, BaseConstant.Request.Argument.SORT),
    fields(BaseConstant.Request.Argument.FIELDS),
    view(BaseConstant.Request.Argument.VIEW);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

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
