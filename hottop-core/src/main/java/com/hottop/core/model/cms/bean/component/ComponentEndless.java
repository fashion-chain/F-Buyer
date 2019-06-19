package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.ELoadingDirection;
import lombok.Data;

@Data
public abstract class ComponentEndless extends ComponentBase {
    private String flag;
    private String url;
    private ELoadingDirection direction;

    public ComponentEndless(EComponentType componentType) {
        super(componentType);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
