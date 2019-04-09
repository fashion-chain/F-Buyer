package com.hottop.core.request.argument.view.exception;

public class ViewExistsException extends Exception {
    public ViewExistsException(Class clazz, String viewName) {
        super(String.format("class: %s view %s exists. please check it out.", clazz.getSimpleName(), viewName));
    }
}
