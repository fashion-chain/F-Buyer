package com.hottop.core.request.argument.view.exception;

public class ViewNotFoundException extends Exception {
    public ViewNotFoundException(Class clazz, String viewName) {
        super(String.format("class: %s view %s not found. please check it out.", clazz.getSimpleName(), viewName));
    }
}
