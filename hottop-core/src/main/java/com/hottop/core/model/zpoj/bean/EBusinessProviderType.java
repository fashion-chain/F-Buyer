package com.hottop.core.model.zpoj.bean;

import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.user.User;

public enum EBusinessProviderType {

    brand(CommerceBrand.class),
    user(User.class),
    ;


    private Class<?> clazz;

    EBusinessProviderType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> entity() {
        return this.clazz;
    }
}
