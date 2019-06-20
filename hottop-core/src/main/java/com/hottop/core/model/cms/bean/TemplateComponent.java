package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.EComponentType;
import lombok.Getter;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Getter
public class TemplateComponent implements Serializable {
    private EComponentType componentType;
    private EActionType actionType;

    public TemplateComponent(String templateComponentStr) {
        if (templateComponentStr.contains("_")) {
            String[] parts = templateComponentStr.split("_");
            this.componentType = EComponentType.valueOf(parts[0]);
            this.actionType = EActionType.valueOf(parts[1]);
        } else {
            this.componentType = EComponentType.valueOf(templateComponentStr);
        }
    }

    public TemplateComponent(EComponentType componentType, EActionType actionType) {
        this.componentType = componentType;
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return String.format("%s_%s", componentType, actionType);
    }

    public EComponentType getComponentType() {
        return componentType;
    }

    public EActionType getActionType() {
        return actionType;
    }

    public static class TemplateComponentBuilder {
        private EComponentType componentType;
        private EActionType actionType;

        private TemplateComponentBuilder(EComponentType componentType) {
            this.componentType = componentType;
        }

        public static TemplateComponentBuilder init(EComponentType componentType) {
            return new TemplateComponentBuilder(componentType);
        }

        public TemplateComponentBuilder action(EActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public TemplateComponent create() {
            return new TemplateComponent(this.componentType, this.actionType);
        }
    }
}
