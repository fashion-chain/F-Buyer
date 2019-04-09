package com.hottop.core.model.zpoj.cms.field;

import com.hottop.core.model.zpoj.cms.enums.EFieldType;
import lombok.Data;

@Data
public class FieldDouble extends FieldBase {
    private Double value;

    public FieldDouble(String fieldName, Double value) {
        super(new FieldTemplate(EFieldType.field_double, fieldName));
        this.value = value;
    }
}
