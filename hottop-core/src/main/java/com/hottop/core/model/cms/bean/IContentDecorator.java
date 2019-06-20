package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.component.EComponentType;

public interface IContentDecorator {
    EComponentType decorateComponentType();

    IComponentDecorator decorator();
}
