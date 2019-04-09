package com.hottop.core.model.zpoj.cms.component;

import com.hottop.core.model.zpoj.cms.IComponent;
import com.hottop.core.model.zpoj.cms.enums.EComponentType;

import java.io.Serializable;

public abstract class ComponentBase implements IComponent, Serializable {
    private EComponentType componentType;

    public ComponentBase(EComponentType componentType) {
        this.componentType = componentType;
    }

    @Override
    public EComponentType getComponentType() {
        return this.componentType;
    }
}
