package com.hottop.core.feature.type;

import com.hottop.core.feature.ICabinet;
import com.hottop.core.feature.type.exception.TypeIndicatorNotFoundException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TypeCabinet implements ICabinet<TypeIndicator> {
    private ArrayList<TypeIndicator> typeIndicators;

    public TypeCabinet() {
        this.typeIndicators = new ArrayList<>();
    }

    @Override
    public TypeCabinet register(TypeIndicator... items) {
        this.typeIndicators.addAll(Arrays.asList(items));
        return this;
    }

    @Override
    public TypeIndicator getItemByName(String name) throws Exception {
        for (TypeIndicator typeIndicator: typeIndicators) {
            if (StringUtils.equals(name, typeIndicator.name())) {
                return typeIndicator;
            }
        }
        throw new TypeIndicatorNotFoundException(String.format("given type: %s not found in this cabinet.", name));
    }

    @Override
    public ArrayList<TypeIndicator> items() {
        return this.typeIndicators;
    }
}
