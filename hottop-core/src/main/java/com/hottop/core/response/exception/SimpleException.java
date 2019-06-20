package com.hottop.core.response.exception;

public class SimpleException extends Exception {
    public SimpleException(Class<?> clazz, String msg) {
        super(String.format("Exception in %s, error message:%s", clazz.getSimpleName(), msg));
    }
}
