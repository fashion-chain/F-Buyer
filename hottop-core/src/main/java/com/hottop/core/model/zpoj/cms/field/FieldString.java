package com.hottop.core.model.zpoj.cms.field;

import com.hottop.core.model.zpoj.cms.enums.EFieldType;
import lombok.Data;

@Data
public class FieldString extends FieldBase {
    private String value;

    public FieldString(String fieldName, String value) {
        super(new FieldTemplate(EFieldType.field_string, fieldName));
        this.value = value;
    }
}
