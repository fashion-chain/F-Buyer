package com.hottop.core.request.argument.field.exception;

public class FieldExistsException extends Exception {
    public FieldExistsException(Class clazz, String fieldName) {
        super(String.format("class: %s field %s exists, please check it out.", clazz.getSimpleName(), fieldName));
    }
}
