package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentContainer;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.cms.bean.component.bean.DataDecorator;
import com.hottop.core.model.cms.bean.component.bean.ComponentDecorator;

import java.util.ArrayList;
import java.util.Map;

public class CmsValidation {

    public static boolean templateValidation(PageContent pageContent, TemplateContent templateContent) {
        if (pageContent == null || pageContent.getComponents() == null
                || templateContent == null || templateContent.getComponents() == null) {
            return false;
        }
        if (pageContent.getComponents().size() != templateContent.getComponents().size()) {
            return false;
        }
        for (int i=0; i<templateContent.getComponents().size(); i++) {
            TemplateComponent templateComponent = templateContent.getComponents().get(i);
            ComponentBase component = pageContent.getComponents().get(i);
            if (templateComponent.getComponentType() != component.getComponentType()) {
                return false;
            }
        }
        return true;
    }

    public static boolean contentValidation(PageContent pageContent) {
        boolean validationPassed = true;
        for (ComponentBase component: pageContent.getComponents()) {
            if (ComponentContainer.class.isAssignableFrom(component.getClass()) && ((ComponentContainer)component).getCells() != null) {
                for (ComponentBase childComponent: ((ComponentContainer)component).getCells()) {
                    boolean isAvailableChildComponentType = false;
                    for (EComponentType childComponentType: component.getComponentType().getAvailableChildComponentTypes()) {
                        if (childComponent.getComponentType() == childComponentType) {
                            isAvailableChildComponentType = true;
                        }
                    }
                    if (!isAvailableChildComponentType) {
                        validationPassed = false;
                        break;
                    }
                }
            }
        }
        return validationPassed;
    }

    public static boolean dataDecoratorValidation(PageContent pageContent, DataDecorator dataDecorator) {
        if (dataDecorator == null || dataDecorator.getDecorators() == null) {
            return true;
        }

        for (Map.Entry<Integer, ArrayList<ComponentDecorator>> decorators: dataDecorator.getDecorators().entrySet()) {
            if (pageContent.getComponents().size() < decorators.getKey() + 1) {
                return false;
            }
            if (decorators.getValue() != null) {
                for (ComponentDecorator decorator: decorators.getValue()) {
                    if (!decorator.validate(pageContent.getComponents().get(decorators.getKey()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
