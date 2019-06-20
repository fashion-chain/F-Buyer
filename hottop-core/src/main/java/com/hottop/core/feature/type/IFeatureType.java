package com.hottop.core.feature.type;

import java.util.HashMap;

public interface IFeatureType {
    TypeIndicator getType();

    HashMap<String, Object> getTypeMeta();

    void setTypeMeta(HashMap<String, Object> metaParams);

    default HashMap<String, Object> specificTypeMeta() throws Exception {
        TypeIndicator typeIndicator = getType();
        if (typeIndicator != null) {
            return typeIndicator.value(getTypeMeta());
        }
        return null;
    }
}
