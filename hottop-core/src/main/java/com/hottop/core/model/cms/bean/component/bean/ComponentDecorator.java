package com.hottop.core.model.cms.bean.component.bean;

import com.hottop.core.model.cms.bean.IComponentDecorator;
import lombok.Getter;

@Getter
public abstract class ComponentDecorator implements IComponentDecorator {
    private EComponentDecoratorType decoratorType;

    public ComponentDecorator(EComponentDecoratorType decoratorType) {
        this.decoratorType = decoratorType;
    }

}
