package com.hottop.core.model.zpoj.cms.template;

import com.hottop.core.model.zpoj.cms.ITemplate;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;
import com.hottop.core.model.zpoj.cms.field.FieldTemplate;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class TemplateBase implements ITemplate, Serializable {
    private ETemplateType templateType;

    private ArrayList<FieldTemplate> extraFields;

    public TemplateBase(ETemplateType templateType) {
        this.templateType = templateType;
    }

    public boolean addField(FieldTemplate field) {
        if (this.extraFields == null) {
            this.extraFields = new ArrayList<>();
        }
        for (FieldTemplate extraField: extraFields) {
            if (StringUtils.equals(field.getFieldName(), extraField.getFieldName())) {
                return false;
            }
        }
        this.extraFields.add(field);
        return true;
    }

    @Override
    public ETemplateType getTemplateType() {
        return this.templateType;
    }

    @Override
    public ArrayList<FieldTemplate> getExtraFields() {
        return extraFields;
    }
}
