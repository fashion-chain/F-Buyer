package com.hottop.core.model.zpoj.cms.field;

import com.hottop.core.model.zpoj.cms.enums.EFieldType;
import lombok.Data;

import java.util.Date;

@Data
public class FieldDate extends FieldBase {
    private Date value;

    public FieldDate(String fieldName, Date value) {
        super(new FieldTemplate(EFieldType.field_date, fieldName));
        this.value = value;
    }
}
