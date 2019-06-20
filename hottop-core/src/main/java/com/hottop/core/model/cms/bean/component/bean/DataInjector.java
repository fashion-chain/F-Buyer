package com.hottop.core.model.cms.bean.component.bean;

import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentContainer;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public class DataInjector extends ComponentDecorator implements Serializable {
    private HashMap<Integer, ComponentBase> injectedComponents;

    public DataInjector() {
        super(EComponentDecoratorType.dataInjector);
    }

    @Override
    public void decorate(ComponentBase component) {
        if (component instanceof ComponentContainer) {
            for (Map.Entry<Integer, ComponentBase> injectComponentsEntry: getInjectedComponents().entrySet()) {
                ((ComponentContainer) component).addCell(injectComponentsEntry.getKey(), injectComponentsEntry.getValue());
            }
        }
    }

    @Override
    public boolean validate(ComponentBase component) {
        if (component != null && component instanceof ComponentContainer) {
            ComponentContainer container = (ComponentContainer) component;
            if (container.getCells() != null && injectedComponents != null) {
                for (Map.Entry<Integer, ComponentBase> injectedComponent : injectedComponents.entrySet()) {
                    if (container.getCells().size() < injectedComponent.getKey() + 1) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
