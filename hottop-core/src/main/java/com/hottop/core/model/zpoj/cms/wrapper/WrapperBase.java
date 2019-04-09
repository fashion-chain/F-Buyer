package com.hottop.core.model.zpoj.cms.wrapper;

import com.hottop.core.model.zpoj.cms.ITemplate;
import com.hottop.core.model.zpoj.cms.IWrapper;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;

import java.io.Serializable;

public abstract class WrapperBase implements IWrapper, Serializable {
    private ITemplate template;

    public WrapperBase(ITemplate template) {
        this.template = template;
    }

    @Override
    public ETemplateType getTemplateType() {
        return template.getTemplateType();
    }

    @Override
    public ITemplate getTemplate() {
        return this.template;
    }
}
