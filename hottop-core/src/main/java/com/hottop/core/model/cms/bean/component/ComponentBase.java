package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.cms.bean.action.ActionBase;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class ComponentBase implements Serializable {
    private EComponentType componentType;
    private ActionBase action;

    ComponentBase(EComponentType componentType) {
        this.componentType = componentType;
    }

    public ComponentBase setAction(ActionBase action) {
        this.action = action;
        return this;
    }
}
