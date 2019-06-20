package com.hottop.core.model.cms.bean.component;

import lombok.Getter;

@Getter
public class ComponentBanner extends ComponentContainer {
    private String slipDuration;  //unit: ms

    public ComponentBanner(String slipDuration) {
        super(EComponentType.banner);
        this.slipDuration = slipDuration;
    }
}
