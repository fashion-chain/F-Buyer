package com.hottop.core.response.exclusion;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleExclusionStrategy implements ExclusionStrategy {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Class<?> clazz;
    private List<String> exclusionFieldNames;

    public SimpleExclusionStrategy(Class<?> clazz, String... fieldNames) {
        this.clazz = clazz;
        this.exclusionFieldNames = new ArrayList<>();
        addExclusionFields(fieldNames);
    }

    public void addExclusionFields(String... fieldNames) {
        this.exclusionFieldNames.addAll(Arrays.asList(fieldNames));
    }

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        if (fieldAttributes.getDeclaringClass() == clazz) {
            for (String fieldName : this.exclusionFieldNames) {
                if (fieldName.equals(fieldAttributes.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
