package com.hottop.core.model.cms.bean.component.bean;

import java.lang.reflect.Type;

public enum EComponentDecoratorType {
    dataFetcher,
    dataInjector,
    dataComponentIndicator;

    public static Type getCorrespondingType(EComponentDecoratorType decoratorType) {
        Type targetType = null;
        switch (decoratorType) {
            case dataFetcher:
                targetType = DataFetcher.class;
                break;
            case dataInjector:
                targetType = DataInjector.class;
                break;
            case dataComponentIndicator:
                targetType = DataComponentIndicator.class;
                break;
        }
        return targetType;
    }
}
