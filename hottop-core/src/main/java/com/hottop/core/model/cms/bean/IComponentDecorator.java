package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.component.ComponentBase;

public interface IComponentDecorator {
    void decorate(ComponentBase component);

    boolean validate(ComponentBase component);
}
