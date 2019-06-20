package com.hottop.core.feature;

public interface IMeta {
    EMetaType metaType();

    Class<?> serializeClass();

    boolean isRequired();

    String key();
}
