package com.hottop.core.model.cms.bean.component;

import lombok.Getter;

@Getter
public class ComponentSwitcher extends ComponentBase {

    public ComponentSwitcher(Long duration) {
        super(EComponentType.switcher);
    }
}
