package com.hottop.core.feature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum EMetaType {
    string(String.class),
//    integer(Integer.class),
    number(Double.class),
//    date(Date.class),
    list(List.class),
    set(Set.class),
    map(Map.class);

    private Class<?> metaClazz;

    EMetaType(Class<?> metaClazz) {
        this.metaClazz = metaClazz;
    }

    public Class<?> metaClazz() {
        return this.metaClazz;
    }
}
