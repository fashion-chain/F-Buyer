package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.feature.type.TypeFactory;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.utils.LogUtil;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;

public class TypeIndicatorConverter implements AttributeConverter<TypeIndicator, String> {

    @Override
    public String convertToDatabaseColumn(TypeIndicator typeIndicator) {
        if (typeIndicator == null) {
            return null;
        }
        return typeIndicator.name();
    }

    @Override
    public TypeIndicator convertToEntityAttribute(String s) {
        try {
            return TypeFactory.indicator(s);
        } catch (Exception e) {
            LogUtil.error(String.format("error in TypeIndicatorConverter.convertToEntityAttribute, message: %s", e.getMessage()));
            return TypeFactory.dummyIndicator;
        }
    }
}
