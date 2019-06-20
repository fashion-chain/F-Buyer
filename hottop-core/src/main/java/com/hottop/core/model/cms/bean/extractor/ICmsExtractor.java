package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.EComponentType;

public interface ICmsExtractor<T> {
    default ActionBase extractAction(T obj, EActionType actionType) {
        return null;
    }

    default ComponentBase extractComponent(T obj, EComponentType componentType) {
        return null;
    }

    default EComponentType defaultComponentType() {
        return null;
    }

    default EActionType defaultActionType() {
        return null;
    }

    Class<?> clazz();
}
