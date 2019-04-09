package com.hottop.core.model.zpoj.cms;

import com.hottop.core.model.zpoj.cms.enums.ETemplateType;

public interface IWrapper {
    ETemplateType getTemplateType();

    ITemplate getTemplate();
}
