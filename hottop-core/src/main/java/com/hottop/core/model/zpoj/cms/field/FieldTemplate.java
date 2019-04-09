package com.hottop.core.model.zpoj.cms.field;

import com.hottop.core.model.zpoj.adapter.JsonDeserializationWithOptions;
import com.hottop.core.model.zpoj.cms.enums.EFieldType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class FieldTemplate implements Serializable {
    @JsonDeserializationWithOptions.FieldRequired
    private EFieldType fieldType;

    private String fieldName;

    public FieldTemplate(EFieldType fieldType, String fieldName) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }

    public boolean sameField(FieldTemplate field) {
        return StringUtils.equals(field.getFieldName(), this.fieldName) && field.getFieldType() == this.fieldType;
    }

    public EFieldType getFieldType() {
        return this.fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }
}
