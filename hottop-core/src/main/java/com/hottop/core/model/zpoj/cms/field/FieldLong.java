package com.hottop.core.model.zpoj.cms.field;

import com.hottop.core.model.zpoj.cms.enums.EFieldType;
import lombok.Data;

@Data
public class FieldLong extends FieldBase {
    private Long value;

    public FieldLong(String fieldName, Long value) {
        super(new FieldTemplate(EFieldType.field_long, fieldName));
        this.value = value;
    }
}
