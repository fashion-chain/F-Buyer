package com.hottop.core.model.zpoj.cms;

import com.hottop.core.model.zpoj.cms.enums.ETemplateType;
import com.hottop.core.model.zpoj.cms.field.FieldTemplate;

import java.util.ArrayList;

public interface ITemplate {
    ETemplateType getTemplateType();

    ArrayList<FieldTemplate> getExtraFields();
}
