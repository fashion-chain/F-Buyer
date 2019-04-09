package com.hottop.core.model.zpoj.cms.wrapper;

import com.hottop.core.model.zpoj.cms.IAction;
import com.hottop.core.model.zpoj.cms.IComponent;
import com.hottop.core.model.zpoj.cms.template.ComponentTemplate;
import lombok.Data;

@Data
public class ComponentWrapper extends WrapperBase {
    private IComponent component;
    private IAction action;

    public ComponentWrapper(ComponentTemplate template, IComponent component, IAction action) {
        super(template);
        this.component = component;
        this.action = action;
    }

}
