package com.hottop.core.model.cms.bean.component;

import com.hottop.core.utils.LogUtil;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class ComponentContainer extends ComponentBase {

    private ArrayList<ComponentBase> cells;

    public ComponentContainer(EComponentType componentType) {
        super(componentType);
    }

    public ComponentContainer addCell(Integer index, ComponentBase component) {
        if (cells == null) {
            this.cells = new ArrayList<>();
        }
        if (getComponentType().isValidChildComponentType(component.getComponentType())) {
            this.cells.add(index, component);
        } else {
            LogUtil.error(String.format("componentType: %s can not accept componentType: %s", getComponentType(), component.getComponentType()));
        }
        return this;
    }

    public ComponentContainer addCell(ComponentBase component) {
        if (cells == null) {
            this.cells = new ArrayList<>();
        }
        if (getComponentType().isValidChildComponentType(component.getComponentType())) {
            this.cells.add(component);
        } else {
            LogUtil.error(String.format("componentType: %s can not accept componentType: %s", getComponentType(), component.getComponentType()));
        }
        return this;
    }

    public ComponentContainer setCells(ArrayList<ComponentBase> components) {
        if (cells == null) {
            this.cells = new ArrayList<>();
        } else {
            this.cells.clear();
        }
        for (ComponentBase component: components) {
            addCell(component);
        }
        return this;
    }
}
