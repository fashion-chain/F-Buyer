package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.cms.bean.exception.ActionTypeNotSupportException;
import com.hottop.core.model.cms.bean.exception.ExtractorNotFoundException;
import com.hottop.core.model.cms.bean.exception.ComponentTypeNotSupportException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtractorFactory {
    private ArrayList<ICmsExtractor> cmsExtractors;

    public ExtractorFactory() {
        this.cmsExtractors = new ArrayList<>();
    }

    public void registerExtractor(ICmsExtractor... componentExtractor) {
        this.cmsExtractors.addAll(Arrays.asList(componentExtractor));
    }

    public void registerExtractors(List<ICmsExtractor> extractorList) {
        this.cmsExtractors.addAll(extractorList);
    }

    private ICmsExtractor getExtractor(Object obj) throws ExtractorNotFoundException {
        for (ICmsExtractor extractor: cmsExtractors) {
            if (obj.getClass() == extractor.clazz()) {
                return extractor;
            }
        }
        for (ICmsExtractor extractor: cmsExtractors) {
            if (obj.getClass().isAssignableFrom(extractor.clazz())) {
                return extractor;
            }
        }
        throw new ExtractorNotFoundException(String.format("extractor for class: %s not found.", obj.getClass().getSimpleName()));
    }

    private ComponentBase encapsulateComponent(Object obj, EComponentType componentType) throws ComponentTypeNotSupportException, ExtractorNotFoundException {
        ICmsExtractor componentExtractor = getExtractor(obj);
        if (componentType == null) {
            componentType = componentExtractor.defaultComponentType();
        }
        ComponentBase component = componentExtractor.extractComponent(obj, componentType);
        if (component != null) {
            return component;
        }
        throw new ComponentTypeNotSupportException(String.format("component type: %s not support for class: %s", componentType, obj.getClass().getSimpleName()));
    }

    public ComponentBase encapsulateComponent(Object obj, EComponentType componentType, EActionType actionType)
            throws ComponentTypeNotSupportException, ActionTypeNotSupportException,
                ExtractorNotFoundException {
        ICmsExtractor componentExtractor = getExtractor(obj);
        ComponentBase component = encapsulateComponent(obj, componentType);
        if (actionType == null) {
            actionType = componentExtractor.defaultActionType();
        }
        if (actionType != null) {
            ActionBase actionBase = getExtractor(obj).extractAction(obj, actionType);
            if (actionBase != null) {
                component.setAction(actionBase);
            } else {
                throw new ActionTypeNotSupportException(String.format("action type: %s not support for class: %s", actionType, obj.getClass().getSimpleName()));
            }
        }
        return component;
    }
}
