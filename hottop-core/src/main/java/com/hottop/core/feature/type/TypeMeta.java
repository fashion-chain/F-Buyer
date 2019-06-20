package com.hottop.core.feature.type;

import com.hottop.core.feature.EMetaType;
import com.hottop.core.feature.IMeta;
import lombok.Getter;

@Getter
public class TypeMeta implements IMeta {
    private String key;
    private EMetaType metaType;
    private boolean required;

    private TypeMeta(String key, EMetaType metaType) {
        this.key = key;
        this.metaType = metaType;
    }

    @Override
    public EMetaType metaType() {
        return this.metaType;
    }

    @Override
    public Class<?> serializeClass() {
        return this.metaType.metaClazz();
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public String key() {
        return this.key;
    }

    public static TypeMeta build(String key, EMetaType metaType) {
        return new TypeMeta(key, metaType);
    }

    public TypeMeta required() {
        this.required = true;
        return this;
    }
}
