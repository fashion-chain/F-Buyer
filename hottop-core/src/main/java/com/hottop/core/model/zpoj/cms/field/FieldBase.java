package com.hottop.core.model.zpoj.cms.field;

import com.hottop.core.model.zpoj.cms.enums.EFieldType;

import java.io.Serializable;

public abstract class FieldBase implements Serializable {
    private FieldTemplate fieldTemplate;

    public FieldBase(FieldTemplate fieldTemplate) {
        this.fieldTemplate = fieldTemplate;
    }

    public EFieldType getFieldType() {
        return this.fieldTemplate.getFieldType();
    }

    public String getFieldName() {
        return this.fieldTemplate.getFieldName();
    }
}
