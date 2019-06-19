package com.hottop.core.feature;

@FunctionalInterface
public interface HandlerFunction<T, R, E extends Exception> {
    R apply(T context, R item) throws E;
}
