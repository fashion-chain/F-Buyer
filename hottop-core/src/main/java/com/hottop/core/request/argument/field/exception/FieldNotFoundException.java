package com.hottop.core.request.argument.field.exception;

public class FieldNotFoundException extends Exception {
    public FieldNotFoundException(Class clazz, String fieldName) {
        super(String.format("class: %s field %s not found, please check it out.", clazz.getSimpleName(), fieldName));
    }
}
